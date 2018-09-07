package com.example.abdullah.budgetary.ui.newcategory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.abdullah.budgetary.data.BudgetaryRepository;

public class NewCategoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Context context;
    private final BudgetaryRepository repository;

        public NewCategoryViewModelFactory(Context context, BudgetaryRepository repository) {
            this.context = context;
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new NewCategoryViewModel(context, repository);
        }
    }
