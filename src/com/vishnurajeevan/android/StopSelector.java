package com.vishnurajeevan.android;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StopSelector extends ListActivity{
	private static final String TAG = NextBusActivity.class.getSimpleName();
	String route;
	ArrayList<String> stopList;
	int stopCount;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stop_selector);
		
		
		
		stopCount = 0;
		stopList = new ArrayList<String>();
		
	}
}
