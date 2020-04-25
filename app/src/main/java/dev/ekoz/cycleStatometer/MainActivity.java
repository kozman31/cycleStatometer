package dev.ekoz.cycleStatometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import dev.ekoz.cycleStatometer.services.ConnectionService;

public class MainActivity extends AppCompatActivity {

    ConnectionService connectionService;
    private boolean mBound1;
    boolean mBound = false;
    int clc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected  void onStart(){
        super.onStart();
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    public void startDisoveru(View view){
        connectionService.startDiscovery();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get
            // LocalService instance
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) service;
            connectionService = binder.getService();
            mBound1 = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound1 = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound1) {
            unbindService(mConnection);
            mBound1 = false;
        }
    }
}
