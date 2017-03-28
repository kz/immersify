package in.iamkelv.immersify.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import in.iamkelv.immersify.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
