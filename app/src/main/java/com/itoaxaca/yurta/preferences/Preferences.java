package com.itoaxaca.yurta.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    public static final String STRING_PREFERENCES="com.itoaxaca.yurta";
    public static final String PREFERENCE_SESION = "com.itoaxaca.yurta.sesion";
    public static final String PREFERENCE_USER_ID = "com.itoaxaca.yurta.user.id";
    public static final String PREFERENCE_API_TOKEN = "com.itoaxaca.yurta.user.api.token";
    public static final String PREFERENCE_USER_NAME = "com.itoaxaca.yurta.user.name";
    public static final String PREFERENCE_USER_EMAIL = "com.itoaxaca.yurta.user.email";
    public static final String PREFERENCE_OBRA_ID = "com.itoaxaca.yurta.obra.id";
    public static final String PREFERENCE_OBRA_NOMBRE = "com.itoaxaca.yurta.obra.nombre";
    public static final String PREFERENCE_USER_IMG = "com.itoaxaca.yurta.user.img";
    public static final String PREFERENCE_USER_FCM_TOKEN="com.itoaxaca.yurta.user.fcm_token";

    public static void savePreferenceBoolean(Context context, boolean b, String key){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,b).apply();
    }

    public static boolean getPeferenceBoolean(Context context,String key){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }


    public static void savePreferenceString(Context context,String value,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).apply();

    }
    public static String getPeferenceString(Context context,String key){
       SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES
                ,context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }


}
