package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "periods")
public class Period {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private LocalDateTime start;
    private LocalDateTime end;

    public Period(long id, LocalDateTime start, LocalDateTime end){
        this.id = id;
        this.start = start;
        this.end = end;
    }

    @Ignore
    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static Period createFirstPeriod(int periodDay) {
        LocalDateTime periodStart = LocalDateTime.now();
        periodStart = periodStart.withDayOfMonth(periodDay);
        periodStart = setHour(periodStart);

        LocalDateTime periodEnd = calcPeriodEnd(periodStart);

        return new Period(periodStart, periodEnd);
    }

    public static Period createNewPeriod(LocalDateTime prevPeriodEnd){
        LocalDateTime periodStart = prevPeriodEnd.plusDays(1);

        LocalDateTime periodEnd =calcPeriodEnd(periodStart);

        return new Period(periodStart, periodEnd);
    }

    private static LocalDateTime calcPeriodEnd(LocalDateTime periodStart){
        periodStart = periodStart.plusMonths(1);
        periodStart = periodStart.minusDays(1);
        periodStart = setHour(periodStart);

        return periodStart;
    }

    private static LocalDateTime setHour(LocalDateTime dt){
        dt = dt.withHour(0);
        dt = dt.withMinute(0);
        dt = dt.withSecond(0);
        dt = dt.withNano(0);
        return dt;
    }
}
