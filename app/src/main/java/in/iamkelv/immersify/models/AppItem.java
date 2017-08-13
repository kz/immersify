package in.iamkelv.immersify.models;

import android.support.annotation.NonNull;

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
}
