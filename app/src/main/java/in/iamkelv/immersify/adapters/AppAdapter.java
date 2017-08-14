package in.iamkelv.immersify.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.models.AppItem;

public class AppAdapter extends ArrayAdapter<AppItem>{

    private ArrayList<AppItem> apps;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView appNameTextView;
        SwitchCompat isAppEnabledSwitch;
    }

    public AppAdapter(ArrayList<AppItem> data, Context context) {
        super(context, R.layout.app_row_item, data);
        this.apps = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return ((apps != null) ? apps.size() : 0);
    }

    @Override
    public AppItem getItem(int position) {
        return ((apps != null) ? apps.get(position) : null);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_row_item, parent, false);
            viewHolder.appNameTextView = (TextView) convertView.findViewById(R.id.appNameTextView);
            viewHolder.isAppEnabledSwitch = (SwitchCompat) convertView.findViewById(R.id.isAppEnabledSwitch);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        AppItem item = getItem(position);

        viewHolder.appNameTextView.setText(item.getName());
        viewHolder.isAppEnabledSwitch.setChecked(item.getIsEnabled());

        return result;
    }
}
