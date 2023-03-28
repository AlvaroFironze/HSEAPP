package org.hse.baseproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public final static String PREFERENCE_FILE = "org.hse.android.file";
    public final SharedPreferences sharedPref;

    public PreferenceManager(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
    }
    public void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getValue(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }
}
