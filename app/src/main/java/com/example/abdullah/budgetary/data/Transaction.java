package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.database.TransactionType;
import com.example.abdullah.budgetary.utilities.CurrencyUtility;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import java.util.Date;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private Date date;
    private String note;
    private boolean isIncome = true;
    private double amount;
    private TransactionType type;

    public Transaction(int id, Date date, String note, boolean isIncome, double amount, TransactionType type) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.isIncome = isIncome;
        this.amount = amount;
        this.type = type;
    }

    @Ignore
    public Transaction() {

    }

    public int getId() {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAmountInfo() {
        return CurrencyUtility.getFormattedCurrency(amount);
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
        return type.getIcon();
    }

}
