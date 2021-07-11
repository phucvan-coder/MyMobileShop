package com.example.mobilestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilestore.R;
import com.example.mobilestore.activities.DetailedActivity;
import com.example.mobilestore.activities.SearchActivity;
import com.example.mobilestore.activities.SearchDetailActivity;
import com.example.mobilestore.models.SearchAllModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAllAdalter extends RecyclerView.Adapter<SearchAllAdalter.ViewHolder> {

    Context context;
    List<SearchAllModel> list;

    public SearchAllAdalter(Context context, List<SearchAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.price.setText(list.get(position).getPrice() + " $");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchDetailActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, price, rating;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.search_img);
            name = itemView.findViewById(R.id.seach_name);
            description = itemView.findViewById(R.id.search_description);
            price = itemView.findViewById(R.id.search_price);
            rating = itemView.findViewById(R.id.search_rating);

        }
    }
}
