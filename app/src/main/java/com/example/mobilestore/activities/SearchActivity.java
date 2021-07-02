package com.example.mobilestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobilestore.R;
import com.example.mobilestore.adapter.SearchAllAdalter;
import com.example.mobilestore.adapter.ViewAllAdapter;
import com.example.mobilestore.models.SearchAllModel;
import com.example.mobilestore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    SearchAllAdalter searchAllAdalter;
    Toolbar toolbar;
    List<SearchAllModel> searchAllModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchAllModelList = new ArrayList<>();
        searchAllAdalter = new SearchAllAdalter(this, searchAllModelList);
        recyclerView.setAdapter(searchAllAdalter);

//        search fruit
        if (type != null && type.equalsIgnoreCase("phone")) {
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
//        search vegetable
        if (type != null && type.equalsIgnoreCase("tablet")) {
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
//        search fish
        if (type != null && type.equalsIgnoreCase("watch")) {
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
//        search egg
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
//        search milkkkkkkkkkkkkk
        if (type != null && type.equalsIgnoreCase("milk")) {
            firestore.collection("AllProducts")
                    .whereEqualTo("type", "egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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