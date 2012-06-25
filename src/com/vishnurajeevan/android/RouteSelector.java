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
import android.widget.ArrayAdapter;

public class RouteSelector extends ListActivity {
	private static final String TAG = NextBusActivity.class.getSimpleName();
	ArrayList<String> routeList;
	int routeCount;
	String agency;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);

		setContentView(R.layout.route_selector);

		routeCount = 0;
		routeList = new ArrayList<String>();

		Document routeSelection;

		Intent intent = getIntent();

		agency = intent.getStringExtra("AGENCY");

		Log.v(TAG,agency);

		if(agency.equals("Georgia Tech Campus")){
			try {
				routeSelection = Jsoup.connect("http://www.nextbus.com/predictor/simpleRouteSelector.shtml?a=georgia-tech").get();

				String title = routeSelection.title();
				Log.v(TAG,title);

				Element table = routeSelection.select("table[border=1]").first();
				String tableText = table.toString();
				Log.v(TAG,tableText);

				Iterator<Element> ite = table.select("tr").iterator();

				while(ite.hasNext()){
					routeList.add(ite.next().text());

					Log.v(TAG,"Value "+routeCount+": " + routeList.get(routeCount));

					routeCount++;
				}

				ArrayAdapter<String> arrayAdapter =   new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, routeList);
				setListAdapter(arrayAdapter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}


