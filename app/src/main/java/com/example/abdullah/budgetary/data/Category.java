package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private boolean isIncome;
    private boolean isExpense;
    private String iconName;
    private int categoryColor;
    private String description;
    private long value = 0L;

    public Category(long id, String name, boolean isIncome, boolean isExpense, String iconName, int categoryColor, String description, long value){
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
        this.isExpense = isExpense;
        this.iconName = iconName;
        this.categoryColor = categoryColor;
        this.description = description;
        this.value = value;
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

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Icon getIcon(){
        return new Icon(id, categoryColor, iconName, description);
    }
}
