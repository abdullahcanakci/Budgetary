package com.example.abdullah.budgetary.utilities;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.blackcat.currencyedittext.CurrencyTextFormatter;

import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatter{
    private Locale locale;
    private Locale defaultLocale = Locale.US;
    private Currency currency;
    private int decimalDigit;

    public CurrencyFormatter(@NonNull Context context) {
        getLocale(context);
    }

    private void getLocale(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = context.getApplicationContext().getResources().getConfiguration().getLocales().get(0);
            } else {
                locale = context.getApplicationContext().getResources().getConfiguration().locale;
            }
        } catch (Exception e) {
            Log.v("CurrencFormatter", String.format("An error occurred while retrieving user Locale. Falling back to default Locale '%s'", defaultLocale), e);
            locale = defaultLocale;
        }
    }


    public String getFormattedCurrency(Long amount) {
        if(amount == null) {
            amount = 0L;
        }
        return CurrencyTextFormatter.formatText(amount.toString(), locale, defaultLocale);
    }
}
