package com.example.abdullah.budgetary.ui.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.databinding.CardCategoryBinding;

class CategoryViewHolder extends RecyclerView.ViewHolder {
    private CardCategoryBinding binding;

    public CategoryViewHolder(@NonNull CardCategoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Category category) {
        binding.selected.setVisibility(View.INVISIBLE);
        binding.setCategory(category);
    }
}
