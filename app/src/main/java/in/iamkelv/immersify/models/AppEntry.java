package in.iamkelv.immersify.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.iamkelv.immersify.R;

public class AppEntry {
    private String mLabel;
    private String mPackageName;
    private Drawable mIcon;
    private boolean mIsEnabled;

    public AppEntry(@NonNull String label, @NonNull String packageName, @NonNull Drawable icon, @NonNull Boolean isEnabled) {
        mLabel = label;
        mPackageName = packageName;
        mIcon = icon;
        mIsEnabled = isEnabled;
    }

    public String getLabel() {
        return mLabel;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIsEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }

    public boolean getIsEnabled() {
        return mIsEnabled;
    }

    /*
     * Perform alphabetical comparison of application entry objects.
     */
    private static final Comparator<AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AppEntry object1, AppEntry object2) {
            return sCollator.compare(object1.getLabel(), object2.getLabel());
        }
    };

    static ArrayList<AppEntry> getListFromPackageManager(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ExcludedApps excludedApps = new ExcludedApps(context);

        ArrayList<AppEntry> appItems = new ArrayList<>();

        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            // Get the package info name (com.example.app)
            String name = packageInfo.packageName;

            // Set the default icon
            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_android_black_24dp);

            if (packageInfo.applicationInfo != null) {
                // Get the application name if exists (Example App)
                name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                // Get the application icon
                icon = packageInfo.applicationInfo.loadIcon(packageManager);
            }

            // Get whether the application is enabled
            Boolean isEnabled = !excludedApps.contains(packageInfo.packageName);

            appItems.add(new AppEntry(name, packageInfo.packageName, icon, isEnabled));
        }

        Collections.sort(appItems, ALPHA_COMPARATOR);

        return appItems;
    }
}
