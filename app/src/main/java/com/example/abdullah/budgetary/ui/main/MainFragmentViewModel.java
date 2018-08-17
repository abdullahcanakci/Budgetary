package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.utilities.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private LiveData<List<Transaction>> transactions;
    private MutableLiveData<List<Category>> cat = new MutableLiveData<>();
    private BudgetaryRepository repo;

    MainFragmentViewModel(BudgetaryRepository repository) {
        transactions = repository.getTransactions();
        LiveData<List<Category>> categories = repository.getCategories();
        categories.observeForever((c) -> {
            cat.setValue(c);
            updateCategoryValuation();
        });
        repo =repository;
    }


    public LiveData<List<Transaction>> getLastTransactions() {
        return transactions;
    }

    public MutableLiveData<List<Category>> getCategories() {
        return cat;
    }

    public LiveData<List<Transaction>> setCategoryFocus(long categoryFocus) {
        return repo.getTransactionsByCategory(categoryFocus, 10);
    }

    public LiveData<List<Transaction>> clearCategoryFocus() {
        return repo.getTransactions(10);
    }

    public double getCategoryValue(long id) {
        return repo.getTransactionValueByCategory(id);
    }

    private void updateCategoryValuation() {
        if(cat.getValue() == null)
            return;
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Category> cats = new ArrayList<>();
            for(Category c : cat.getValue()) {
                c.setValue(getCategoryValue(c.getId()));
                cats.add(c);
            }
            cat.postValue(cats);
        });
    }

    public void updateCat() {
        updateCategoryValuation();
    }
}
