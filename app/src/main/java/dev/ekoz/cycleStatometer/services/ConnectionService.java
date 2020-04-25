package dev.ekoz.cycleStatometer.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wahoofitness.connector.HardwareConnector;
import com.wahoofitness.connector.HardwareConnectorEnums;
import com.wahoofitness.connector.HardwareConnectorTypes;
import com.wahoofitness.connector.conn.connections.SensorConnection;
import com.wahoofitness.connector.listeners.discovery.DiscoveryListener;
import com.wahoofitness.connector.listeners.discovery.DiscoveryResult;

import dev.ekoz.cycleStatometer.listeners.IDiscoveryListener;

public class ConnectionService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private HardwareConnector mHardwareConnector;
    private DiscoveryListener discoveryListener = new IDiscoveryListener();
    private final HardwareConnector.Listener mHardwareConnectorListener = new HardwareConnector.Listener() {
        @Override
        public void onHardwareConnectorStateChanged(
                @NonNull HardwareConnectorTypes.NetworkType networkType,
                @NonNull HardwareConnectorEnums.HardwareConnectorState hardwareConnectorState) {}

        @Override
        public void onFirmwareUpdateRequired(@NonNull SensorConnection sensorConnection, @NonNull String s, @NonNull String s1) {}
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        com.wahoofitness.common.log.Logger.setLogLevel(Log.VERBOSE);
        Log.d("ConnectionService", "Starting");
        mHardwareConnector= new HardwareConnector(this, mHardwareConnectorListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

        mHardwareConnector.shutdown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Log.d("ConnectionService", "Starting");
        return START_NOT_STICKY;
    }

    public void startDiscovery(){
        DiscoveryResult discoveryResult = mHardwareConnector.startDiscovery(discoveryListener);
        Log.d("ConnectionService", discoveryResult.toString());
        discoveryResult.toString();
    }

    public class LocalBinder extends Binder {
        public ConnectionService getService() {
            // Return this instance of LocalService so clients can call public
            // methods
            return ConnectionService.this;
        }
    }

    public void setDiscoveryListener(DiscoveryListener discoveryListener){
        this.discoveryListener = discoveryListener;
    }
}
