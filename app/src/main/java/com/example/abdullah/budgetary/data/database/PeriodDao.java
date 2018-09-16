package com.example.abdullah.budgetary.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.abdullah.budgetary.data.Period;

import java.util.List;

@Dao
public interface PeriodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Period> period);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Period p);

    @Query("SELECT * FROM periods")
    LiveData<List<Period>> getAllPeriods();

    @Query("DELETE FROM periods WHERE id != 100")
    void removeAllPeriods();
}
