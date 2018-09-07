package com.example.abdullah.budgetary.utilities;

import android.content.Context;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.database.BudgetaryDatabase;
import com.example.abdullah.budgetary.ui.main.MainFragmentViewModelFactory;
import com.example.abdullah.budgetary.ui.newTransaction.NewTransactionViewModelFactory;
import com.example.abdullah.budgetary.ui.newcategory.NewCategoryViewModelFactory;

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

    public static NewTransactionViewModelFactory provideNewFragmentViewModelFactory(Context context) {
        BudgetaryRepository repository = provideRepository(context.getApplicationContext());
        return new NewTransactionViewModelFactory(repository);
    }

    public static NewCategoryViewModelFactory provideNewCategoryViewModelFactory(Context context){
        BudgetaryRepository repository = provideRepository(context.getApplicationContext());
        return new NewCategoryViewModelFactory(context, repository);
    }
}
