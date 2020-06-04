package com.nl.recipeapp.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Review;

import java.util.ArrayList;

public class DetailedViewRecyclerViewAdapter_Reviews extends RecyclerView.Adapter<DetailedViewRecyclerViewAdapter_Reviews.ViewHolder> {
    private View view;
    private ArrayList<Review> arraylist_reviews;

    public DetailedViewRecyclerViewAdapter_Reviews(ArrayList<Review> arraylist_reviews) {
        this.arraylist_reviews = arraylist_reviews;
    }

    @NonNull
    @Override
    public DetailedViewRecyclerViewAdapter_Reviews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listitem_review, parent, false);
        DetailedViewRecyclerViewAdapter_Reviews.ViewHolder viewHolder = new DetailedViewRecyclerViewAdapter_Reviews.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedViewRecyclerViewAdapter_Reviews.ViewHolder holder, int position) {
        holder.textview_title.setText(arraylist_reviews.get(position).getTitle());
        holder.textview_rating.setText(arraylist_reviews.get(position).getRating());
        holder.textview_description.setText(arraylist_reviews.get(position).getDescription());
        holder.textview_username.setText(arraylist_reviews.get(position).getUsername());
        holder.textview_date.setText(arraylist_reviews.get(position).getReview_date());
    }

    @Override
    public int getItemCount() {
        return arraylist_reviews.size();
    }

    /**
     * Creates an inner ViewHolder class that holds the elements we need from the desired .xml file
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_title, textview_rating, textview_description, textview_username, textview_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_title = itemView.findViewById(R.id.recyclerview_listitem_review_textview_title);
            textview_rating = itemView.findViewById(R.id.recyclerview_listitem_review_textview_rating);
            textview_description = itemView.findViewById(R.id.recyclerview_listitem_review_textview_description);
            textview_username = itemView.findViewById(R.id.recyclerview_listitem_review_textview_username);
            textview_date  = itemView.findViewById(R.id.recyclerview_listitem_review_textview_date);
        }
    }
}
