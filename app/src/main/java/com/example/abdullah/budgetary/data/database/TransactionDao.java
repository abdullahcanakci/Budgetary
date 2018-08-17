package com.example.abdullah.budgetary.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.abdullah.budgetary.data.Transaction;

import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Transaction... transactions);

    @Query("SELECT * FROM transactions WHERE date >= :date  ORDER BY date DESC ")
    LiveData<List<Transaction>> getTransactionsFromDate(Date date);

    @Query("SELECT * FROM transactions WHERE date >= :before AND date < :after")
    LiveData<List<Transaction>> getTransactionsBetweenDates(Date before, Date after);

    @Query("SELECT * FROM transactions WHERE id = :id")
    LiveData<Transaction> getTransaction(long id);

    @Query("SELECT SUM(amount) FROM transactions WHERE date >= :before AND isIncome")
    LiveData<Double> getIncomeSummary(Date before);

    @Query("SELECT SUM(amount) FROM transactions WHERE date >= :before AND NOT(isIncome)")
    LiveData<Double> getExpenseSummary(Date before);


    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM transactions WHERE id = :id")
    void deleteTransaction(long id);

    @Query("SELECT COUNT(id) FROM transactions")
    int getCount();

    @Query("SELECT * FROM transactions WHERE cat_id = :id ORDER BY date DESC LIMIT :limit")
    LiveData<List<Transaction>> getTransactionsByCategory(long id, int limit);

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT :limit")
    LiveData<List<Transaction>> getTransactions(int limit);

    @Query("SELECT SUM(amount) FROM TRANSACTIONS WHERE cat_id = :id AND date > :date")
    double getExpenseSummaryByCategory(long id, Date date);
}
