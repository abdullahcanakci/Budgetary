package com.example.abdullah.budgetary.utilities;

import android.text.format.DateUtils;

import com.example.abdullah.budgetary.data.Period;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

public class DateUtilities {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime periodStart() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC);
    }

    public static String getHourInfo(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("HH:mm")
                .withLocale(Locale.getDefault());
        return formatter.format(date);
    }

    public static String getDateRelation(LocalDateTime date) {
        return String.valueOf(DateUtils.getRelativeTimeSpanString(date.toInstant(ZoneOffset.UTC).toEpochMilli(),  nowMilli(), DateUtils.DAY_IN_MILLIS));
    }

    public static String getPeriodInfo(Period period){

        DateTimeFormatter m = DateTimeFormatter.ofPattern("MM").withLocale(Locale.getDefault());
        DateTimeFormatter m1 = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.getDefault());
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd").withLocale(Locale.getDefault());

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
