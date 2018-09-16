package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.utilities.DateUtilities;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private long id;


    private LocalDateTime date;
    private String note;
    private boolean isIncome = true;
    private Long amount;
    @Embedded(prefix = "cat_")
    private Category category;

    public Transaction(long id, LocalDateTime date, String note, boolean isIncome, Long amount, Category category) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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


    public CustomLong getAmountInfo() {
        return new CustomLong(amount);
    }

    public String getTransactionInfo() {
        return "Lorem ipsum dolor sit amet";
}

    public String getHourInfo() {
        return DateUtilities.getHourInfo(date);
    }

    public String getDateRelationInfo() {
        return DateUtilities.getDateRelation(date);
    }

    public String getFullDateRelation(){
        return getHourInfo() + ", " + getDateRelationInfo();
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

    public int getStatusDrawable() {
        return isIncome ? R.drawable.arrow_income : R.drawable.arrow_expense;
    }


}
