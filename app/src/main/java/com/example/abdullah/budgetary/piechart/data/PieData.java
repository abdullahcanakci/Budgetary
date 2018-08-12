package com.example.abdullah.budgetary.piechart.data;

import android.util.Log;

import com.example.abdullah.budgetary.piechart.PieSlice;
import com.example.abdullah.budgetary.piechart.interfaces.PieDataListener;

import java.util.ArrayList;
import java.util.List;

public class PieData{
    private List<PieSlice> slices;
    private boolean isLegendVisivle = false;
    private PieDataListener pieDataListener;
    public PieData() {
        slices = new ArrayList<>();
    }

    public void setPieDataListener(PieDataListener pieDataListener) {
        this.pieDataListener = pieDataListener;
    }

    public boolean addSlice(PieSlice slice){
        if(slices.contains(slice))
            return false;
        slices.add(slice);
        return true;
    }

    private void calculateAngles() {
        //int startAngle = -90; //Start from the top
        double total = 0;
        int degreePerSliceData;
        int startAngle = 0;
        for(PieSlice slice : slices) {
            total += slice.getSliceData().getValue();
        }

        degreePerSliceData = ((int) (360 / total));

        for(int i = 0; i < slices.size(); i++) {
            PieSlice slice = slices.get(i);
            int degree = ((int) (slice.getSliceData().getValue() * degreePerSliceData));
            if (i == slices.size() - 1) {
                slice.getSliceData().setStartAngle(startAngle);
                slice.getSliceData().setSweepAngle(360 - startAngle);
                return;
            }
            slice.getSliceData().setStartAngle(startAngle);
            slice.getSliceData().setSweepAngle(degree);
            startAngle += degree;
        }
    }

    public void update(){
        Log.d("PieData", "number of slices" + slices.size());
        calculateAngles();
        for (PieSlice p: slices)
            p.invalidate();
    }

    public List<PieSlice> getSlices() {
        return slices;
    }

    public void addSlice(List<PieSlice> slices){
        List<PieSlice> filteredSlices = joinLists(this.slices, slices);
    }

    private List<PieSlice> joinLists(List<PieSlice> listOne, List<PieSlice> listTwo) {
        ArrayList<PieSlice> newList = new ArrayList<>(listOne.size() + listTwo.size());
        newList.addAll(listOne);
        for(PieSlice o: listTwo) {
            if(!listOne.contains(o)) {
                newList.add(o);
            }
        }
        newList.trimToSize();
        return newList;
    }

    public PieSlice getSliceAtDegree(int degree) {
        for(PieSlice p : slices) {
            if(p.getSliceData().getStartAngle() < degree && p.getSliceData().getStartAngle() + p.getSliceData().getSweepAngle() > degree)
                return p;
        }
        return null;
    }
}
