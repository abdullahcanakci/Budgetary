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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Category> categories);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getCategories();


    @Query("DELETE FROM categories WHERE id = :id")
    void deleteCategory(long id);

    @Query("SELECT * FROM categories WHERE isExpense = :isExpense OR isIncome = :isIncome")
    LiveData<List<Category>> getCategoriesByType(Boolean isExpense, Boolean isIncome);

    @Query("UPDATE categories SET value = :amount WHERE id = :id")
    void updateCategoryValue(long id, Long amount);

    @Query("SELECT * FROM categories WHERE value > 1")
    LiveData<List<Category>> getCategoriesBySpending();

}
