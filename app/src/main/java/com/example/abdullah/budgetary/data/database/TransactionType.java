package com.example.abdullah.budgetary.data.database;

import com.example.abdullah.budgetary.R;

import java.util.ArrayList;

public enum TransactionType {
    //TYPE(isExpense, isIncome, backgroundColor, id)
    WAGE(0, true, true, 0),
    CHILD_SUPPORT(1, true, true, 0),
    UTILITY(2, true, true, 0),
    MAINTENANCE(3, true, false, 0),
    SHOPPING(4, true, false, 0),
    FOOD(5, true, false, 0),
    VACATION(6, true, false, 0),
    RENT(7, true, true, 0),
    GAMBLE(8, true, true, 0),
    PRIZE(9, false, true, 0),
    STOCK(10, true, true, 0),
    SCHOLARSHIP(11, false, true, 0),
    SCHOOL(12, true, false, 0),
    MORTGAGE(13, true, false, 0);



    public final int id;
    public final boolean isExpense;
    public final boolean isIncome;
    public int backgroundColor;
    TransactionType(int id, boolean isExpense, boolean isIncome, int backgroundColor){
        this.id = id;
        this.isExpense = isExpense;
        this.isIncome = isIncome;
        this.backgroundColor = backgroundColor;
    }

    public static TransactionType[] getExpenses() {
        ArrayList<TransactionType> expenses = new ArrayList<>();
        for(TransactionType t : TransactionType.values()){
            if (t.isExpense) {
                expenses.add(t);
            }
        }
        return ((TransactionType[]) expenses.toArray());
    }

    public static TransactionType[] getIncomes() {
        ArrayList<TransactionType> expenses = new ArrayList<>();
        for(TransactionType t : TransactionType.values()){
            if (t.isIncome) {
                expenses.add(t);
            }
        }
        return ((TransactionType[]) expenses.toArray());
    }

    public int getColor() {
        if(backgroundColor == 0) {
            return android.R.color.holo_green_light;
        }
        return backgroundColor;
    }

    public static TransactionType getType(int id) {
        for (TransactionType t : TransactionType.values()) {
            if(t.id == id) {
                return t;
            }
        }
        return null;
    }

    public int getIcon() {
        return R.drawable.money;
    }
}
