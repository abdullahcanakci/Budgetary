package com.example.abdullah.budgetary.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.abdullah.budgetary.data.database.BudgetaryDatabase;
import com.example.abdullah.budgetary.data.database.TransactionDao;
import com.example.abdullah.budgetary.utilities.AppExecutors;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import java.util.List;

public class BudgetaryRepository {
    private static final String TAG = "BudgetaryRepositry";

    private static final Object LOCK = new Object();
    private static BudgetaryRepository sInstance;
    private final TransactionDao mTransactionDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private BudgetaryRepository(TransactionDao transactionDao, AppExecutors appExecutors) {

        mTransactionDao = transactionDao;
        mExecutors = appExecutors;
    }

    public synchronized static BudgetaryRepository getInstance(TransactionDao transactionDao, AppExecutors executors){
        Log.d(TAG, "Getting the repository");
        if(sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BudgetaryRepository(transactionDao, executors);
                Log.d(TAG, "Created new repository");
            }
        }
        return sInstance;
    }



    public LiveData<List<Transaction>> getTransactions(){
        return mTransactionDao.getTransactionsFromDate(DateUtilities.periodStart());

    }

    public LiveData<Double> getIncomeSummary() {
        return mTransactionDao.getIncomeSummary(DateUtilities.periodStart());
    }

    public LiveData<Double> getExpenseSummary() {
        return mTransactionDao.getExpenseSummary(DateUtilities.periodStart());

    }

}
