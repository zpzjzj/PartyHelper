package com.clz.partyhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ItemsListActivity extends Activity{
	private static String LOG_TAG="ItemsListActivity";
	
	private TextView typeView;	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);
		
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        Spinner spinnerAge = (Spinner)findViewById(R.id.spinnerAge);
        ArrayAdapter<CharSequence> adapterAge = 
        		ArrayAdapter.createFromResource(
        				this, 
        				R.array.array_age, 
        				R.xml.static_spinner);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);
        
        Spinner spinnerPlace = (Spinner)findViewById(R.id.spinnerPlace);
        ArrayAdapter<CharSequence> adapterPlace=
        		ArrayAdapter.createFromResource(
        				this, 
        				R.array.array_place,
        				R.xml.static_spinner);
        adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace.setAdapter(adapterPlace);
        
        Spinner spinnerCounts = (Spinner)findViewById(R.id.spinnerCounts);
        ArrayAdapter<CharSequence> adapterCount = 
        		ArrayAdapter.createFromResource(
        				this, 
        				R.array.array_counts, 
        				R.xml.static_spinner);
        adapterCount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounts.setAdapter(adapterCount);
        
        String[] fromColumns = new String[3];
        int[] toViews = new int[3];
        for (int i=0; i<3; i++){
        	fromColumns[i] = new String("item " + i);
        	toViews[i] = i;
        }
        
        ListView itemLists = (ListView)findViewById(R.id.itemList);
        SimpleAdapter adapterList = 
        		new SimpleAdapter(
        				this, 
        				getData(),
        				R.xml.search_list,
        				new String[]{"title", "age", "people_number", "image"},
        				new int[]{R.id.list_item_title, R.id.list_age, R.id.list_people, R.id.list_image});    
        itemLists.setAdapter(adapterList);
        
        //Uri uri = getIntent().getData();
		//Cursor cursor=managedQuery(uri, null, null, null, null);
		
//		if (cursor == null){
//			finish();
//		} else {
//			//fetch data from the database
//			cursor.moveToFirst();
//			ImageView image = new ImageView(null);
//			image.setImageResource(R.drawable.ic_game_128x128);
//			
//			TextView name = new TextView(null);
//			name.setText("Game X");
//		}
//		if (Intent.ACTION_SEARCH.equals((intent.getAction()))){
//			String query = intent.getStringExtra(SearchManager.QUERY);
//			doMySearch(query);
//		}
	}
	
	private List<Map<String, Object>> getData(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "Game 1");
		map.put("age", "child");
		map.put("people_number", "5");
		map.put("image", R.drawable.ic_game_64x64);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "Game 2");
		map.put("age", "young");
		map.put("people_number", "20");
		map.put("image", R.drawable.ic_game_64x64);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "Game 3");
		map.put("age", "2-20");
		map.put("people_number", "25");
		map.put("image", R.drawable.ic_game_64x64);
		list.add(map);
		return list;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_menu, menu);
		
		Log.d(LOG_TAG, "create the list action bar");
		SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView)menu.findItem(R.id.list_action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
		typeView = (TextView)menu.findItem(R.id.list_action_type).getActionView();
		typeView.setGravity(Gravity.CENTER);
		typeView.setWidth(300);
		typeView.setTextSize(20);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		typeView.setText(message);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		case R.id.action_search:
			doMySearch("this is a sql query");
			onSearchRequested();
			return true;
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void doMySearch(String query){
		
	}
	
}
