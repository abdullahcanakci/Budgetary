package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private boolean isIncome;
    private boolean isExpense;
    private String description;
    private long value = 0L;
    @ColorInt
    public int color;

    @Embedded(prefix = "_icon")
    private Icon icon;

    public Category(long id, String name, boolean isIncome, boolean isExpense, String description, long value, Icon icon){
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
        this.isExpense = isExpense;
        this.description = description;
        this.value = value;
        this.icon = icon;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Icon getIcon(){
        icon.setColor(color);
        icon.setDescription(description);
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
