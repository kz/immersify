package in.iamkelv.immersify.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class ImmersiveModeUtils {
    private static String POLICY_CONTROL = "policy_control";
    private static String IMMERSIVE_ENABLED = "immersive.full=*";
    private static String IMMERSIVE_DISABLED = "null";

    public static boolean hasSecureSettingsPermission(@NonNull Context context) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_SECURE_SETTINGS);
    }

    public static boolean isImmersiveModeActive(@NonNull Context context) {
        String policyControl = Settings.Global.getString(context.getContentResolver(), POLICY_CONTROL);
        return (!policyControl.equals(IMMERSIVE_DISABLED));
    }

    public static boolean toggleImmersiveMode(@NonNull Context context) {
        // Change the immersive mode status depending on the current status
        if (isImmersiveModeActive(context)) {
            return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL, IMMERSIVE_DISABLED);
        } else {
            return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL, IMMERSIVE_ENABLED);
        }
    }

    public static boolean enableImmersiveMode(@NonNull Context context) {
        return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL, IMMERSIVE_ENABLED);
    }
    
    public static boolean disableImmersiveMode(@NonNull Context context) {
        return Settings.Global.putString(context.getContentResolver(), POLICY_CONTROL, IMMERSIVE_DISABLED);
    }
}
