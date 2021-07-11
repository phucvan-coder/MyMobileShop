package com.example.mobilestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilestore.R;
import com.example.mobilestore.adapter.SearchAllAdalter;
import com.example.mobilestore.adapter.ViewAllAdapter;
import com.example.mobilestore.models.SearchAllModel;
import com.example.mobilestore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    SearchAllAdalter searchAllAdalter;
    Toolbar toolbar;
    Spinner s1, s2;
    String from, to;
    List<SearchAllModel> searchAllModelList;
    Button filter, reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filter = findViewById(R.id.filter);
        reload = findViewById(R.id.refresh);

        searchAllModelList = new ArrayList<>();
        searchAllAdalter = new SearchAllAdalter(this, searchAllModelList);
        recyclerView.setAdapter(searchAllAdalter);

        String[] price = new String[]{
                "100", "200", "300", "400", "500", "600", "700", "800", "900",
                "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000"
        };

        s1 = findViewById(R.id.spin1);
        s2 = findViewById(R.id.spin2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.support_simple_spinner_dropdown_item, price);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s2.setAdapter(adapter);

//        search phone
        if (type != null && (type.equalsIgnoreCase("phone") ||
                type.equalsIgnoreCase("p") ||
                type.equalsIgnoreCase("ph") ||
                type.equalsIgnoreCase("pho")) ||
                type.equalsIgnoreCase("phon")) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        searchAllModelList.add(searchAllModel);
                        searchAllAdalter.notifyDataSetChanged();
                    }

                }
            });
        }
//        search tablet
        if (type != null && (type.equalsIgnoreCase("tablet") ||
                type.equalsIgnoreCase("t") ||
                type.equalsIgnoreCase("ta") ||
                type.equalsIgnoreCase("tab") ||
                type.equalsIgnoreCase("tabl") ||
                type.equalsIgnoreCase("table"))) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "tablet").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        searchAllModelList.add(searchAllModel);
                        searchAllAdalter.notifyDataSetChanged();
                    }

                }
            });
        }
//        search watch
        if (type != null && (type.equalsIgnoreCase("watch") ||
                type.equalsIgnoreCase("w") ||
                type.equalsIgnoreCase("wa") ||
                type.equalsIgnoreCase("wat") ||
                type.equalsIgnoreCase("watc"))) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "watch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        searchAllModelList.add(searchAllModel);
                        searchAllAdalter.notifyDataSetChanged();
                    }

                }
            });
        }
//        search laptop
        if (type != null && type.equalsIgnoreCase("laptop")) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "laptop").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        searchAllModelList.add(searchAllModel);
                        searchAllAdalter.notifyDataSetChanged();
                    }

                }
            });
        }
//        search acess
        if (type != null && (type.equalsIgnoreCase("accessories") ||
                type.equalsIgnoreCase("a") ||
                type.equalsIgnoreCase("ac") ||
                type.equalsIgnoreCase("acc") ||
                type.equalsIgnoreCase("acces") ||
                type.equalsIgnoreCase("access"))) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "accessories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        searchAllModelList.add(searchAllModel);
                        searchAllAdalter.notifyDataSetChanged();
                    }

                }
            });
        }

        //        searchiung every thing ting ting
        if (type != null) {
            firestore.collection("AllProducts").whereGreaterThanOrEqualTo("name", type)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        SearchAllModel searchAllModel = documentSnapshot.toObject(SearchAllModel.class);
                        if (searchAllModel.getName().contains(type)) {
                            searchAllModelList.add(searchAllModel);
                            searchAllAdalter.notifyDataSetChanged();
                        }
                    }

                }
            });
        }
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    from = s1.getSelectedItem().toString();
                    to = s2.getSelectedItem().toString();
                    int a = Integer.parseInt(from);
                    int b = Integer.parseInt(to);
                    if (a > b) {
                        new AlertDialog.Builder(SearchActivity.this)
                                .setTitle("Wrong Select")
                                .setMessage("Your from parameter smaller than to parameter !!!")
                                .setNegativeButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        searchAllModelList.clear();
                        firestore.collection("AllProducts").whereLessThanOrEqualTo("price", b)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                SearchAllModel searchAllModel = document.toObject(SearchAllModel.class);
                                                if (searchAllModel.getPrice() <= b && searchAllModel.getPrice() >= a) {
                                                    searchAllModelList.add(searchAllModel);
                                                    searchAllAdalter.notifyDataSetChanged();
                                                    recyclerView.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    new AlertDialog.Builder(SearchActivity.this)
                            .setTitle("Error")
                            .setMessage(e.getMessage().toString())
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAllModelList.clear();
                firestore.collection("AllProducts")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        SearchAllModel searchAllModel = document.toObject(SearchAllModel.class);
                                        searchAllModelList.add(searchAllModel);
                                        searchAllAdalter.notifyDataSetChanged();
                                        recyclerView.setVisibility(View.VISIBLE);

                                    }
                                } else {
                                    new AlertDialog.Builder(SearchActivity.this)
                                            .setTitle("Error")
                                            .setMessage("Some thing went wrong here")
                                            .setNegativeButton(android.R.string.yes, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}