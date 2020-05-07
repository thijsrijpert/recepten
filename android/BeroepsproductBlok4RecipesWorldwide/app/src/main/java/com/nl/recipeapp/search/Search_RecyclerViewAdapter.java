package com.nl.recipeapp.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Recipe;

import java.util.ArrayList;

public class Search_RecyclerViewAdapter extends RecyclerView.Adapter<Search_RecyclerViewAdapter.ViewHolder> {
    private View view;
    private ArrayList<Recipe> recipes;

    public Search_RecyclerViewAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listitem_ingredient, parent, false);
        Search_RecyclerViewAdapter.ViewHolder viewHolder = new Search_RecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textview_title.setText(recipes.get(position).getName());
        holder.textview_subtext.setText(recipes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_title, textview_subtext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_title = itemView.findViewById(R.id.recyclerview_listitem_recipe_textview_title);
            textview_subtext = itemView.findViewById(R.id.recyclerview_listitem_recipe_textview_subtext);
        }
    }
}
