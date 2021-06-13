package com.example.mobilproje;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class SP {

    static final String SP_NAME = "Ayarlar";


    public void saveString(Context context, String SP_KEY, String VALUE) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SP_KEY, VALUE);
        editor.commit();
    }

    public void saveInt(Context context, String SP_KEY, int VALUE) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SP_KEY, VALUE);
        editor.commit();
    }


    public String getStringValue(Context context, String SP_KEY){
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String text =settings.getString(SP_KEY, null);
        return text;
    }

    public int getIntValue(Context context, String SP_KEY){
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        int newin =settings.getInt(SP_KEY, -1);
        return newin;
    }



    public void clear(Context context){
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(Context context, String SP_KEY){
        SharedPreferences settings = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(SP_KEY);
        editor.commit();
    }
}
