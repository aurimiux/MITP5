package com.example.MITP5;

import static com.example.MITP5.R.layout.activity_main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    // Shake detection variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    // Light sensor variables
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                getRateOnShake(count);
            }
        });

        // Light sensor initialization
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }
        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                ((TextView)findViewById(R.id.LightLabel)).setText(value + " lx");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) { }
        };
    }

    public void getRateOnShake(int count)
    {
        if(count >0) {
            new DataLoader(this) {
                @Override
                public void onPostExecute(String result) {
                    StringArrayToListView(String.valueOf(result));
                }
            }.execute("EUR");
        }
    }

    public void btnGetRateOnClick(View v)
    {
        new DataLoader(this){
            @Override
            public void onPostExecute(String result)
            {
                StringArrayToListView(String.valueOf(result));
            }
        }.execute("EUR");
    }

    private void StringArrayToListView(String str)
    {
        ListView listView1 = findViewById(R.id.listView);
        String[] data = stringToStringArray(str);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, data);
        listView1.setAdapter(adapter);
    }

    private String[] stringToStringArray(String str)
    {
        return str.split("\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

}