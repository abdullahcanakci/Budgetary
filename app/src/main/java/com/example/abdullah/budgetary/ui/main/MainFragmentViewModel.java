package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Transaction;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {
    private LiveData<Double> mIncomeSummary;
    private LiveData<Double> mExpenseSummary;

    private LiveData<List<Transaction>> transactions;
    private LiveData<List<Category>> categories;
    private BudgetaryRepository repo;

    MainFragmentViewModel(BudgetaryRepository repository) {
        transactions = repository.getTransactions();
        mIncomeSummary = repository.getIncomeSummary();
        mExpenseSummary = repository.getExpenseSummary();
        categories = repository.getCategories();
        repo =repository;
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
        return transactions;
    }

    public void setLastTransactions(LiveData<List<Transaction>> lastTransactions) {
        this.transactions = lastTransactions;
    }


    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public void setCategories(LiveData<List<Category>> categories) {
        this.categories = categories;
    }

    public LiveData<List<Transaction>> setCategoryFocus(long categoryFocus) {
        return repo.getTransactionsByCategory(categoryFocus, 10);

    }

    public LiveData<List<Transaction>> clearCategoryFocus() {
        return repo.getTransactions(10);

    }
}
