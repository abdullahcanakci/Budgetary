package com.example.abdullah.budgetary.utilities;

import android.content.Context;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.database.BudgetaryDatabase;
import com.example.abdullah.budgetary.ui.main.MainFragmentViewModelFactory;

public class InjectorUtils {
    public static BudgetaryRepository provideRepository(Context context) {
        BudgetaryDatabase database = BudgetaryDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return BudgetaryRepository.getInstance(database.transactionDao(), database.categoryDao(), executors);
    }


    public static MainFragmentViewModelFactory provideMainViewModelFactory(Context context){
        BudgetaryRepository repository = provideRepository(context.getApplicationContext());
        return new MainFragmentViewModelFactory(repository);
    }
}
