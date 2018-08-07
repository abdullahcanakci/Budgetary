package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Transaction;

import java.util.Date;
import java.util.List;

class MainFragmentViewModel extends ViewModel {
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

    public void setIncomeSummary(LiveData<Double> mIncomeSummary) {
        this.mIncomeSummary = mIncomeSummary;
    }

    public LiveData<Double> getExpenseSummary() {
        return mExpenseSummary;
    }

    public void setExpenseSummary(LiveData<Double> mExpenseSummary) {
        this.mExpenseSummary = mExpenseSummary;
    }

    public LiveData<List<Transaction>> getLastTransactions() {
        return mLastTransactions;
    }

    public void setLastTransactions(LiveData<List<Transaction>> mLastTransactions) {
        this.mLastTransactions = mLastTransactions;
    }
}
