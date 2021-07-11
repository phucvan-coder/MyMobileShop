package com.example.mobilestore;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilestore.adapter.MyCartAdapter;
import com.example.mobilestore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartsFragment extends Fragment {

    MyCartsFragment myCartsFragment;
    ConstraintLayout constraintLayout;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    Button buyNow;
    String res;
    int totalBill;
    ProgressBar progressBar;

    public MyCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        buyNow = root.findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        overTotalAmount = root.findViewById(R.id.textview6);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("MyTotalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        constraintLayout = root.findViewById(R.id.constraint1);
        if (cartModelList.isEmpty()) {
            constraintLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else if (cartModelList.size() > 0) {
            constraintLayout.setVisibility(View.GONE);
        }
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentUID = documentSnapshot.getId();

                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                        cartModel.setDocumentUID(documentUID);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                        constraintLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        myRef.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    String g = s.getValue().toString();
                    if (g.equalsIgnoreCase("normal") || g.equalsIgnoreCase("vip")) {
                        res = g;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartModelList.size() == 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Empty Cart!!!!!")
                            .setMessage("Your cart is empty !!!")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    long totalPayment = 0, respayment = 0;
                    if (cartModelList.size() > 0) {
                        for (int i = 0; i < cartModelList.size(); ++i) {
                            totalPayment += cartModelList.get(i).getTotalPrice();
                        }
                    }
                    if (res.equalsIgnoreCase("vip")) {
                        respayment = totalPayment - (totalPayment * 20 / 100);
                    } else {
                        respayment = totalPayment;
                    }
                    new AlertDialog.Builder(getContext())
                            .setTitle("Price notification")
                            .setMessage("Your revenue is: " + respayment + " $")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                                    intent.putExtra("itemList", (Serializable) cartModelList);
                                    startActivity(intent);
                                    for (int i = 0; i < cartModelList.size(); ++i) {
                                        int position = i;
                                        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                                .collection("AddToCart").document(cartModelList.get(i).getDocumentUID()).delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            try {
                                                                cartModelList.remove(cartModelList.get(position));
                                                            } catch (IndexOutOfBoundsException e) {
                                                                System.out.println(e.getMessage());
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                    constraintLayout = root.findViewById(R.id.constraint1);
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    constraintLayout.setVisibility(View.VISIBLE);
                                    overTotalAmount.setText("0");
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }
        });
        return root;
    }


    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long totalBill = intent.getLongExtra("totalAmount", 0);
            overTotalAmount.setText("Total Bill: " + totalBill + "$");
        }
    };
}