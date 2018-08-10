package com.example.abdullah.budgetary.utilities;

import android.arch.lifecycle.LiveData;
import android.databinding.BindingAdapter;
import android.widget.TextView;

public class BindingUtils {
    @BindingAdapter("android:text")
    public static void setText(TextView textView, LiveData<Double> data) {
        textView.setText(CurrencyUtility.getFormattedCurrency(data.getValue()));
    }
}
