package com.itoaxaca.yurta.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String STRING_PREFERENCES="mx.com.itoaxaca.yurtaapp";
    public static final String PREFERENCE_ESTADO_SESION = "estado.button.sesion";

    public static void savePreferenceBoolean(Context context, boolean b, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,b).apply();
    }

    public static boolean getPeferenceBoolean(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}
