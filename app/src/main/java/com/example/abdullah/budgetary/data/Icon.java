package com.example.abdullah.budgetary.data;

public class Icon {
    private long id;
    private int colorInt;
    private String iconName;
    private String iconDescription;

    public Icon(long id, int colorInt, String iconName, String iconDescription) {
        this.id = id;
        this.colorInt = colorInt;
        this.iconName = iconName;
        this.iconDescription = iconDescription;
    }

    public long getId() {
        return id;
    }

    public int getColorInt() {
        return colorInt;
    }


    public void setColor(int color) {
        this.colorInt = color;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconDescription() {
        return iconDescription;
    }

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }
}
