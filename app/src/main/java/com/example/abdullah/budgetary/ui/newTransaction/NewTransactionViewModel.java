package com.example.abdullah.budgetary.ui.newTransaction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;

import java.util.List;

public class NewTransactionViewModel extends ViewModel {
    private BudgetaryRepository repository;
    private MutableLiveData<Boolean> categoryType = new MutableLiveData<>();
    public final LiveData<List<Category>> categories = Transformations.switchMap(categoryType, (isExpense) -> {
        return repository.getCategoryByType(isExpense);
    });

    NewTransactionViewModel(BudgetaryRepository repository){
        this.repository = repository;
        setCategoryType(true);
    }

    public void setCategoryType(boolean isExpense){
        categoryType.setValue(isExpense);
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

}
