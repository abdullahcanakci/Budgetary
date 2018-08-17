package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.abdullah.budgetary.R;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private boolean isIncome;
    private boolean isExpense;
    private int resId;
    private int backgroundColor;
    private String description;
    private double value;

    public Category(long id, String name, boolean isIncome, boolean isExpense, int resId, int backgroundColor, String description){
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
        this.isExpense = isExpense;
        this.resId = resId;
        this.backgroundColor = backgroundColor;
        this.description = description;
    }

    @Ignore
    public Category() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public int getResId() {
        return resId == 0 ? R.drawable.money : resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
