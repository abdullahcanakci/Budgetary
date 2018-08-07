package com.example.abdullah.budgetary.utilities;

import java.text.NumberFormat;

public class CurrencyUtility {
    public static String getFormattedCurrency(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(amount);
    }
}
