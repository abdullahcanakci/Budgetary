package com.example.abdullah.budgetary.ui.perioddetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.abdullah.budgetary.data.BudgetaryRepository;

public class PeriodDetailFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final BudgetaryRepository repository;

    public PeriodDetailFragmentViewModelFactory(BudgetaryRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new PeriodDetailViewModel(repository);
    }
}
