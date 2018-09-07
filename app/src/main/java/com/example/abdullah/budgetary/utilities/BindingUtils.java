package com.example.abdullah.budgetary.utilities;

import android.content.res.AssetManager;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyTextFormatter;
import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.CustomLong;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class BindingUtils {

    @BindingAdapter({"android:src"})
    public static void setImageResource(ImageView imageView, String resourceName) {
        //The though behind this was to create some sort of caching to reduce disk io
        //Problem is tinting one view updates the drawable and all views are changing color
        Log.d("AssetLoader", "Loading asset: " +resourceName);
        Drawable drawable = null;
        InputStream stream = null;
        AssetManager assetManager = imageView.getContext().getAssets();
        try {
            stream = assetManager.open("categories/" + resourceName);
            drawable = Drawable.createFromStream(stream, null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        drawable.setTint(imageView.getResources().getColor(R.color.icon_default));
        imageView.setImageDrawable(drawable);

    }

    @BindingAdapter({"android:tint"})
    public static void setDrawableColor(ImageView imageView, @ColorInt int color) {
        imageView.getDrawable().setTint(color);
    }

    @BindingAdapter({"android:text"})
    public static void setText(TextView view, CustomLong d) {
        Locale locale = view.getResources().getConfiguration().locale;
        String text = CurrencyTextFormatter.formatText(d.amount.toString(), locale);
        view.setText(text);
    }

/*
    @BindingAdapter({"android:bind"})
    public static void setIcon(ImageView view, Icon icon) {
        ((LayoutIconHolderBinding) view).setIcon(icon);
    }
*/

}
