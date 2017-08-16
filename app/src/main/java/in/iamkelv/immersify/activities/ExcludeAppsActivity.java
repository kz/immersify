package in.iamkelv.immersify.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.adapters.AppAdapter;
import in.iamkelv.immersify.models.AppEntry;
import in.iamkelv.immersify.models.AppListLoader;

public class ExcludeAppsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<AppEntry>> {

    ArrayList<AppEntry> mApps;
    ProgressBar mProgressBar;
    ListView mListView;
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_apps);

        getSupportActionBar().setTitle(R.string.exclude_apps_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mListView = (ListView) findViewById(R.id.appListView);

        mAdapter = new AppAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);

        setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void setListShown(boolean listShown) {
        if (listShown) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Loader<ArrayList<AppEntry>> onCreateLoader(int id, Bundle args) {
        return new AppListLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppEntry>> loader, ArrayList<AppEntry> data) {
        // Set the new data in the adapter.
        mAdapter.setData(data);

        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppEntry>> loader) {
        // Clear the data in the adapter.
        mAdapter.setData(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
