package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

@Entity(tableName = "icons")
public class Icon {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String description;

    @ColorInt
    @Ignore
    private int colorInt = 0;

    public Icon(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getColorInt() {
        return colorInt;
    }


    public void setColor(int color) {
        this.colorInt = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
