package in.iamkelv.immersify.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class ExcludedApps {
    private static final String PREFS_NAME = "ExcludedAppsFile";

    private SharedPreferences settings;

    public ExcludedApps(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public Set<String> getAll() {
        return settings.getAll().keySet();
    }

    public void add(String packageName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(packageName, true);
        editor.apply();
    }

    public void remove(String packageName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(packageName);
        editor.apply();
    }

    public boolean contains(String packageName) {
        return settings.contains(packageName);
    }
}
