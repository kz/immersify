package in.iamkelv.immersify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.iamkelv.immersify.R;

public class StatusFragment extends Fragment {

    private static final String SECURE_SETTINGS_STATUS = "SECURE_SETTINGS_STATUS";

    public StatusFragment() {
    }

    public static StatusFragment newInstance(boolean secureSettingsStatus) {
        StatusFragment statusFragment = new StatusFragment();

        // Supply WRITE_SECURE_SETTINGS permission status as argument
        Bundle args = new Bundle();
        args.putBoolean(SECURE_SETTINGS_STATUS, secureSettingsStatus);
        statusFragment.setArguments(args);

        return statusFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);
        TextView permsGrantedTextView = (TextView) rootView.findViewById(R.id.permissions_granted_status);

        Bundle bundle = this.getArguments();
        if (bundle.getBoolean(SECURE_SETTINGS_STATUS, false)) {
            permsGrantedTextView.setText(R.string.secure_settings_granted);
        } else {
            permsGrantedTextView.setText(R.string.secure_settings_denied);
        }

        return rootView;
    }
}