package com.vishnurajeevan.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NextBusActivity extends Activity {
	private static final String TAG = NextBusActivity.class.getSimpleName();
	ArrayList<Element> stateList;
	int stateCount;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stateCount = 0;
		stateList = new ArrayList<Element>();

		setContentView(R.layout.main);

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
				stateList.add(ite.next());
				
				
				Log.v(TAG,"Value "+stateCount+": " + stateList.get(stateCount).text());
				
				stateCount++;
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}