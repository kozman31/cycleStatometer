package dev.ekoz.cycleStatometer.listeners;

import androidx.annotation.NonNull;

import com.wahoofitness.common.log.Log;
import com.wahoofitness.connector.conn.connections.params.ConnectionParams;
import com.wahoofitness.connector.listeners.discovery.DiscoveryListener;

public class IDiscoveryListener implements DiscoveryListener {

    @Override
    public void onDeviceDiscovered(@NonNull ConnectionParams connectionParams) {
        Log.d("Device Discovered {}", connectionParams.getName());
        Log.d("Device Discovered {}", connectionParams.getNetworkType());
    }

    @Override
    public void onDiscoveredDeviceLost(@NonNull ConnectionParams connectionParams) {
        android.util.Log.d("onDiscoveredDeviceLost", "lost");
    }

    @Override
    public void onDiscoveredDeviceRssiChanged(@NonNull ConnectionParams connectionParams, int i) {
        android.util.Log.d("onDiscoveredChanged", "changed");
    }
}
