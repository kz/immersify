package in.iamkelv.immersify.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.utils.ImmersiveModeUtils;
import in.iamkelv.immersify.utils.ToggleNotification;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_TOGGLE_IMMERSIVE_MODE = "pref_toggle";
    public static final String KEY_NOTIFICATION_ENABLED = "pref_notification_enabled";
    public static final String KEY_HIDE_STATUS_BAR = "pref_hide_status_bar";
    public static final String KEY_HIDE_NAVIGATION_BAR = "pref_hide_navigation_bar";
    public static final String KEY_EXCLUDE_APPS = "pref_exclude_apps";
    public static final String KEY_DETECT_KEYBOARD = "pref_detect_keyboard";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set up a listener whenever a key changes
        // Source: http://stackoverflow.com/a/3799894/2102540
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        // Register onPreferenceClickListener for "Toggle Immersive Mode"
        Preference toggleImmersiveModePref = findPreference(KEY_TOGGLE_IMMERSIVE_MODE);
        toggleImmersiveModePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Check Secure Settings permission
                if (!ImmersiveModeUtils.hasSecureSettingsPermission(getContext())) {
                    Toast.makeText(getContext(),
                            "Follow the installation instructions for Immersify to work",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                // Toggle immersive mode
                if (!ImmersiveModeUtils.toggleImmersiveMode(getContext())) {
                    Toast.makeText(getContext(),
                            "Unable to toggle immersive mode. An unknown error occurred.",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_NOTIFICATION_ENABLED)) {
            // Create the notification if applicable
            if (sharedPreferences.getBoolean(key, false)) {
                ToggleNotification.show(getContext());
            } else {
                ToggleNotification.cancel(getContext());
            }
        }
    }
}
