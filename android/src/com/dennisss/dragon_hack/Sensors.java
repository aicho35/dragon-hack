package com.dennisss.dragon_hack;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Sensors implements SensorEventListener{

	private SensorManager sensorManager;
	private Sensor accel;
	private Sensor magn;
	
	public Sensors(Activity a){
		sensorManager = (SensorManager)a.getSystemService(Context.SENSOR_SERVICE);
		
		accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magn = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}
	
	private float[] accel_data;
	private float[] magn_data;
	
	private float angle;
	
	@Override
	public final void onSensorChanged(SensorEvent event) {
		
		float azimuth;
		
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			accel_data = event.values;
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			magn_data = event.values;
		if (accel_data != null && magn_data != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, accel_data, magn_data);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				azimuth = orientation[1]; // orientation contains: azimut, pitch and roll
				
				azimuth = (float) (azimuth * (180.0 / Math.PI));
				
				angle = azimuth;
				
				Log.i("angle", ((Float)azimuth).toString());
				
			}
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	public float getAngle(){
		return angle;
	}
	
	
	public void resume(){
		sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, magn, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void pause(){
		sensorManager.unregisterListener(this);
	}
	
}
