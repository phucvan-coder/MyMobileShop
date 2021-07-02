package com.example.mobilestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilestore.R;
import com.example.mobilestore.models.SearchAllModel;
import com.example.mobilestore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SearchDetailActivity extends AppCompatActivity {

    TextView quantity;
    int totalquantity = 1;
    int totalPrice = 0;
    ImageView detailImg;
    TextView price, rating, description;
    Button addToCart;
    ImageView addItem, removeItem;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    SearchAllModel searchAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof SearchAllModel) {
            searchAllModel = (SearchAllModel) object;
        }

        quantity = findViewById(R.id.search_quantity);

        detailImg = findViewById(R.id.search_detailed_img);
        addItem = findViewById(R.id.search_add_item);
        removeItem = findViewById(R.id.search_remove_item);

        price = findViewById(R.id.search_detailed_price);
        rating = findViewById(R.id.search_detailed_rating);
        description = findViewById(R.id.search_detailed_rec);

        if (searchAllModel != null) {
            Glide.with(getApplicationContext()).load(searchAllModel.getImg_url()).into(detailImg);
            rating.setText(searchAllModel.getRating());
            description.setText(searchAllModel.getDescription());
            price.setText("Price: " + searchAllModel.getPrice() + " $");

            totalPrice = searchAllModel.getPrice() * totalquantity;

        }

        addToCart = findViewById(R.id.search_add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalquantity < 1000000) {
                    ++totalquantity;
                    quantity.setText(String.valueOf(totalquantity));
                    totalPrice = searchAllModel.getPrice() * totalquantity;
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalquantity > 1) {
                    --totalquantity;
                    quantity.setText(String.valueOf(totalquantity));
                    totalPrice = searchAllModel.getPrice() * totalquantity;
                }
            }
        });

    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", searchAllModel.getName());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(SearchDetailActivity.this, "Added To Cart !!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}