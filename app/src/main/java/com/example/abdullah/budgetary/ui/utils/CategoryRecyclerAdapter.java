package com.example.abdullah.budgetary.ui.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.databinding.CardCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    List<Category> categories = new ArrayList<>();
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.card_category, viewGroup, false);

        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        if (categories!= null)
            categoryViewHolder.bind(categories.get(i));
    }

    public void updateList(List<Category> categories) {
        if(categories == null || categories.size() == 0)
            return;
        this.categories = categories;
        notifyDataSetChanged();
        /*

        CategoryDiffUtilCallBack diff = new CategoryDiffUtilCallBack(this.categories, categories);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diff, true);
        result.dispatchUpdatesTo(this);*/
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
