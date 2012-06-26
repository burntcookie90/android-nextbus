package com.vishnurajeevan.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PredictionDisplay extends Activity{
	private static final String TAG = PredictionDisplay.class.getSimpleName();
	private String stop;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prediction_display);
		
		Intent intent = getIntent();
		stop = intent.getStringExtra("STOP");
		stop = stop.replaceAll("&amp;","&");
		Log.v(TAG,stop);
	}
}
