package in.iamkelv.immersify.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.preference.PreferenceManager;

import in.iamkelv.immersify.R;
import in.iamkelv.immersify.fragments.SettingsFragment;
import in.iamkelv.immersify.services.NotificationService;

public class ToggleNotification {
    public static String ACTION_TOGGLE = "toggle";
    public static String ACTION_CHECK = "check";

    // Show a notification in the notification drawer
    public static void show(Context context) {
        // Build toggle action
        Intent notificationServiceIntent = new Intent(context, NotificationService.class);
        notificationServiceIntent.setAction(ACTION_TOGGLE);
        PendingIntent notificationPendingIntent = PendingIntent.getService(context, 0, notificationServiceIntent, 0);
        NotificationCompat.Action toggleAction = new NotificationCompat.Action(
                R.drawable.ic_border_clear_black_24dp,
                "Toggle",
                notificationPendingIntent
        );

        // Build notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_zoom_out_map_black_24dp)
                        .setContentTitle("Immersify")
                        .setContentText("Tap to toggle immersive mode")
                        .setContentIntent(notificationPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_MIN)
                        .setCategory(NotificationCompat.CATEGORY_SERVICE)
                        .addAction(toggleAction)
                        .setOngoing(true);

        // Clear existing notifications
        ToggleNotification.cancel(context);

        // Notify the notification manager to create the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public static void cancel(Context context) {
        NotificationManagerCompat.from(context).cancelAll();
    }

    // Checks to see if a notification should be displayed and displays it if so
    public static void ensureShown(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isNotificationShown = prefs.getBoolean(SettingsFragment.KEY_NOTIFICATION_ENABLED, false);

        if (isNotificationShown) {
            ToggleNotification.show(context);
        } else {
            ToggleNotification.cancel(context);
        }
    }
}
