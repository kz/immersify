package in.iamkelv.immersify.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Set;

import in.iamkelv.immersify.models.ExcludedApps;

public class ImmersiveModeUtils {
    private static String POLICY_CONTROL = "policy_control";
    private static String IMMERSIVE_ENABLED_EXCEPT_PREFIX = "immersive.full=apps";
    private static String IMMERSIVE_ENABLED_EXCEPT_SEPARATOR = ",-";
    private static String IMMERSIVE_DISABLED = "null";

    public static boolean hasSecureSettingsPermission(@NonNull Context context) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_SECURE_SETTINGS);
    }

    public static boolean isImmersiveModeActive(@NonNull Context context) {
        String policyControl = Settings.Global.getString(context.getContentResolver(), POLICY_CONTROL);
        return !(policyControl == null || policyControl.equals(IMMERSIVE_DISABLED));
    }

    public static boolean toggleImmersiveMode(@NonNull Context context) {
        // Change the immersive mode status depending on the current status
        if (isImmersiveModeActive(context)) {
            return disableImmersiveMode(context);
        } else {
            return enableImmersiveMode(context);
        }
    }

    public static boolean enableImmersiveMode(@NonNull Context context) {
        return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL,
                getImmersiveEnabledExceptString(context));
    }

    public static boolean disableImmersiveMode(@NonNull Context context) {
        return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL, IMMERSIVE_DISABLED);
    }

    private static String getImmersiveEnabledExceptString(@NonNull Context context) {
        ExcludedApps excludedApps = new ExcludedApps(context);
        StringBuilder stringBuilder = new StringBuilder(IMMERSIVE_ENABLED_EXCEPT_PREFIX);

        for (String packageName : excludedApps.getAll()) {
            stringBuilder.append(IMMERSIVE_ENABLED_EXCEPT_SEPARATOR);
            stringBuilder.append(packageName);
        }

        return stringBuilder.toString();
    }
}
