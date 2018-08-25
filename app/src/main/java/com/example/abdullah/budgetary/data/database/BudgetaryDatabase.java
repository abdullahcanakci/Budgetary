package com.example.abdullah.budgetary.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.data.database.converters.DateConverter;


@Database(entities = {Transaction.class, Category.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BudgetaryDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "budgetary";
    private static final Object LOCK = new Object();
    private static volatile BudgetaryDatabase sInstance;

    //DAO'S
    public abstract TransactionDao transactionDao();
    public abstract CategoryDao categoryDao();

    public static BudgetaryDatabase getInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                if(sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), BudgetaryDatabase.class, BudgetaryDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }



}
