package com.example.abdullah.budgetary.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.abdullah.budgetary.data.Icon;

import java.util.List;

@Dao
public interface IconDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Icon... icons);

    @Query("SELECT * FROM icons")
    LiveData<List<Icon>> getAllIcons();

    @Query("SELECT * FROM icons WHERE id = :id")
    LiveData<Icon> getIconById(int id);
}
