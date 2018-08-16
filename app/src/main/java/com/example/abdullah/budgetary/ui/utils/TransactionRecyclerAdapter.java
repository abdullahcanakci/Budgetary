package com.example.abdullah.budgetary.ui.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.databinding.LayoutTransactionListViewBinding;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionHolder> {
    private List<Transaction> activeList;
    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = from(parent.getContext());
        LayoutTransactionListViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_transaction_list_view, parent, false);
        return new TransactionHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        holder.bind(activeList.get(position));
    }

    @Override
    public int getItemCount() {
        return activeList == null ? 0 : activeList.size();
    }

    public void updateList(List<Transaction> newTransactions) {
        if (newTransactions == null || newTransactions.size() == 0) {
            return;
        }

        TransactionDiffUtilCallback diff = new TransactionDiffUtilCallback(activeList, newTransactions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diff, true);
        result.dispatchUpdatesTo(this);

        activeList = newTransactions;
    }


}
