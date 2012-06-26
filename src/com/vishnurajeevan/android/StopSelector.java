package com.vishnurajeevan.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

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

public class StopSelector extends ListActivity{
	private static final String TAG = StopSelector.class.getSimpleName();
	String direction,route;
	ArrayList<String> stopList,stop_URL_List;
	int stopCount, directionNumber;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stop_selector);
		
		
		
		stopCount = 0;
		stopList = new ArrayList<String>();
		stop_URL_List = new ArrayList<String>();
		
		Intent intent = getIntent();
		direction = intent.getStringExtra("DIRECTION");
		directionNumber = intent.getIntExtra("DIRECTION_NUM", 0);
		route = intent.getStringExtra("ROUTE_URL");
		
		Log.v(TAG,direction+" "+directionNumber+ " "+route);
		
		Document stopSelection;
		
		try {
			stopSelection = Jsoup.connect("http://nextbus.com"+route).get();
			String title = stopSelection.title();
			
			Log.v(TAG,title);
			
			Element table = stopSelection.select("td.simstopSelectorTD.top").get(directionNumber);
			String tableText = table.toString();
			Log.v(TAG,tableText);
			
			Iterator<Element> ite = table.select("tr").iterator();
			
			while(ite.hasNext()){
				Element currentElement = ite.next();
				String stop = currentElement.text();
				
				stopList.add(stop.substring(0,stop.length()-7));
				stop_URL_List.add(currentElement.select("a").toString());
				
			}
			
			for(int i = 0; i<stop_URL_List.size();i++){
				String modURL = stop_URL_List.get(i);
				modURL = modURL.substring(9);
				StringTokenizer st = new StringTokenizer(modURL,"\"");
				modURL = (String) st.nextElement();
				Log.v(TAG,modURL);
				stop_URL_List.set(i, modURL);
			}
			
			ArrayAdapter<String> arrayAdapter =   new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stopList);
			setListAdapter(arrayAdapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something when a list item is clicked

		Toast toast = Toast.makeText(getApplicationContext(), "You just clicked " + l.getItemAtPosition(position), Toast.LENGTH_SHORT);
		toast.show();

		Intent intent = new Intent(StopSelector.this,PredictionDisplay.class);
		intent.putExtra("STOP", stop_URL_List.get(position));
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}
}
