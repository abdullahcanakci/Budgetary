package com.example.abdullah.budgetary.piechart;

import java.util.ArrayList;
import java.util.List;

public abstract class PieChartAdapter implements PieSliceInterface {
    protected List<PieSlice> slices = new ArrayList<>();
    private PieChart chart;
    protected void setChart(PieChart chart) {
        this.chart = chart;
    }

    protected PieSlice getSlice(int id){
        for(PieSlice s: slices)
            if(s.getSliceId() == id) return s;
        return null;
    }

    protected void addSlice(PieSlice slice) {
        for(PieSlice s: slices){
            if(s.getSliceId() == slice.getSliceId()) return;
        }
        slices.add(slice);
        chart.updateView();
    }

    protected void notifyDataSetChanged(){
        //TODO remove all slices
        slices.clear();
        for(int i  = 0; i < getItemCount(); i++) {
            PieSlice slice = chart.getNewSliceInstance();
            slice.setSliceId(this.getId(i));
            slice.setColor(this.getColor(i));
            slices.add(slice);
        }
        if(chart != null)
            chart.updateView();
    }

    protected void notifyItemInserted(int index) {
        PieSlice slice = chart.getNewSliceInstance();
        slice.setSliceId(getId(index));
        slice.setColor(this.getColor(index));
        this.addSlice(slice);

    }

    protected double getTotalValue(){
        double total = 0.0f;

        for (int i = 0; i < getItemCount(); i ++) {
            total += getValue(i);
        }
        return total;
    }

}
