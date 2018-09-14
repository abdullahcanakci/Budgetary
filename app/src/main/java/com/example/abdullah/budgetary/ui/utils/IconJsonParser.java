package com.example.abdullah.budgetary.ui.utils;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.utilities.AppExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IconJsonParser {
    private static final String TAG = "IconJsonParser";
    private int version;
    private boolean isRepoOld = false;
    private String string;

    //Provided string will not be parsed fully.
    //It will only parsed one level to determine the version of the iconsfile
    public IconJsonParser(String stringToParse, int version){
        this.version = version;
        this.string = stringToParse;
    }

    private void parseVersion() {
        try {
            JSONObject iconObj = new JSONObject(string);
            int version = iconObj.getInt("version");
            if(version > this.version)
                isRepoOld = true;
            Log.d(TAG, "oldVersion:" + this.version+" + newVersion:" + version);

        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    public LiveData<Boolean> getVersionStatus(){
        MutableLiveData<Boolean> b = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(()-> {
            parseVersion();
            b.postValue(isRepoOld);
        });
        return b;
    }

    public boolean isRepositoryOld(){return isRepoOld;}

    public LiveData<List<Icon>> getIcons(){
        MutableLiveData<List<Icon>> icons = new MutableLiveData<>();
        parseIcons(icons);
        return icons;
    }

    private void parseIcons(MutableLiveData<List<Icon>> icons) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Icon> readIcons = new ArrayList<>();
            try {
                JSONObject iconObj = new JSONObject(string);
                this.version = iconObj.getInt("version");
                JSONArray array = iconObj.getJSONArray("icons");
                for(int i = 0; i < array.length(); i++){
                    Icon tempIcon = getIcon(array.get(i));
                    if(tempIcon != null){
                        readIcons.add(tempIcon);
                    }
                }

            }catch (JSONException ex) {
                ex.printStackTrace();
            }
            icons.postValue(readIcons);
        });
    }

    private Icon getIcon(Object o) {
        JSONObject obj = ((JSONObject) o);
        Icon icon;
        try {
            String name = obj.getString("name");
            int id = obj.getInt("id");
            String desc = obj.getString("description");
            icon = new Icon(id,name,desc);
        }catch (JSONException ex){
            return null;
        }
        return icon;
    }


    public int getVersion() {
        return version;
    }
}
