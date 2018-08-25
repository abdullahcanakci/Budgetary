package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.abdullah.budgetary.utilities.CurrencyUtility;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import java.util.Date;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private long id;


    private Date date;
    private String note;
    private boolean isIncome = true;
    private Long amount;
    @Embedded(prefix = "cat_")
    private Category category;

    public Transaction(long id, Date date, String note, boolean isIncome, Long amount, Category category) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.isIncome = isIncome;
        this.amount = amount;
        this.category = category;
    }

    @Ignore
    public Transaction() {

    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    public String getAmountInfo() {
        return String.valueOf(amount);
    }

    public String getTransactionInfo() {
        return note;
}

    public String getHourInfo() {
        return DateUtilities.getHourInfo(date);
    }

    public String getDateRelationInfo() {
        return DateUtilities.getDateRelation(date);
    }

    public int getIcon() {
        return category.getResId();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }


}
