package dev.ekoz.cycleStatometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import dev.ekoz.cycleStatometer.services.ConnectionService;

public class MainActivity extends AppCompatActivity {

    ConnectionService connectionService;
    private boolean mBound1;

    private Button mStartWorkoutButton;
    private Chronometer mChronometer;
    private Boolean mIsTimerRunning;
    private Long mTimeWhenStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChronometer = findViewById(R.id.chronometer);
        mStartWorkoutButton = findViewById(R.id.workoutStart);
        mStartWorkoutButton.setOnClickListener(mStartStopWorkoutListener);
        mTimeWhenStopped = 0L;
        mIsTimerRunning =false;
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

    View.OnClickListener mStartStopWorkoutListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(mIsTimerRunning){
                stopTimer();
            } else{
                startTimer();
            }
            mIsTimerRunning = !mIsTimerRunning;
        }
    };

    public void startTimer(){
        mChronometer.setBase(SystemClock.elapsedRealtime() + mTimeWhenStopped);
        mChronometer.start();
    }

    private void stopTimer(){
        mTimeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
    }
}
