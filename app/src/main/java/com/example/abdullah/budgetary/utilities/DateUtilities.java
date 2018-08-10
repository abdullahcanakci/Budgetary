package com.example.abdullah.budgetary.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtilities {
    public static Date now() {
        return new Date();
    }

    public static Date periodStart() {
        return new Date(0);
    }

    public static String getHourInfo(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static String getDateRelation(Date date) {
        //TODO Create a relation method
        return "Relation";
    }
}