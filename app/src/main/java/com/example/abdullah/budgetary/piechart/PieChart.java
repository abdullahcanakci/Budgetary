package com.example.abdullah.budgetary.piechart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.piechart.data.PieData;

import java.util.List;

public class PieChart extends ViewGroup implements View.OnTouchListener{
    PieData data;
    boolean isLegendVisible = false;
    boolean isBuilt = false;
    private PieSlice sliceInFocus = null;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        data = new PieData();
        this.setOnTouchListener(this);
    }

    int dragDeadZone = 15;
    int dragOrigin = -1;
    int dragDirection = 0;
    boolean inDrag = false;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int degree = calculateTouchAngle(event.getX(), event.getY());
            dragOrigin = degree;
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            int degree = calculateTouchAngle(event.getX(), event.getY());

            int delta = degree -dragOrigin;
            if(!inDrag)
                if(Math.abs(delta) < dragDeadZone){
                    inDrag = true;
                    return true;
                }
            dragOrigin = degree;
            if(delta < -300) {
                delta = delta + 360;
            } else if (delta > 300) {
                delta = delta - 360;
            }
            Log.d("Drag", "Delta: " + delta + " Origin: " + dragOrigin);
            if(delta != 0) {
                data.setStartOffset(delta);
                dragDirection = delta;
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(!inDrag) {
                int degree = calculateTouchAngle(event.getX(), event.getY());
                calculateTouchSlice(degree);
            }
            if(inDrag) {
                data.startDragDrift(dragDirection);
            }
            inDrag = false;
            dragOrigin = -1;
        }

        return false;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int calculateTouchAngle(float x, float y) {
        int width = getWidth();
        int height = getHeight();

        //Works
        float deltaX = x > getWidth() / 2 ? x - getWidth() / 2 : -getWidth() / 2 + x;
        float deltaY = y > getHeight() / 2 ?  + getHeight() / 2 - y : - y + getHeight() / 2;

        double result = Math.toDegrees(Math.atan2(deltaY, deltaX));


        int degree = result < 0 ? (int) -result : ((int) (360 - result));
        Log.d("PieChart", "calculateTouchAngle: " + degree);
        return degree;
    }

    private synchronized void calculateTouchSlice(int degree) {
        PieSlice clickedSlice = data.getSliceAtDegree(degree);
        if (clickedSlice == null)
            return;

        if(sliceInFocus == null) {
            sliceInFocus = clickedSlice;
            sliceInFocus.expand();
            return;
        }

        if(sliceInFocus == clickedSlice) {
            sliceInFocus.shrink();
            sliceInFocus = null;
            return;
        }
        sliceInFocus.shrink();
        sliceInFocus = clickedSlice;
        sliceInFocus.expand();
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
