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

public class AgencySelectorActivity extends ListActivity {
	private static final String TAG = NextBusActivity.class.getSimpleName();
	String state;
	ArrayList<String> agencyList;
	int agencyCount,agencyURLCount;
	ArrayList<String> agencyURLList;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.agency_selector);

		Intent intent = getIntent();

		state = intent.getStringExtra("STATE");
		state = state.replaceAll(" ","+");
		Log.v(TAG,"StateName: " + state);

		agencyCount = 0;
		agencyURLCount =0;
		agencyList = new ArrayList<String>();
		agencyURLList = new ArrayList<String>();

		Document agencySelection;

		try {
			agencySelection = Jsoup.connect("http://www.nextbus.com/predictor/simpleAgencySelector.shtml?re="+state).get();

			String title = agencySelection.title();
			Log.v(TAG,title);

			Element table = agencySelection.select("table[border=1]").first();
			String tableText = table.toString();
			Log.v(TAG,tableText);

			Iterator<Element> ite = table.select("tr").iterator();
			//			Iterator<Element> ite_url = table.select("a").iterator();

			while(ite.hasNext()){
				Element currentElement = ite.next();
				agencyList.add(currentElement.text());
				agencyURLList.add(currentElement.select("a").toString());

				ite.next();

				Log.v(TAG,"Value "+agencyCount+": " + agencyList.get(agencyCount) + agencyURLList.get(agencyCount));

				agencyCount++;
			}			

			for(int i = 0; i<agencyURLList.size();i++){
				String modURL = agencyURLList.get(i);
				modURL = modURL.substring(9);
				StringTokenizer st = new StringTokenizer(modURL,"\"");
				modURL = (String) st.nextElement();
				Log.v(TAG,modURL);
				agencyURLList.set(i, modURL);
			}
			ArrayAdapter<String> arrayAdapter =   new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, agencyList);
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

		Intent intent = new Intent(AgencySelectorActivity.this,RouteSelector.class);
		intent.putExtra("AGENCY", agencyURLList.get(position));
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}
}
