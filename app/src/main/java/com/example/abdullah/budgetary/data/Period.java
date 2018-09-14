package com.example.abdullah.budgetary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "periods")
public class Period {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private Date start;
    private Date end;

    public Period(long id, Date start, Date end){
        this.id = id;
        this.start = start;
        this.end = end;
    }

    @Ignore
    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
