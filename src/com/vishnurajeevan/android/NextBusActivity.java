package com.vishnurajeevan.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

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

			//					stateSelection = Jsoup.parse("http://www.nextbus.com/predictor/simpleRegionSelector.shtml");

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

		//		ArrayList<String> your_array_list = new ArrayList<String>();
		//        your_array_list.add("foo");
		//        your_array_list.add("bar");
		//        // This is the array adapter, it takes the context of the activity as a first // parameter, the type of list view as a second parameter and your array as a third parameter
		//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, your_array_list);
		//        Log.v(TAG,""+arrayAdapter.getItem(0));
		//        setListAdapter(arrayAdapter); 

	}
}