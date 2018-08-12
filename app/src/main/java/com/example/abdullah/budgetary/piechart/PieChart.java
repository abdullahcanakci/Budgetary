package com.example.abdullah.budgetary.piechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.piechart.data.PieData;

import java.util.List;

public class PieChart extends ViewGroup{
    PieData data;
    boolean isLegendVisible = false;
    boolean isBuilt = false;
    PieSlice sliceInFocus = null;

    @SuppressLint("ClickableViewAccessibility")
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        data = new PieData();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    calculateTouchAngle(event.getX(), event.getY());
                return true;
            }
        });


    }

    private void calculateTouchAngle(float x, float y) {
        int width = getWidth();
        int height = getHeight();

        //Works
        float deltaX = x > getWidth() / 2 ? x - getWidth() / 2 : -getWidth() / 2 + x;
        float deltaY = y > getHeight() / 2 ?  + getHeight() / 2 - y : - y + getHeight() / 2;

        double result = Math.toDegrees(Math.atan2(deltaY, deltaX));


        int degree = result < 0 ? (int) -result : ((int) (360 - result));
        Log.d("PieChart", "calculateTouchAngle: " + degree);
        calculateTouchSlice(degree);
    }

    private void calculateTouchSlice(int degree) {
        if( sliceInFocus != null) {
            if(sliceInFocus.equals(data.getSliceAtDegree(degree))) {
                sliceInFocus.onTouch(false);
                sliceInFocus = null;
                return;
            } else  {
                sliceInFocus.onTouch(false);
                sliceInFocus = data.getSliceAtDegree(degree);
                sliceInFocus.onTouch(true);
                return;
            }
        }
        sliceInFocus = data.getSliceAtDegree(degree);
        sliceInFocus.onTouch(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(PieSlice view: data.getSlices()) {
            //view.invalidate(l,t,r,b);
            view.layout(l,t,r,b);
            view.startAnimation();
        }
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    private void drawLegend() {
        if(!isLegendVisible)
            return;
        //TODO implement legend drawing;
    }

    public void setLegendVisibility(boolean isVisible) {
        isLegendVisible = isVisible;
        invalidate();
    }

    public boolean addSlice(PieSlice slice) {
        if(data.addSlice(slice)) {
            this.addView(slice);
            data.update();
            invalidate();
            return true;
        }
        return false;
    }

    public boolean[] addSlice(List<PieSlice> slices) {
        boolean[] result = new boolean[slices.size()];

        for(int i = 0; i < slices.size(); i++) {
            result[i] = addSlice(slices.get(i));
        }
        return result;
    }

}
