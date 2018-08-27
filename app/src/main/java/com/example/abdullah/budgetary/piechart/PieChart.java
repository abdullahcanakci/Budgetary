package com.example.abdullah.budgetary.piechart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.piechart.interfaces.PieChartInterface;

public class PieChart extends ViewGroup implements View.OnTouchListener{
    boolean isLegendVisible = false;
    boolean isBuilt = false;
    private PieSlice sliceInFocus = null;
    private PieChartInterface listener;
    private PieChartAdapter adapter;
    private int startOffset;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.setOnTouchListener(this);
    }

    int dragDeadZone = 15;
    int dragOrigin = -1;
    int dragDirection = 0;
    boolean inDrag = false;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            dragOrigin = calculateTouchAngle(event.getX(), event.getY());
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
                startOffset += delta;
                dragDirection = delta;
                refreshView();
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(!inDrag) {
                int degree = calculateTouchAngle(event.getX(), event.getY());
                calculateTouchSlice(degree);
            }
            if(inDrag) {
                //TODO drift
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

    private void refreshView() {
        calculateAngles();
        for(PieSlice s: adapter.slices){
            s.invalidate();
        }
    }

    public void updateView() {
        calculateAngles();
        this.removeAllViews();
        for(PieSlice p : adapter.slices) {
            this.addView(p);
        }
        invalidate();
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
        PieSlice clickedSlice = getSliceAtDegree(degree);

            if (clickedSlice == null)
                return;

            if (sliceInFocus == null) {
                sliceInFocus = clickedSlice;
                sliceInFocus.expand();
                listener.onSliceFocusEntry(sliceInFocus.getSliceId());
                return;
            }

            if (sliceInFocus == clickedSlice) {
                sliceInFocus.shrink();
                listener.onSliceFocusExit(sliceInFocus.getSliceId());
                sliceInFocus = null;
                return;
            }
            sliceInFocus.shrink();
            sliceInFocus = clickedSlice;
            sliceInFocus.expand();
            listener.onSliceFocusEntry(sliceInFocus.getSliceId());

    }

    public PieSlice getSliceAtDegree(int degree) {
        float start = 0;
        float end = 0;
        for(PieSlice p : adapter.slices) {
            start = p.getStartAngle();
            end = p.getSweepAngle() + start;
            if (end > 360.0f){
                end %= 360.0f;
                if(degree > start && degree <= 360.0f || degree > 0.0f && degree <= end)
                    return p;
            }
            else {
                if(degree > start && degree <= end)
                    return p;
            }
        }
        return null;
    }

    private void calculateAngles() {
        //int startAngle = -90; //Start from the top
        double total = adapter.getTotalValue();
        float degreePerSliceData = ((float) (360.0f / total));
        float startAngle = startOffset;
        if(startAngle < 0.0f)
            startAngle += 360.0f;
        startAngle %= 360.0f;
        float remainingDegree = 360.0f;

        degreePerSliceData = ((float) (360 / total));

        int itemCount = adapter.getItemCount();

        for(int i = 0; i < itemCount; i++) {
            PieSlice slice = adapter.slices.get(i);
            float sliceSweep = ((float) (adapter.getValue(i) * degreePerSliceData));
            if(sliceSweep == 0.0f) {
                continue;
            }
            slice.setStartAngle(startAngle);
            slice.setSweepAngle(sliceSweep);
            remainingDegree -= sliceSweep;
            startAngle += sliceSweep;
            slice.setEndAngle(startAngle);
        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i = 0; i < adapter.getItemCount(); i++) {
            adapter.slices.get(i).layout(l, t, r, b);
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



    public void setChartListener(PieChartInterface listener) {
        this.listener = listener;
    }

    public void setPieSliceAdapter(PieChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setChart(this);
    }

    public PieSlice getNewSliceInstance() {
        return new PieSlice(this.getContext());
    }


}
