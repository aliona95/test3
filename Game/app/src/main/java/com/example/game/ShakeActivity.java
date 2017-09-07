package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeActivity extends Activity implements SensorEventListener {
/*
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    ImageView image;
    String color = "blue";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.RelativeLayout02);
        rl.setBackgroundColor(Color.rgb(190, 238, 233));



        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Toast toast = Toast.makeText(getApplicationContext(), "Įrenginys pajudintas. Greitis: " + ShakeDetector.speed, Toast.LENGTH_LONG);
                toast.show();
                if (color.equals("blue")) {
                    rl.setBackgroundColor(Color.rgb(255, 220, 171));
                    color = "yellow";
                    Log.i("COLOR", color);
                }else{
                    rl.setBackgroundColor(Color.rgb(190, 238, 233));
                    color = "blue";
                }
            }
        }) {
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }*/




    ////////////////////////////////////////////////


    private SensorManager senSensorManager;
    private ProgressBar firstBar = null;
    private Sensor senAccelerometer;
    String color = "blue";

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        textView = (TextView) findViewById(R.id.xAxis);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.RelativeLayout02);
        rl.setBackgroundColor(Color.rgb(190, 238, 233));

        firstBar = (ProgressBar)findViewById(R.id.firstBar);
        firstBar.setVisibility(View.VISIBLE);
        firstBar.setMax(10);

        firstBar.setProgress(0);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.RelativeLayout02);
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 200) {
                long diffTime = (curTime - lastUpdate);
                Log.i("TAG", "LAIKAAAAAAS " + diffTime);

                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000/2;
                Log.i("TAG", "SPEED " + speed);
                if (speed > SHAKE_THRESHOLD) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Įrenginys pajudintas. Greitis: " + speed, Toast.LENGTH_SHORT);
                    toast.show();
                    if (color.equals("blue")) {
                        rl.setBackgroundColor(Color.rgb(255, 220, 171));
                        color = "yellow";
                        Log.i("COLOR", color);
                    }else{
                        rl.setBackgroundColor(Color.rgb(190, 238, 233));
                        color = "blue";
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;

                textView.setText(Float.toString(last_x));
                if (last_x < 0){
                    firstBar.setProgress(Math.abs(Math.round(last_x)));
                }


            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}



    /*
    
    private ProgressBar firstBar = null;
    private Button myButton;
    private int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        firstBar = (ProgressBar)findViewById(R.id.firstBar);
        firstBar.setVisibility(View.VISIBLE);
        firstBar.setProgress(66);
        myButton = (Button)findViewById(R.id.myButton);
        myButton.setOnClickListener(new ButtonListener());

    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (i == 0 || i == 10) {
                //make the progress bar visible
                //firstBar.setVisibility(View.VISIBLE);
                firstBar.setMax(150);
            }else if (i < firstBar.getMax()) {
                //Set first progress bar value
                firstBar.setProgress(i);
                //Set the second progress bar value
                firstBar.setSecondaryProgress(i + 10);
            }else {
                firstBar.setProgress(0);
                firstBar.setSecondaryProgress(0);
                i = 0;
                firstBar.setVisibility(View.GONE);
            }
            i =+ 10;
        }
    }
}
*/