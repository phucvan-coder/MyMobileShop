package com.example.mobilestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mobilestore.R;
import com.example.mobilestore.adapter.AllProductsAdapter;
import com.example.mobilestore.models.AllProductsModel;
import com.example.mobilestore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllProductsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AllProductsAdapter allProductsAdapter;
    List<AllProductsModel> allProductsModelList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        String type = "query all from allproducts collection";
        recyclerView = findViewById(R.id.view_all_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allProductsModelList = new ArrayList<>();
        allProductsAdapter = new AllProductsAdapter(this, allProductsModelList);

        if (type != null) {
            firestore.collection("AllProducts").whereEqualTo("type", "phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AllProductsModel allProductsModel = documentSnapshot.toObject(AllProductsModel.class);
                        allProductsModelList.add(allProductsModel);
                        allProductsAdapter.notifyDataSetChanged();
                    }

                }
            });

            firestore.collection("AllProducts").whereEqualTo("type", "laptop").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AllProductsModel allProductsModel = documentSnapshot.toObject(AllProductsModel.class);
                        allProductsModelList.add(allProductsModel);
                        allProductsAdapter.notifyDataSetChanged();
                    }

                }
            });

            firestore.collection("AllProducts").whereEqualTo("type", "tablet").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AllProductsModel allProductsModel = documentSnapshot.toObject(AllProductsModel.class);
                        allProductsModelList.add(allProductsModel);
                        allProductsAdapter.notifyDataSetChanged();
                    }

                }
            });

            firestore.collection("AllProducts").whereEqualTo("type", "watch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AllProductsModel allProductsModel = documentSnapshot.toObject(AllProductsModel.class);
                        allProductsModelList.add(allProductsModel);
                        allProductsAdapter.notifyDataSetChanged();
                    }

                }
            });

            firestore.collection("AllProducts").whereEqualTo("type", "accessories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AllProductsModel allProductsModel = documentSnapshot.toObject(AllProductsModel.class);
                        allProductsModelList.add(allProductsModel);
                        allProductsAdapter.notifyDataSetChanged();
                    }

                }
            });

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}