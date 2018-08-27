package com.example.abdullah.budgetary.utilities;

import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.piechart.PieChartAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends PieChartAdapter {
    private List<Category> category;

    public ChartAdapter(ArrayList<Category> categories) {
        super();
        category = categories;
    }

    public ChartAdapter() {

    }

    @Override
    public Long getValue(int index) {
        return 25L;
    }

    @Override
    public long getId(int index) {
        return category.get(index).getId();
    }

    @Override
    public String getLabel(int index) {
        return category.get(index).getName();
    }

    @Override
    public int getColor(int index) {
        return category.get(index).getBackgroundColor();
    }

    @Override
    public int getDrawable(int index) {
        return category.get(index).getResId();
    }

    @Override
    public int getItemCount() {
        return category == null? 0 : category.size();
    }

    public void addSlice(Category category){
        this.category.add(category);
        this.notifyItemInserted(getItemCount() - 1);

    }

    public void addSlices(List<Category> categories){
        this.category = categories;
        this.notifyDataSetChanged();
    }
}
