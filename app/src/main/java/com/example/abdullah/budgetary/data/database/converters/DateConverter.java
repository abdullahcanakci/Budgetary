package com.example.abdullah.budgetary.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static long toLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(long time) {
        return new Date(time);
    }

}
