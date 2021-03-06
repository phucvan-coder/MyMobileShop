package com.example.mobilestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilestore.R;
import com.example.mobilestore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    MyCartAdapter myCartAdapter;
    Context context;
    List<MyCartModel> cartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    public long Calculate() {
        // pass data
        long totalPrice = 0;
        for (int i = 0; i < cartModelList.size(); ++i)
            totalPrice += cartModelList.get(i).getTotalPrice();
        return totalPrice;
    }

    public void clearMemory() {
        if (cartModelList.size() > 0) cartModelList.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));

        long totalPrice = Calculate();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//        x??a c??c sp ??? ????y
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").document(cartModelList.get(position).getDocumentUID()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                // n???u x??a th??nh c??ng
                                if (task.isSuccessful()) {
//                                    x??a theo v??? tr?? c???a item tr??n trang
                                    cartModelList.remove(cartModelList.get(position));
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
//                                    x??a xong th?? g??n l???i cho t???ng gi?? tr??? l?? 0 xong r???i d??ng thu???t to??n v??t c???n t??nh t???ng l???i l???n n???a
                                    long totalPrice = Calculate();
                                    Intent intent = new Intent("MyTotalAmount");
                                    intent.putExtra("totalAmount", totalPrice);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                                    rloasd l???i forrm cho n?? ?????p
                                    MyCartAdapter.this.notifyDataSetChanged();

                                } else { // n???u ko x??a ??c
                                    Toast.makeText(context, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, time, quantity, totalPrice, total;
        ImageView deleteItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteItem = itemView.findViewById(R.id.delete);
            total = itemView.findViewById(R.id.textview6);
        }
    }
}
