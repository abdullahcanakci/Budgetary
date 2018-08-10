package com.example.abdullah.budgetary.utilities;

import java.text.NumberFormat;

public class CurrencyUtility {
    public static String getFormattedCurrency(Double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(amount == null ? 0.0 : amount);
    }
}
