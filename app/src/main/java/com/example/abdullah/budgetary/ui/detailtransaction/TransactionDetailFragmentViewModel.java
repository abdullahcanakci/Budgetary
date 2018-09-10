package com.example.abdullah.budgetary.ui.detailtransaction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Transaction;

public class TransactionDetailFragmentViewModel extends ViewModel {
    private LiveData<Transaction> transaction = null;
    public TransactionDetailFragmentViewModel(BudgetaryRepository repository, long transactionId) {
        transaction = repository.getTransactionById(transactionId);
    }


    public LiveData<Transaction> getTransaction() {
        return transaction;
    }
}
