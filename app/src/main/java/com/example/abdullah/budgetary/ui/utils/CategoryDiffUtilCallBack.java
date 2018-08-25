package com.example.abdullah.budgetary.ui.utils;

import android.support.v7.util.DiffUtil;

import com.example.abdullah.budgetary.data.Category;

import java.util.List;

public class CategoryDiffUtilCallBack extends DiffUtil.Callback {

    private final List<Category> listOld;
    private final List<Category> listNew;

    public CategoryDiffUtilCallBack(List<Category> listOld, List<Category> listNew) {
        this.listOld = listOld;
        this.listNew = listNew;
    }

    @Override
    public int getOldListSize() {
        return listOld != null ? listOld.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return listNew != null ? listNew.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return listOld.get(i) == listNew.get(i1);

    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return listOld.get(i).equals(listNew.get(i1));
    }
}
