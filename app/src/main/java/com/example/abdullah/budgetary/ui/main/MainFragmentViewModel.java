package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Transaction;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {
    private LiveData<Double> mIncomeSummary;
    private LiveData<Double> mExpenseSummary;

    private LiveData<List<Transaction>> mLastTransactions;

    MainFragmentViewModel(BudgetaryRepository repository) {
        mLastTransactions = repository.getTransactions();
        mIncomeSummary = repository.getIncomeSummary();
        mExpenseSummary = repository.getExpenseSummary();
    }



    public LiveData<Double> getIncomeSummary() {
        return mIncomeSummary;
    }

    public void setIncomeSummary(LiveData<Double> incomeSummary) {
        this.mIncomeSummary = incomeSummary;
    }

    public LiveData<Double> getExpenseSummary() {
        return mExpenseSummary;
    }

    public void setExpenseSummary(LiveData<Double> expenseSummary) {
        this.mExpenseSummary = expenseSummary;
    }

    public LiveData<List<Transaction>> getLastTransactions() {
        return mLastTransactions;
    }

    public void setLastTransactions(LiveData<List<Transaction>> lastTransactions) {
        this.mLastTransactions = lastTransactions;
    }


}
