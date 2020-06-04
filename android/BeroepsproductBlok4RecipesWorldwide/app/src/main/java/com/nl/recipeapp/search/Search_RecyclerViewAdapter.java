package com.nl.recipeapp.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.recipe.DetailedView;

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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listitem_recipe, parent, false);
        Search_RecyclerViewAdapter.ViewHolder viewHolder = new Search_RecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textview_title.setText(recipes.get(position).getName());
        holder.textview_subtext.setText(recipes.get(position).getDescription());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailedView.class);
                Recipe recipe = null;

                for (int c = 0; c < recipes.size(); c++) {
                    if (recipes.get(position).getName().equals(recipes.get(c).getName())) {
                        recipe = recipes.get(c);
                    }
                }

                intent.putExtra("KEY", recipe);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_title, textview_subtext;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_title = itemView.findViewById(R.id.recyclerview_listitem_recipe_textview_title);
            textview_subtext = itemView.findViewById(R.id.recyclerview_listitem_recipe_textview_subtext);
            linearLayout = itemView.findViewById(R.id.recyclerview_listitem_recipe_linearlayout);
        }
    }
}
