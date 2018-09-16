package com.example.abdullah.budgetary.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

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

    @TypeConverter
    public static long toLong(LocalDateTime dateTime){ return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();}

    @TypeConverter
    public static LocalDateTime toLocalDateTime(long time){ return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);}

}
