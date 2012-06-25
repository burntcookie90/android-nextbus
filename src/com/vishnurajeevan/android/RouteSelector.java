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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

		try {
			routeSelection = Jsoup.connect("http://www.nextbus.com"+agency).get();

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


	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something when a list item is clicked

		Toast toast = Toast.makeText(getApplicationContext(), "You just clicked " + l.getItemAtPosition(position), Toast.LENGTH_SHORT);
		toast.show();

		Intent intent = new Intent(RouteSelector.this,DirectionSelector.class);
		intent.putExtra("ROUTE", (String)l.getItemAtPosition(position));
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}
}


