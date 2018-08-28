package com.example.abdullah.budgetary.utilities;

import android.arch.lifecycle.LiveData;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyTextFormatter;
import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.CustomLong;

import java.util.Locale;

public class BindingUtils {
    @BindingAdapter({"android:src"})
    public static void setImageResource(ImageView imageView, int resourceId) {
        try {
            imageView.setImageResource(resourceId);
        } catch (Resources.NotFoundException ex) {
            Log.d("BindingUtils", "setImageResource: resource id is wrong. Please reinstall application");
            imageView.setImageResource(R.drawable.ic_error_red_500_24dp);
        }
    }

    @BindingAdapter({"android:tint"})
    public static void setDrawableColor(ImageView imageView, int resourceId) {
        int color = ContextCompat.getColor(imageView.getContext(), resourceId);
        imageView.getDrawable().setTint(color);
    }

    @BindingAdapter({"android:text"})
    public static void setText(TextView view, CustomLong d) {
        Locale locale = view.getResources().getConfiguration().locale;
        String text = CurrencyTextFormatter.formatText(d.amount.toString(), locale);
        view.setText(text);
    }

}
