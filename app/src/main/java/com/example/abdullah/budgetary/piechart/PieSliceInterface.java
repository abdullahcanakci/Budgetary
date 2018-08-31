package com.example.abdullah.budgetary.piechart;

public interface PieSliceInterface {
    Long getValue(int index);
    long getId(int index);
    String getLabel(int index);
    int getColor(int index);
    String getDrawable(int index);
    int getItemCount();
}
