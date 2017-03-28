package in.iamkelv.immersify.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import in.iamkelv.immersify.utils.ImmersiveModeUtils;

@TargetApi(Build.VERSION_CODES.N)
@RequiresApi(api = Build.VERSION_CODES.N)
public class ImmersifyTileService extends TileService {
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();

        getTileState();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        getTileState();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();

        Tile tile = getQsTile();

        if (!ImmersiveModeUtils.hasSecureSettingsPermission(this.getApplicationContext())) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.updateTile();
        }

        switch (tile.getState()) {
            case Tile.STATE_UNAVAILABLE:
                showToast("Unable to toggle immersive mode. Open ImmersiveModeUtils for more details.");
                break;
            case Tile.STATE_ACTIVE:
                // Disable immersive mode
                if (ImmersiveModeUtils.disableImmersiveMode(this.getApplicationContext())) {
                    tile.setState(Tile.STATE_INACTIVE);
                    tile.updateTile();
                } else {
                    showToast("Unable to disable immersive mode due to unknown error.");
                }
                break;
            case Tile.STATE_INACTIVE:
                // Enable immersive mode
                if (ImmersiveModeUtils.enableImmersiveMode(this.getApplicationContext())) {
                    tile.setState(Tile.STATE_ACTIVE);
                    tile.updateTile();
                } else {
                    showToast("Unable to enable immersive mode due to unknown error.");
                }
                break;
        }
    }

    private void showToast(final String text) {
        final Context thisContext = this;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(thisContext, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTileState() {
        Tile tile = getQsTile();

        // Check if permissions are granted
        if (!ImmersiveModeUtils.hasSecureSettingsPermission(this.getApplicationContext())) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.updateTile();
            return;
        }

        // Update tile based on PolicyControl
        if (ImmersiveModeUtils.isImmersiveModeActive(this.getApplicationContext())) {
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }
}
