package com.example.abdullah.budgetary.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.abdullah.budgetary.data.database.CategoryDao;
import com.example.abdullah.budgetary.data.database.IconDao;
import com.example.abdullah.budgetary.data.database.TransactionDao;
import com.example.abdullah.budgetary.utilities.AppExecutors;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import java.util.List;

public class BudgetaryRepository {
    private static final String TAG = "BudgetaryRepository";

    private static final Object LOCK = new Object();
    private static BudgetaryRepository sInstance;
    private final TransactionDao transactionDao;
    private final CategoryDao categoryDao;
    private final AppExecutors executors;
    private boolean mInitialized = false;
    private LiveData<List<Transaction>> transactions;
    private LiveData<Long> expenseSummary;
    private LiveData<Long> incomeSummary = new MutableLiveData<>();
    private boolean isInitialized = false;
    private IconDao iconDao;

    private BudgetaryRepository(TransactionDao transactionDao, CategoryDao categoryDao, IconDao iconDao, AppExecutors appExecutors) {
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
        this.iconDao = iconDao;
        this.executors = appExecutors;
        initializeData();
    }

    public synchronized static BudgetaryRepository getInstance(TransactionDao transactionDao, CategoryDao categoryDao, IconDao iconDao, AppExecutors executors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BudgetaryRepository(transactionDao, categoryDao, iconDao, executors);
                Log.d(TAG, "Created new repository");
            }
        }
        return sInstance;
    }

    private void initializeData() {
        if (isInitialized)
            return;
        isInitialized = true;

        transactions = transactionDao.getTransactionsFromDate(DateUtilities.periodStart());
        incomeSummary = transactionDao.getIncomeSummary(DateUtilities.periodStart());
        expenseSummary = transactionDao.getExpenseSummary(DateUtilities.periodStart());
    }

    public LiveData<List<Transaction>> getTransactions() {
        if (!isInitialized)
            initializeData();
        return transactions;

    }

    public void addTransaction(Transaction t) {
        executors.diskIO().execute(() -> {
            transactionDao.bulkInsert(t);
            categoryDao.updateCategoryValue(t.getCategory().getId(), t.getAmount() + t.getCategory().getValue());
        });
    }

    public void addCategory(Category category) {
        executors.diskIO().execute(() -> {
            categoryDao.insert(category);
        });
    }

    public void addCategories(List<Category> categories){
        executors.diskIO().execute(() -> {
            categoryDao.insert(categories);
        });
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getCategories();
    }

    public Long getTransactionValueByCategory(long id) {
        return transactionDao.getExpenseSummaryByCategory(id, DateUtilities.periodStart());
    }

    public LiveData<List<Transaction>> getTransactionsByCategory(long id, int limit) {
        return transactionDao.getTransactionsByCategory(id, limit);
    }

    public LiveData<List<Transaction>> getTransactions(int limit){
        return transactionDao.getTransactions(limit);
    }

    public LiveData<List<Category>> getCategoryByType(Boolean isExpense) {
        return categoryDao.getCategoriesByType(isExpense, !isExpense);
    }

    public LiveData<List<Category>> getCategoriesUsed() {
        return categoryDao.getCategoriesBySpending();
    }

    public LiveData<Transaction> getTransactionById(long transactionId) {
        return transactionDao.getTransaction(transactionId);
    }

    public void addIcons(List<Icon> icons) {
        executors.diskIO().execute(() -> {
            iconDao.insert((icons.toArray(new Icon[icons.size()])));
        });
    }

    public LiveData<List<Icon>> getAllIcons(){
        return iconDao.getAllIcons();
    }

    public LiveData<Icon> getIconById(int id) {
        return iconDao.getIconById(id);
    }
}
