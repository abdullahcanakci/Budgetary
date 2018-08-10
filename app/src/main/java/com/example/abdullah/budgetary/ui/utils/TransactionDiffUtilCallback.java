package com.example.abdullah.budgetary.ui.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.abdullah.budgetary.data.Transaction;

import java.util.List;

class TransactionDiffUtilCallback extends DiffUtil.Callback {
    private final List<Transaction> newTransactions;
    private final List<Transaction> transactions;

    TransactionDiffUtilCallback(List<Transaction> transactions, List<Transaction> newTransactions) {
        this.transactions = transactions;
        this.newTransactions = newTransactions;
    }

    @Override
    public int getOldListSize() {
        return transactions == null ? 0 : transactions.size();
    }

    @Override
    public int getNewListSize() {
        return newTransactions == null ? 0 : newTransactions.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return transactions.get(oldItemPosition).getId() == newTransactions.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return transactions.get(oldItemPosition).equals(newTransactions.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
