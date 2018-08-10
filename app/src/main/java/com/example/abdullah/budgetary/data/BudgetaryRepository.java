package com.example.abdullah.budgetary.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.abdullah.budgetary.data.database.TransactionDao;
import com.example.abdullah.budgetary.utilities.AppExecutors;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import java.util.List;

public class BudgetaryRepository {
    private static final String TAG = "BudgetaryRepository";

    private static final Object LOCK = new Object();
    private static BudgetaryRepository sInstance;
    private final TransactionDao mTransactionDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;
    private LiveData<List<Transaction>> transactions;
    private LiveData<Double> expenseSummary;
    private LiveData<Double> incomeSummary = new MutableLiveData<>();
    private boolean isInitialized = false;

    private BudgetaryRepository(TransactionDao transactionDao, AppExecutors appExecutors) {

        mTransactionDao = transactionDao;
        mExecutors = appExecutors;
        initializeData();
    }

    public synchronized static BudgetaryRepository getInstance(TransactionDao transactionDao, AppExecutors executors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BudgetaryRepository(transactionDao, executors);
                Log.d(TAG, "Created new repository");
            }
        }
        return sInstance;
    }

    private void initializeData() {
        if (isInitialized)
            return;
        isInitialized = true;

        transactions = mTransactionDao.getTransactionsFromDate(DateUtilities.periodStart());
        incomeSummary = mTransactionDao.getIncomeSummary(DateUtilities.periodStart());
        expenseSummary = mTransactionDao.getExpenseSummary(DateUtilities.periodStart());
    }

    public LiveData<List<Transaction>> getTransactions() {
        if (isInitialized)
            initializeData();
        return transactions;

    }

    public LiveData<Double> getIncomeSummary() {
        if (!isInitialized)
            initializeData();

        return incomeSummary;

    }

    public LiveData<Double> getExpenseSummary() {
        if (isInitialized)
            initializeData();
        return expenseSummary;

    }

    public void addTransaction(Transaction t) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mTransactionDao.bulkInsert(t);
        });
    }
}
