package com.example.abdullah.budgetary.piechart;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import com.example.abdullah.budgetary.piechart.interfaces.PieSliceListener;

public class PieSlice extends View{
    private static int sliceRadius = 100;
    private static int sliceThickness = 100;
    private static int focusOffset = 25;

    private long sliceId;

    private Point centerPoint;

    private PieSliceListener pieSliceListener;
    private RectF innerRect;
    private RectF outerRect;
    private Paint paintFill;
    private Paint paintBorder;

    private int endAngle = 0;
    private int startAngle = 0;
    private int animationStatusAngle = 0;
    private int sweepAngle = 0;

    private float focus = 0.0f;
    private Path path;
    private boolean isFirstRun = true;
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
        paintFill.setColor(getResources().getColor(color));
        innerRect.set(centerPoint.x - sliceRadius - focusOffset * focus, centerPoint.y - sliceRadius - focusOffset * focus, centerPoint.x + sliceRadius + focusOffset * focus, centerPoint.y + sliceRadius + focusOffset * focus);
        outerRect.set(centerPoint.x - sliceRadius - sliceThickness -focusOffset * focus, centerPoint.y - sliceRadius - sliceThickness - focusOffset * focus, centerPoint.x + sliceRadius + sliceThickness + focusOffset * focus, centerPoint.y + sliceRadius + sliceThickness + focusOffset * focus);

        path.reset();
        //path.arcTo(innerRect, sliceData.getAnimStatAngle(), -sliceData.getAnimStatAngle());
        //path.arcTo(outerRect, sliceData.getStartAngle(), sliceData.getAnimStatAngle());
        path.arcTo(outerRect, endAngle, -sweepAngle);
        path.arcTo(innerRect, startAngle, +sweepAngle);
        path.close();

        canvas.drawPath(path, paintFill);
        //canvas.drawPath(path, paintBorder);

    }

    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofInt(startAngle, endAngle);
        anim.setEvaluator(new IntEvaluator());
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationStatusAngle = ((int) animation.getAnimatedValue());
                invalidate();
            }
        });
        anim.start();

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

    protected void setStartAngle(int angle) {
        this.startAngle = angle;
    }

    protected int getStartAngle() {
        return startAngle;
    }

    protected void setEndAngle(int angle) {
        this.endAngle = angle;
    }

    protected int getEndAngle() {
        return endAngle;
    }

    protected void setSweepAngle(int angle) {
        this.sweepAngle = angle;
    }

    protected int getSweepAngle() {
        return sweepAngle;
    }

    public void setColor(int color) {
        this.color = color;
        paintFill.setColor(getResources().getColor(this.color));
        invalidate();
    }

    public long getSliceId() {
        return sliceId;
    }

    public void setSliceId(long sliceId) {
        this.sliceId = sliceId;
    }
}
