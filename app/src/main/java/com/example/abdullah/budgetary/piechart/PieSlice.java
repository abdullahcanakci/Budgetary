package com.example.abdullah.budgetary.piechart;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.view.View;

public class PieSlice extends View{
    private static int sliceRadius = 100;
    private static int sliceThickness = 100;
    private static int focusOffset = 25;

    private long sliceId;

    private Point centerPoint;

    private RectF innerRect;
    private RectF outerRect;
    private Paint paintFill;
    private Paint paintBorder;

    private float endAngle = 0;
    private float startAngle = 0;
    private float animationStatusAngle = 0;
    private float sweepAngle = 0;

    private float focus = 0.0f;
    private Path path;
    private boolean isFirstRun = true;
    @ColorInt
    private int color;


    public PieSlice(Context context) {
        super(context);
        centerPoint = new Point(0,0);
        innerRect = new RectF();
        outerRect = new RectF();

        path = new Path();

        paintFill = new Paint();
        paintFill.setAntiAlias(true);
        paintFill.setDither(true);

        paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setAntiAlias(true);
        paintBorder.setDither(true);
        paintBorder.setStrokeWidth(2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerPoint.set(canvas.getWidth() / 2, canvas.getHeight() / 2);
        paintFill.setColor(this.color);
        innerRect.set(centerPoint.x - sliceRadius - focusOffset * focus, centerPoint.y - sliceRadius - focusOffset * focus, centerPoint.x + sliceRadius + focusOffset * focus, centerPoint.y + sliceRadius + focusOffset * focus);
        outerRect.set(centerPoint.x - sliceRadius - sliceThickness -focusOffset * focus, centerPoint.y - sliceRadius - sliceThickness - focusOffset * focus, centerPoint.x + sliceRadius + sliceThickness + focusOffset * focus, centerPoint.y + sliceRadius + sliceThickness + focusOffset * focus);

        path.reset();
        path.arcTo(outerRect, endAngle, -sweepAngle);
        path.arcTo(innerRect, startAngle, +sweepAngle);
        path.close();

        canvas.drawPath(path, paintFill);

    }

    public void startAnimation() {
        return;
        /*
        ValueAnimator anim = ValueAnimator.ofFloat(startAngle, endAngle);
        anim.setEvaluator(new FloatEvaluator());
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationStatusAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
        */
    }

    public void selectionAnimation(boolean isExpanding) {
        ValueAnimator anim;
        float startValue;
        float endValue;
        if(isExpanding) {
            startValue = 0.0f;
            endValue = 1.0f;
            if(focus != 0.0f)
                startValue = focus;
        } else {
            startValue = 1.0f;
            endValue = 0.0f;
            if(focus != 0.0f)
                startValue = focus;
        }

        anim = ValueAnimator.ofFloat(startValue, endValue);
        anim.setEvaluator(new FloatEvaluator());
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                focus = ((float) animation.getAnimatedValue());
                invalidate();
            }
        });

        anim.start();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        startAnimation();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }



    public void shrink() {
        selectionAnimation(false);
    }

    public void expand() {
        selectionAnimation(true);
    }

    protected void setStartAngle(float angle) {
        this.startAngle = angle;
    }

    protected float getStartAngle() {
        return startAngle;
    }

    protected void setEndAngle(float angle) {
        this.endAngle = angle;
    }

    protected float getEndAngle() {
        return endAngle;
    }

    protected void setSweepAngle(float angle) {
        this.sweepAngle = angle;
    }

    protected float getSweepAngle() {
        return sweepAngle;
    }

    public void setColor(int color) {
        this.color = color;
        paintFill.setColor(this.color);
        invalidate();
    }

    public long getSliceId() {
        return sliceId;
    }

    public void setSliceId(long sliceId) {
        this.sliceId = sliceId;
    }
}
