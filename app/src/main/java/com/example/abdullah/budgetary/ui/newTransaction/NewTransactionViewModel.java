package com.example.abdullah.budgetary.ui.newTransaction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;

import java.util.List;

public class NewTransactionViewModel extends ViewModel {
    LiveData<List<Category>> categories;

    NewTransactionViewModel(BudgetaryRepository repository){
        categories = repository.getCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

}
