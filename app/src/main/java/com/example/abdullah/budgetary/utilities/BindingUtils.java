package com.example.abdullah.budgetary.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyTextFormatter;
import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.CustomLong;
import com.example.abdullah.budgetary.data.Icon;

import java.util.Hashtable;
import java.util.Locale;

public class BindingUtils {
    private static String packageName = null;
    private static Hashtable<String, Integer> icons= new Hashtable<>();

    @BindingAdapter({"android:src"})
    public static void setImageResource(ImageView imageView, String resourceName) {
        if(resourceName == null){
            imageView.setImageResource(R.drawable.ic_error_outline_red_500_24dp);
            return;
        }

        imageView.setImageResource(icons.get(resourceName));

        if(imageView.getDrawable() == null){
            imageView.setImageResource(R.drawable.ic_error_outline_red_500_24dp);
        }


    }

    @BindingAdapter({"android:tint"})
    public static void setDrawableColor(ImageView imageView, @ColorInt int color) {
        if(imageView.getDrawable() != null)
            imageView.getDrawable().setTint(color);
    }

    @BindingAdapter({"android:text"})
    public static void setText(TextView view, CustomLong d) {
        if(d == null){
            view.setText(R.string.not_avaible);
            return;
        }
        Locale locale = view.getResources().getConfiguration().locale;
        String text = CurrencyTextFormatter.formatText(d.amount.toString(), locale);
        view.setText(text);
    }

    public static void loadIcons(Context context, BudgetaryRepository repository) {
        packageName = context.getPackageName();
        repository.getAllIcons().observeForever(ic -> AppExecutors.getInstance().diskIO().execute(() -> {
            Resources res = context.getResources();
            for(Icon i : ic){
                int id = res.getIdentifier(i.getName(), "drawable", packageName);
                icons.put(i.getName(), id);
            }
        }));
    }

/*
    @BindingAdapter({"android:bind"})
    public static void setIcon(ImageView view, Icon icon) {
        ((LayoutIconHolderBinding) view).setIcon(icon);
    }
*/

}
