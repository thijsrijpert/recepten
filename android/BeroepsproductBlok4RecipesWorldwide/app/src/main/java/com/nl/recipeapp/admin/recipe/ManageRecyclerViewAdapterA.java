package com.nl.recipeapp.admin.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Ingredient;

import java.util.ArrayList;

public class ManageRecyclerViewAdapterA extends RecyclerView.Adapter<com.nl.recipeapp.admin.recipe.ManageRecyclerViewAdapterA.ViewHolder> {
    private View view;
    private ArrayList<Ingredient> ingredients_recyclerview;

    public ManageRecyclerViewAdapterA(ArrayList<Ingredient> ingredients_recyclerview) {
        this.ingredients_recyclerview = ingredients_recyclerview;
    }

    @NonNull
    @Override
    public com.nl.recipeapp.admin.recipe.ManageRecyclerViewAdapterA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listitem_ingredient, parent, false);
        com.nl.recipeapp.admin.recipe.ManageRecyclerViewAdapterA.ViewHolder viewHolder = new com.nl.recipeapp.admin.recipe.ManageRecyclerViewAdapterA.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.nl.recipeapp.admin.recipe.ManageRecyclerViewAdapterA.ViewHolder holder, int position) {
        holder.textView.setText(ingredients_recyclerview.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ingredients_recyclerview.size();
    }

    /**
     * Creates an inner ViewHolder class that holds the elements we need from the desired .xml file
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.general_textview_recyclerViewListItem);
            linearLayout = itemView.findViewById(R.id.general_recyclerViewListItemParentLayout);
        }
    }
}