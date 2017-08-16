package in.iamkelv.immersify.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.models.AppEntry;
import in.iamkelv.immersify.models.ExcludedApps;

public class AppAdapter extends ArrayAdapter<AppEntry> {

    private ArrayList<AppEntry> mApps;
    private ExcludedApps mExcludedApps;

    // View lookup cache
    private static class ViewHolder {
        TextView appNameTextView;
        ImageView iconImageView;
        SwitchCompat isAppEnabledSwitch;
    }

    public AppAdapter(Context context) {
        super(context, R.layout.app_row_item);
        mExcludedApps = new ExcludedApps(context);
    }

    public void setData(ArrayList<AppEntry> appEntries) {
        clear();
        if (appEntries != null) {
            addAll(appEntries);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_row_item, parent, false);
            viewHolder.appNameTextView = (TextView) convertView.findViewById(R.id.appNameTextView);
            viewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            viewHolder.isAppEnabledSwitch = (SwitchCompat) convertView.findViewById(R.id.isAppEnabledSwitch);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        final AppEntry item = getItem(position);

        viewHolder.appNameTextView.setText(item.getLabel());
        viewHolder.iconImageView.setImageDrawable(item.getIcon());
        viewHolder.isAppEnabledSwitch.setChecked(item.getIsEnabled());

        viewHolder.isAppEnabledSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((SwitchCompat) v).isChecked()) {
                    mExcludedApps.remove(item.getPackageName());
                } else {
                    mExcludedApps.add(item.getPackageName());
                }
            }
        });

        return result;
    }
}
