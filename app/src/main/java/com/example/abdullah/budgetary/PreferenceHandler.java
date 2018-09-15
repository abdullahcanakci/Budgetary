package com.example.abdullah.budgetary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHandler {
    private static final String FIRST_START = "first_start";
    private static final String DRAWABLE_VERSION = "drawable_version";
    private static final String PERIOD_START = "period_start";


    private SharedPreferences pref;

    public PreferenceHandler(Context context) {
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public int getDrawablesVersion(){
        return pref.getInt(DRAWABLE_VERSION, -1);
    }

    public int getPeriodStart() {
        return pref.getInt(PERIOD_START, 1);
    }

    public void setIconsVersion(int iconsVersion) {
        pref.edit().putInt(DRAWABLE_VERSION, iconsVersion).apply();
    }

    public void setFirstStart(){
        pref.edit().putBoolean(FIRST_START, true).apply();
    }

    public boolean getFirstStart(){
        return pref.getBoolean(FIRST_START, true);
    }
}
