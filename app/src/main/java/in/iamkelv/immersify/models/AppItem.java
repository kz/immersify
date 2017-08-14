package in.iamkelv.immersify.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppItem {
    private String mName;
    private boolean mIsEnabled;

    public AppItem(@NonNull String name, @NonNull boolean isEnabled) {
        mName = name;
        mIsEnabled = isEnabled;
    }

    public String getName() {
        return mName;
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
    private static final Comparator<AppItem> ALPHA_COMPARATOR = new Comparator<AppItem>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AppItem object1, AppItem object2) {
            return sCollator.compare(object1.getName(), object2.getName());
        }
    };

    public static ArrayList<AppItem> getListFromPackageManager(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ExcludedApps excludedApps = new ExcludedApps(context);

        ArrayList<AppItem> appItems = new ArrayList<>();

        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            // Get the package info name (com.example.app)
            String name = packageInfo.packageName;
            if (packageInfo.applicationInfo != null) {
                // Get the application name if exists (Example App)
                name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            }

            Boolean isEnabled = !excludedApps.contains(packageInfo.packageName);

            appItems.add(new AppItem(name, isEnabled));
        }

        Collections.sort(appItems, ALPHA_COMPARATOR);

        return appItems;
    }
}
