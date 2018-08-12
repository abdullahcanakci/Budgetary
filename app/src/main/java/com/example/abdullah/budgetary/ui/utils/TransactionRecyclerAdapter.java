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

import java.util.List;

import static android.view.LayoutInflater.from;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionHolder> {
    private List<Transaction> transactions;
    private int numberOfItems;

    public TransactionRecyclerAdapter(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = from(parent.getContext());
        LayoutTransactionListViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_transaction_list_view, parent, false);
        return new TransactionHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        holder.bind(transactions.get(position));
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }

    public void updateList(List<Transaction> newTransactions) {
        if (newTransactions == null || newTransactions.size() == 0) {
            return;
        }
        List<Transaction> newList = newTransactions.subList(0,
                newTransactions.size() > numberOfItems ? numberOfItems : newTransactions.size());
        TransactionDiffUtilCallback diff = new TransactionDiffUtilCallback(transactions, newList);
        DiffUtil.DiffResult diffResult=  DiffUtil.calculateDiff(diff, true);
        diffResult.dispatchUpdatesTo(this);
        transactions = newList;
    }

}
