package com.example.abdullah.budgetary.piechart.data;

public class PieSliceData {
    private int categoryId;
    private double value;
    private int sweepAngle = 0;
    private String label;
    private int iconResId;
    private int colorResId;
    private boolean isFocused;
    private int startAngle = 0;
    private int animStatAngle = 0;

    public PieSliceData(int categoryId, double value, String label, int iconResId, int colorResId){
        this.categoryId = categoryId;
        this.value = value;
        this.label = label;
        this.iconResId = iconResId;
        this.colorResId = colorResId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        this.animStatAngle = startAngle;
    }

    public int getAnimStatAngle() {
        return animStatAngle;
    }

    public void setAnimStatAngle(int animStatAngle) {
        this.animStatAngle = animStatAngle;
    }

    public boolean isAnimationFinished() {
        return animStatAngle == sweepAngle && animStatAngle != 0 && sweepAngle != 0;
    }
}
