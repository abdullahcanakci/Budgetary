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

    public String getIconName() {
        return iconName;
    }

    public String getIconDescription() {
        return iconDescription;
    }
}
