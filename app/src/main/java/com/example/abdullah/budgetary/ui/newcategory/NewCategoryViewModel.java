package com.example.abdullah.budgetary.ui.newcategory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.ColorInt;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Icon;

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
    private int seekBarPosition;

    NewCategoryViewModel(Context context, BudgetaryRepository repository) {
        this.repository = repository;

        repository.getAllIcons().observeForever((icons) ->{
            this.icons.setValue(icons);
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
        category.setColor(color);
        category.setDescription(getCategoryDescription());
        icon.setDescription(getCategoryDescription());
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

    public void setSeekBarPosition(int barPos) {
        this.seekBarPosition = barPos;
    }

    public int getSeekBarPosition() {
        return seekBarPosition;
    }

    public Icon getIcon() {
        return icon;
    }
}
