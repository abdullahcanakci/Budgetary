package com.example.abdullah.budgetary.piechart.interfaces;

import com.example.abdullah.budgetary.piechart.PieSlice;

public interface PieDataListener {
    void notifyDatasetChanged();
    void notifyDataInserted(PieSlice slice);
}
