package com.example.abdullah.budgetary.ui.newcategory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.AssetManager;
import android.databinding.ObservableField;
import android.support.annotation.ColorInt;
import android.util.Log;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.utilities.AppExecutors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewCategoryViewModel extends ViewModel {
    private static final String TAG = "NewCategoryViewModel";
    private MutableLiveData<List<Icon>> icons = new MutableLiveData<>();
    private Category category = new Category();
    private BudgetaryRepository repository;

    private ObservableField<String> categoryName = new ObservableField<>("");
    private ObservableField<String> categoryDescription = new ObservableField<>("");
    private ObservableField<Boolean> expense = new ObservableField<>(false);
    private ObservableField<Boolean> income = new ObservableField<>(false);
    @ColorInt
    private int color = 0xFF37474F;
    private Icon icon;

    NewCategoryViewModel(Context context, BudgetaryRepository repository) {
        this.repository = repository;
        AssetManager assetManager = context.getAssets();

        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                String[] paths = assetManager.list("categories");
                Log.d(TAG, "Number of items inside assets folder:" + paths.length);

                if(paths.length != 0) {
                    //Log.d(TAG, "There is no assets under category/");
                    ArrayList<Icon> iconList = new ArrayList<>();
                    for (String s: paths) {
                        Icon i = new Icon(0L, color, s, null);
                        iconList.add(i);
                    }
                    this.icons.postValue(iconList);
                }
            } catch (IOException e) {
                Log.d(TAG, "Can't load assets from category folder");
            }
        });
    }

    public MutableLiveData<List<Icon>> getIcons() {
        return icons;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSelectedColor() {
        return color;
    }

    public Category getCategory() {
        category.setName(getCategoryName());
        category.setExpense(isExpense());
        category.setIncome(isIncome());
        category.setDescription(getCategoryDescription());
        icon.setIconDescription(getCategoryDescription());
        icon.setColor(getSelectedColor());
        category.setIcon(icon);
        return category;
    }

    public String getDescription() {
        return categoryDescription.get();
    }
    public void setCategoryDescription(String s) {
        this.categoryDescription.set(s);
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public String getCategoryDescription() {
        return categoryDescription.get();
    }

    public boolean isExpense() {
        try {
            return expense.get();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void setExpense(boolean expense) {
        this.expense.set(expense);
    }

    public boolean isIncome() {
        try {
            return income.get();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void setIncome(boolean income) {
        this.income.set(income);
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void insertCategory(Category cat) {
        repository.addCategory(cat);
    }
}
