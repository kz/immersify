package in.iamkelv.immersify.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.adapters.AppAdapter;
import in.iamkelv.immersify.models.AppEntry;

public class ExcludeAppsActivity extends AppCompatActivity {

    ArrayList<AppEntry> apps;
    ListView listView;
    private AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_apps);

        listView = (ListView) findViewById(R.id.appListView);

        ArrayList<AppEntry> apps = AppEntry.getListFromPackageManager(getApplicationContext());

        adapter = new AppAdapter(apps, getApplicationContext());

        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                AppEntry appItem = apps.get(position);
//                appItem.setIsEnabled(!appItem.getIsEnabled());
//                adapter.notifyDataSetChanged();
//            }
//        });
    }
}
