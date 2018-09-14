package com.example.abdullah.budgetary.utilities;

import android.text.format.DateUtils;

import com.example.abdullah.budgetary.data.Period;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilities {
    public static Date now() {
        return new Date(nowMilli());
    }

    public static Date periodStart() {
        return new Date(0);
    }

    public static String getHourInfo(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static String getDateRelation(Date date) {
        return String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(),  nowMilli(), DateUtils.DAY_IN_MILLIS));
    }

    public static Date getPeriodDate(int dayOfMonth, boolean nextMonth){
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (nextMonth){
            Date d = DateUtilities.now();
            SimpleDateFormat s = new SimpleDateFormat("MM");
            int month = Integer.parseInt(s.format(d));
            month = (month + 1) % 13;
            cal.set(Calendar.MONTH, month);
        }


        return cal.getTime();
    }

    public static String getPeriodInfo(Period period){
        SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat m1 = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());

        StringBuilder b = new StringBuilder();
        if((m.format(period.getStart()).equals(m.format(period.getEnd())))){
            b.append(d.format(period.getStart()));
            b.append(" - ");
            b.append(d.format(period.getEnd()));
            b.append(" ");
            b.append(m1.format(period.getEnd()));
        } else {
            b.append(d.format(period.getStart()));
            b.append(" ");
            b.append(m1.format(period.getStart()));
            b.append(" - ");
            b.append((d.format(period.getEnd())));
            b.append(" ");
            b.append(m1.format(period.getEnd()));
        }
        return b.toString();
    }

    private static long nowMilli() {
        return System.currentTimeMillis();
    }
}
