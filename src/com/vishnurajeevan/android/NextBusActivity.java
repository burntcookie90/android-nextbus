package com.vishnurajeevan.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NextBusActivity extends ListActivity {
	private static final String TAG = NextBusActivity.class.getSimpleName();
	ArrayList<String> stateList;
	int stateCount;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		stateCount = 0;
		stateList = new ArrayList<String>();


		org.jsoup.nodes.Document stateSelection;

		try {
			stateSelection = Jsoup.connect("http://www.nextbus.com/predictor/simpleRegionSelector.shtml").get();

			String title = stateSelection.title();
			Log.v(TAG,title);

			Element table = stateSelection.select("table[border=1]").first();
			String tableText = table.toString();
			Log.v(TAG,tableText);

			Iterator<Element> ite = table.select("tr").iterator();

			while(ite.hasNext()){
				stateList.add(ite.next().text());


				Log.v(TAG,"Value "+stateCount+": " + stateList.get(stateCount));

				stateCount++;
			}

			ArrayAdapter<String> arrayAdapter =   new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stateList);
			setListAdapter(arrayAdapter);




		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
		Toast toast = Toast.makeText(getApplicationContext(),"clicked something", Toast.LENGTH_SHORT);
    }
}