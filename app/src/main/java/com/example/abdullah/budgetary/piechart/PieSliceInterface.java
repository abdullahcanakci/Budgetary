package com.example.abdullah.budgetary.piechart;

public interface PieSliceInterface {
    double getValue(int index);
    long getId(int index);
    String getLabel(int index);
    int getColor(int index);
    int getDrawable(int index);
    int getItemCount();
}
