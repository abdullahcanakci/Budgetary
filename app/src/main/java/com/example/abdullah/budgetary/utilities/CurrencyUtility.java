package com.example.abdullah.budgetary.utilities;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.blackcat.currencyedittext.CurrencyTextFormatter;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtility {
    public static String getFormattedCurrency(Double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(amount == null ? 0.0 : amount);
    }
}
