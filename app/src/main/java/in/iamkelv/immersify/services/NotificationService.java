package in.iamkelv.immersify.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import in.iamkelv.immersify.utils.ImmersiveModeUtils;
import in.iamkelv.immersify.utils.ToggleNotification;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Check whether the action requested is our responsibility to handle
        if (intent.getAction().equals(ToggleNotification.ACTION_TOGGLE)) {
            // Toggle immersive mode
            ImmersiveModeUtils.toggleImmersiveMode(this);
        } else if (intent.getAction().equals(ToggleNotification.ACTION_CHECK)) {
            ToggleNotification.ensureShown(this);
        }

        // If this intent fails, then we do not want to redeliver this intent
        // in case the intent redelivers unacceptably late for the user
        return START_NOT_STICKY;
    }
}
