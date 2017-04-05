package pl.domanski.kamil.climber.Engine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import pl.domanski.kamil.climber.Engine.Constans;

// Klasa odpowiedzialna za odczyt danych z sensorów, potrzebnych do sterowania postacią

public class OrientationData implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelerometer;


    private float[] accelOutput;



    public float getOrientation(){

        return accelOutput[0];
    }



    public OrientationData(){
        manager = (SensorManager) Constans.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void register(){
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }

    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;

    }
}
