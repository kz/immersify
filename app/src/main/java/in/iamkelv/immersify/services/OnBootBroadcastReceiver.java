package in.iamkelv.immersify.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.iamkelv.immersify.utils.ToggleNotification;

// Ensures that the Immersify notification is created on boot
public class OnBootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationServiceIntent = new Intent(context, NotificationService.class);
        notificationServiceIntent.setAction(ToggleNotification.ACTION_CHECK);
        context.startService(notificationServiceIntent);
    }
}
