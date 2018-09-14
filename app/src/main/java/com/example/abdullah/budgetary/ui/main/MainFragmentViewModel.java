package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Transaction;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private LiveData<List<Transaction>> transactions;
    private LiveData<List<Category>> categories;
    private BudgetaryRepository repo;

    MainFragmentViewModel(BudgetaryRepository repository) {
        transactions = repository.getTransactions(10);
        categories = repository.getCategoriesUsed();
        repo =repository;
    }


    public LiveData<List<Transaction>> getLastTransactions() {
        return transactions;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Transaction>> setCategoryFocus(long categoryFocus) {
        return repo.getTransactionsByCategory(categoryFocus, 10);
    }

    public LiveData<List<Transaction>> clearCategoryFocus() {
        return repo.getTransactions(10);
    }

    public Long getCategoryValue(long id) {
        return repo.getTransactionValueByCategory(id);
    }
}
