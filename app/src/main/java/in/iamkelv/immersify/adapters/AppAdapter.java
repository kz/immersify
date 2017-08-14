package in.iamkelv.immersify.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.models.AppEntry;

public class AppAdapter extends ArrayAdapter<AppEntry> {

    private ArrayList<AppEntry> mApps;
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView appNameTextView;
        ImageView iconImageView;
        SwitchCompat isAppEnabledSwitch;
    }

    public AppAdapter(Context context) {
        super(context, R.layout.app_row_item);
        mContext = context;
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

        AppEntry item = getItem(position);

        viewHolder.appNameTextView.setText(item.getName());
        viewHolder.iconImageView.setImageDrawable(item.getIcon());
        viewHolder.isAppEnabledSwitch.setChecked(item.getIsEnabled());

        return result;
    }
}
