package com.example.abdullah.budgetary.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.abdullah.budgetary.BuildConfig;

public class PreferenceHandler {
    private static final String PREFS_NAME = "prefs";

    private static final String VERSION_CODE = "version_code";
    private static final String FIRST_START = "first_start";
    private static final int DOESNT_EXIST = -1;
    private static final String DRAWABLE_VERSION = "drawable_version";
    private static final String PERIOD_START = "period_start";


    private int drawableVersion;
    private int periodStart;
    private boolean firstRun = false;

    private SharedPreferences pref;

    public PreferenceHandler(Context context) {
        this.pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        checkFirstRun();
        checkDrawableVersion();
        checkPeriodStart();
    }
    public int getDrawablesVersion(){
        return drawableVersion;
    }
    public int getPeriodStart() {return periodStart; }
    public boolean isFirstRun(){return firstRun; }

    public void setIconsVersion(int iconsVersion) {
        pref.edit().putInt(DRAWABLE_VERSION, iconsVersion).apply();
    }

    private void checkDrawableVersion(){
        drawableVersion = pref.getInt(DRAWABLE_VERSION, DOESNT_EXIST);
    }
    private void checkPeriodStart(){
        periodStart = pref.getInt(PERIOD_START, 7);
    }
    private void checkFirstRun(){
        int versionCode = BuildConfig.VERSION_CODE;
        int savedVersionCode = pref.getInt(VERSION_CODE, DOESNT_EXIST);
        if (versionCode == savedVersionCode) {
            //Normal run do nothing
        } else if(savedVersionCode == DOESNT_EXIST){
            firstRun = true;
            //first run
        }else if(versionCode > savedVersionCode){
            //This is an upgrade
        }
        pref.edit().putInt(VERSION_CODE, versionCode).apply();
    }
}
