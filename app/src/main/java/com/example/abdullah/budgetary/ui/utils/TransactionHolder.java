package com.example.abdullah.budgetary.ui.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.databinding.LayoutTransactionListViewBinding;

class TransactionHolder extends RecyclerView.ViewHolder {
    private LayoutTransactionListViewBinding binding;

    TransactionHolder(LayoutTransactionListViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    void bind(Transaction transaction) {
        if(transaction!= null)
            binding.setTransaction(transaction);
    }
}
