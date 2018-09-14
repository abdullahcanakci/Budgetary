package com.example.abdullah.budgetary.ui.perioddetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Period;
import com.example.abdullah.budgetary.data.Transaction;

import java.util.List;

public class PeriodDetailViewModel extends ViewModel {
    private BudgetaryRepository repository;
    private MutableLiveData<Period> period = new MutableLiveData<>();
    private final LiveData<List<Transaction>> transactions = Transformations.switchMap(period, (period) -> {
        return repository.getTransactionsByPeriod(period);
    });

    public PeriodDetailViewModel(BudgetaryRepository repository) {
        this.repository = repository;
    }

    public void setPeriod(Period period) {
        this.period.setValue(period);
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }

    public LiveData<List<Period>> getPeriods() {
        return repository.getAllPeriods();
    }
}
