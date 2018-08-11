package com.example.abdullah.budgetary.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.abdullah.budgetary.data.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category... category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getCategories();

    @Query("DELETE FROM categories WHERE id = :id")
    void deleteCategory(int id);
}
