package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.abdullah.budgetary.data.BudgetaryRepository;

public class MainFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final BudgetaryRepository mRepository;

    public MainFragmentViewModelFactory(BudgetaryRepository repository) {
        mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainFragmentViewModel(mRepository);
    }
}
