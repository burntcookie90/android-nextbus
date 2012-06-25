package com.vishnurajeevan.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DirectionSelector extends ListActivity {
	private static final String TAG = NextBusActivity.class.getSimpleName();

	private String route;
	ArrayList<String> directionList;
	int directionCount;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		route = intent.getStringExtra("ROUTE");

		if(route.equals("Tech Trolley")){
			route = "trolley";
		}
		else if(route.equals("Emory Shuttle")){
			route = "emory";
		}
		else if(route.equals("Midnight Rambler")){
			route = "night";
		}

		Log.v(TAG,"Routename: " + route);

		Document directionSelection;

		try {
			directionSelection = Jsoup.connect("http://www.nextbus.com/predictor/simpleStopSelector.shtml?a=georgia-tech&r="+route).get();
			String title = directionSelection.title();
			Log.v(TAG,title);

			Element table = directionSelection.select("table[class=simstopSelectorTable]").first();
			String tableText=table.toString();
			Log.v(TAG,tableText);

			Iterator<Element> ite = table.select("tr").iterator();
			
			while(ite.hasNext()){
				directionList.add(ite.next().text());
				ite.next();

				Log.v(TAG,"Value "+directionCount+": " + directionList.get(directionCount));

				directionCount++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
