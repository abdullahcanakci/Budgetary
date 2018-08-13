package com.example.abdullah.budgetary.piechart.data;

import android.animation.IntEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

import com.example.abdullah.budgetary.piechart.PieSlice;
import com.example.abdullah.budgetary.piechart.interfaces.PieDataListener;

import java.util.ArrayList;
import java.util.List;

public class PieData{
    private List<PieSlice> slices;
    private boolean isLegendVisivle = false;
    private PieDataListener pieDataListener;
    private int startOffset = 0;

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
        int startAngle = startOffset;
        if(startAngle < 360)
            startAngle += 360;
        startAngle %= 360;
        int totalDeg = 360;
        for(PieSlice slice : slices) {
            total += slice.getSliceData().getValue();
        }

        degreePerSliceData = ((int) (360 / total));

        for(int i = 0; i < slices.size(); i++) {
            PieSlice slice = slices.get(i);
            int sliceSweep = ((int) (slice.getSliceData().getValue() * degreePerSliceData));
            if (i == slices.size() - 1) {
                slice.getSliceData().setStartAngle(startAngle);
                slice.getSliceData().setSweepAngle(totalDeg);
                return;
            }
            slice.getSliceData().setStartAngle(startAngle);
            slice.getSliceData().setSweepAngle(sliceSweep);
            totalDeg -= sliceSweep;
            startAngle += sliceSweep;
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
        int start = 0;
        int end = 0;
        for(PieSlice p : slices) {
            start = p.getSliceData().getStartAngle();
            end = p.getSliceData().getSweepAngle() + start;
            if (end > 360){
                end %= 360;
                if(degree > start && degree <= 360 || degree > 0 && degree <= end)
                    return p;
            }
            else {
                if(degree > start && degree <= end)
                    return p;
            }
        }
        return null;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset += startOffset;
        update();
    }

    public void startDragDrift(int direction) {
        int startAngle;
        int endAngle = 0;
        startAngle = startOffset % 360;
        if(startAngle < 0)
            startAngle += 360;
        if(direction > 0) {
            endAngle = (startAngle + 30);
        }
        if (direction < 0) {
            endAngle = (startAngle - 30);
        }

        ValueAnimator anim = ValueAnimator.ofInt(startAngle, endAngle);
        Log.d("Drift", "start: " + startAngle + " end: " + endAngle);
        anim.setEvaluator(new IntEvaluator());
        anim.setDuration(500);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startOffset = ((int) animation.getAnimatedValue());
                update();
            }
        });
        anim.start();
    }
}
