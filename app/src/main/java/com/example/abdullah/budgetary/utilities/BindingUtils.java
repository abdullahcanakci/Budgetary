package com.example.abdullah.budgetary.utilities;

import android.arch.lifecycle.LiveData;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingUtils {
    @BindingAdapter({"android:text"})
    public static void setText(TextView textView, LiveData<Long> data) {
        textView.setText(new CurrencyFormatter(textView.getContext()).getFormattedCurrency(data.getValue()));
    }

    @BindingAdapter({"android:src"})
    public static void setImageResource(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }

    @BindingAdapter({"android:tint"})
    public static void setDrawableColor(ImageView imageView, int resourceId) {
        int color = ContextCompat.getColor(imageView.getContext(), resourceId);
        imageView.getDrawable().setTint(color);
    }

}
