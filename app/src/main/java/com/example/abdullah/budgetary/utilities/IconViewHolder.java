package com.example.abdullah.budgetary.utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.databinding.LayoutIconHolderBinding;

public class IconViewHolder extends RecyclerView.ViewHolder {
    private final LayoutIconHolderBinding binding;

    IconViewHolder(@NonNull LayoutIconHolderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull Icon icon) {
        binding.setIcon(icon);
    }
}
