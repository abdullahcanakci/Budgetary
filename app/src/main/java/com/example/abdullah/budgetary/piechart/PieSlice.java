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
import android.support.annotation.NonNull;
import android.view.View;

import com.example.abdullah.budgetary.piechart.data.PieSliceData;
import com.example.abdullah.budgetary.piechart.interfaces.PieSliceListener;

public class PieSlice extends View{
    private static int sliceRadius = 100;
    private static int sliceThickness = 100;
    private static int focusOffset = 25;

    private final Point centerPoint;
    private PieSliceData sliceData;
    private PieSliceListener pieSliceListener;
    private RectF innerRect;
    private RectF outerRect;
    private Paint paintFill;
    private Paint paintBorder;

    private float focus = 0.0f;
    private Path path;
    private boolean isFirstRun = true;


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
    public boolean equals(Object obj) {
        if(obj != null)
            return ((PieSlice) obj).sliceData.getCategoryId() == sliceData.getCategoryId();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerPoint.set(canvas.getWidth() / 2, canvas.getHeight() / 2);

        innerRect.set(centerPoint.x - sliceRadius - focusOffset * focus, centerPoint.y - sliceRadius - focusOffset * focus, centerPoint.x + sliceRadius + focusOffset * focus, centerPoint.y + sliceRadius + focusOffset * focus);
        outerRect.set(centerPoint.x - sliceRadius - sliceThickness -focusOffset * focus, centerPoint.y - sliceRadius - sliceThickness - focusOffset * focus, centerPoint.x + sliceRadius + sliceThickness + focusOffset * focus, centerPoint.y + sliceRadius + sliceThickness + focusOffset * focus);

        path.reset();
        //path.arcTo(innerRect, sliceData.getAnimStatAngle(), -sliceData.getAnimStatAngle());
        //path.arcTo(outerRect, sliceData.getStartAngle(), sliceData.getAnimStatAngle());
        path.arcTo(outerRect, sliceData.getStartAngle() + sliceData.getSweepAngle(), -sliceData.getSweepAngle());
        path.arcTo(innerRect, sliceData.getStartAngle(), sliceData.getSweepAngle());
        path.close();

        canvas.drawPath(path, paintFill);
        //canvas.drawPath(path, paintBorder);

    }

    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofInt(sliceData.getStartAngle(), sliceData.getStartAngle() + sliceData.getSweepAngle());
        anim.setEvaluator(new IntEvaluator());
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sliceData.setAnimStatAngle(((int) animation.getAnimatedValue()));
                invalidate();
                if(sliceData.isAnimationFinished()) {
                    //pieSliceListener.sliceAnimationEnded();
                }
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
        draw(new Canvas());
    }

    private boolean isCanvasDrawn(Canvas canvas) {
        return canvas.getWidth() != 0 && canvas.getHeight() != 0;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public void setPieSliceListener(@NonNull PieSliceListener listener){
        this.pieSliceListener = listener;
    }

    private void setInnerRect(RectF rect) {
        this.innerRect = rect;
    }


    public void setSliceData(PieSliceData sliceData) {
        paintFill.setColor(getResources().getColor(sliceData.getColorResId()));
        this.sliceData = sliceData;
        invalidate();
    }

    public PieSliceData getSliceData() {
        return this.sliceData;
    }

    public void shrink() {
        selectionAnimation(false);
    }

    public void expand() {
        selectionAnimation(true);
    }
}
