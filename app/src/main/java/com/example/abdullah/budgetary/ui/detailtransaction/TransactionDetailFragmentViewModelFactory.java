package com.example.abdullah.budgetary.ui.detailtransaction;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.abdullah.budgetary.data.BudgetaryRepository;

public class TransactionDetailFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final BudgetaryRepository repository;
    private long transactionId;

    public TransactionDetailFragmentViewModelFactory(BudgetaryRepository repository, long transactionId) {
        this.repository = repository;
        this.transactionId = transactionId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new TransactionDetailFragmentViewModel(repository, transactionId);
    }
}
