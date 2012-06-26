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

public class DirectionSelector extends ListActivity {
	private static final String TAG = DirectionSelector.class.getSimpleName();

	private String route;
	ArrayList<String> directionList;
	int directionCount;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.direction_selector);

		directionList = new ArrayList<String>();
		Document directionSelection;

		Intent intent = getIntent();
		route = intent.getStringExtra("ROUTE");
		route = route.replaceAll("&amp;","&");
		Log.v(TAG,route);
		try {
			directionSelection = Jsoup.connect("http://nextbus.com"+route).get();

			String title = directionSelection.title();

			Log.v(TAG,title);

			Element table = directionSelection.select("table.simstopSelectorTable").first();
			String tableText = table.toString();
//			Log.v(TAG,tableText);
			
			Iterator<Element> ite = table.select("td.simstopSelectorTH").iterator();
			ite.next();
//			Log.v(TAG,ite.next().text());
			
			while(ite.hasNext()){
				directionList.add(ite.next().text().substring(11));
				
			}
			
			ArrayAdapter<String> arrayAdapter =   new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, directionList);
			setListAdapter(arrayAdapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something when a list item is clicked

//		Toast toast = Toast.makeText(getApplicationContext(), "You just clicked " + l.getItemAtPosition(position), Toast.LENGTH_SHORT);
//		toast.show();

		Intent intent = new Intent(DirectionSelector.this,StopSelector.class);
		intent.putExtra("ROUTE_URL",route);
		intent.putExtra("DIRECTION", directionList.get(position));
		Log.v(TAG,"Passing DIRECTION: "+position+ ": "+directionList.get(position));
		intent.putExtra("DIRECTION_NUM", position);
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}
}
