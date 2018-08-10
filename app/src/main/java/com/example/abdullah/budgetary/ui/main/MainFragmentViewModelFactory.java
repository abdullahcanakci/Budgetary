package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.abdullah.budgetary.data.BudgetaryRepository;

public class MainFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final BudgetaryRepository mRepository;

    public MainFragmentViewModelFactory(BudgetaryRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainFragmentViewModel(mRepository);
    }
}
