package com.example.abdullah.budgetary.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.data.database.converters.DateConverter;
import com.example.abdullah.budgetary.data.database.converters.TransactionTypeConverter;


@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, TransactionTypeConverter.class})
public abstract class BudgetaryDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "budgetary";
    private static final Object LOCK = new Object();
    private static volatile BudgetaryDatabase sInstance;

    //DAO'S
    public abstract TransactionDao transactionDao();

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
