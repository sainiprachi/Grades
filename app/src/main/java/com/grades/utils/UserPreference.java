package com.grades.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    public static final String SHARE_PREF_NAME = "Grades_pref";
    private static UserPreference yourPreference;
    private SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String BASE_URL = "BASE_URL";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String USER_NAME = "USER_NAME";
    public static final String THEME = "THEME";


    public static UserPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new UserPreference(context);
        }
        return yourPreference;
    }

    public UserPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getBaseUrl() {
        return sharedPreferences.getString(BASE_URL, "");
    }

    public void clearSession(){
        editor.clear();
        editor.commit();
    }

    public void setBaseUrl(String base_url) {
        editor.putString(BASE_URL, base_url);
        editor.commit();
    }

    public void setCookie(String cookie) {
        editor.putString(SET_COOKIE, cookie);
        editor.commit();
    }

    public void setUserName(String base_url) {
        editor.putString(USER_NAME, base_url);
        editor.commit();
    }

    public void setTheme(String currentTheme) {
        editor.putString(THEME, currentTheme);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }
    public String getCookie() {
        return sharedPreferences.getString(SET_COOKIE, "");
    }
    public String getTheme() {
        return sharedPreferences.getString(THEME, "");
    }

}

