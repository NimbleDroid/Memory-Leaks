package com.nimbledroid.memoryleaks;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static WeakReference<MainActivity> activityReference;
    private Object inner;
    private static View view;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View aicButton = findViewById(R.id.at_button);
        aicButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startAsyncTask();
                nextActivity();
            }
        });
        View saButton = findViewById(R.id.sa_button);
        saButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setStaticActivity();
                nextActivity();
            }
        });
        View hButton = findViewById(R.id.h_button);
        hButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createHandler();
                nextActivity();
            }
        });
        View icButton = findViewById(R.id.ic_button);
        icButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createInnerClass();
                nextActivity();
            }
        });
        View svButton = findViewById(R.id.sv_button);
        svButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setStaticView();
                nextActivity();
            }
        });
        View smButton = findViewById(R.id.sm_button);
        smButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                registerListener();
                nextActivity();
            }
        });
        View tButton = findViewById(R.id.t_button);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                spawnThread();
                nextActivity();
            }
        });
        View ttButton = findViewById(R.id.tt_button);
        ttButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                scheduleTimer();
                nextActivity();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            unregisterListener();
        } else if (view != null) {
            unsetStaticView();
        } else if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    void nextActivity() {
        Intent intent = new Intent(this, DestinationActivity.class);
        startActivity(intent);
        SystemClock.sleep(600);
        finish();
    }

    private static class NimbleTask extends AsyncTask<Void, Void, Void> {
        @Override protected Void doInBackground(Void... params) {
            while(true);
        }
    }

    void startAsyncTask() {
        new NimbleTask().execute();
    }

    void setStaticActivity() {
        activityReference = new WeakReference<MainActivity>(this);
    }

    private static class NimbleHandler extends Handler {
        @Override public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    }

    private static class NimbleRunnable implements Runnable {
        @Override public void run() {
            while(true);
        }
    }

    void createHandler() {
        new NimbleHandler().postDelayed(new NimbleRunnable(), Long.MAX_VALUE >> 1);
    }

    void createInnerClass() {
        class InnerClass {
        }
        inner = new InnerClass();
    }

    void setStaticView() {
        view = findViewById(R.id.sv_button);
    }

    void unsetStaticView() {
        view = null;
    }

    void registerListener() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    void unregisterListener() {
        sensorManager.unregisterListener(this, sensor);
    }

    void spawnThread() {
        thread = new Thread() {
            @Override public void run() {
                while (!isInterrupted()) {
                }
            }
        };
        thread.start();
    }

    private static class NimbleTimerTask extends TimerTask {
        @Override public void run() {
            while(true);
        }
    }

    void scheduleTimer() {
        new Timer().schedule(new NimbleTimerTask(), Long.MAX_VALUE >> 1);
    }
}
