package com.example.mobilestore.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilestore.R;
import com.example.mobilestore.activities.SearchActivity;
import com.example.mobilestore.adapter.HomeAdapter;
import com.example.mobilestore.adapter.PopularAdapter;
import com.example.mobilestore.adapter.RecommendedAdapter;
import com.example.mobilestore.models.HomeCategory;
import com.example.mobilestore.models.PopularModel;
import com.example.mobilestore.models.RecommendedModel;
import com.example.mobilestore.scanner.StartScannerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView popularly, explorary, recommendar;
    ScrollView scrollView;

    ProgressBar progressBar;
    RecyclerView popularRec, homecatRec, recommendedRec;
    FirebaseFirestore db;
    AutoCompleteTextView search;
    Button scan, seacrhing;
    //    string recommend
    private static final String[] products = new String[]{
            "Phone", "Iphone", "Iphone 12", "LapTop", "Laptop msi", "Laptop Asus", "Laptop Acer", "Laptop HP", "Watch", "Accessories", "tablet", "Iphone", "Ipad", "Xiaomi", "Xiaomi Redmi A11",
    };
    //    pop item l?? pop item
    List<PopularModel> popularModelList;
    PopularAdapter popularAdapter;

    //    casi n??y d??nh cho home l?? d??nh cho home category kh??ng d??ng cho c??i kh??c
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    //    ?????a b??n c???a Recommended
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homecatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        search = root.findViewById(R.id.search_box);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, products);
        search.setThreshold(1);
        search.setAdapter(adapter);
        seacrhing = root.findViewById(R.id.btn_search);

        seacrhing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchItem = search.getText().toString();
                if (searchItem.isEmpty()) {
//                    Toast.makeText(getActivity(), "Your search box is empty", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(getContext())
                            .setTitle("Empty text")
                            .setMessage("Please type what you want to search !!!")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    System.out.println(searchItem);
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("type", searchItem);
                    startActivity(intent);

                }
            }
        });

        scan = root.findViewById(R.id.btn_scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StartScannerActivity.class));
            }
        });

//        cho c??c item n?? hi???n h???n ????? ?????p m???t mr c??ng cho mr c??ng l?? m???t ch??i -> popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularModelList);
        popularRec.setAdapter(popularAdapter);
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        ph???n n??y l?? c???a home l???y truy v???n t??? c?? s??? d??? li???u xong n??m v??, ???? code l?? ph???i ghi ch?? ????? mr c??ng xem mr kong kho??i
        homecatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), categoryList);
        homecatRec.setAdapter(homeAdapter);
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        recommended t???i ????y
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}