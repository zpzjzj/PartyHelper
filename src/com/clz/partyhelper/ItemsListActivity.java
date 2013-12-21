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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.clz.partyhelper.game.Game;
import com.clz.partyhelper.game.GamesDataSource;

public class ItemsListActivity extends Activity{
	private static String LOG_TAG="ItemsListActivity";
	
	private TextView typeView;	
	private ListView itemLists;
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
        
        itemLists = (ListView)findViewById(R.id.itemList);   
        Log.d(LOG_TAG, "before add data height" + itemLists.getHeight());
        listAllGames();
	}
	
	/*start GameItemActivity*/
	private void setUpGameItemActivity(Object item){
		Intent intent = new Intent(this, GameItemActivity.class);
		Bundle bundle = new Bundle();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapItem = (HashMap<String, Object>)item;
		bundle.putSerializable("item", mapItem);
		intent.putExtra("param", bundle);
		startActivity(intent);
	}
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			//Log.d(LOG_TAG, adapterView.getItemAtPosition(position).toString()); 
			//Log.d(LOG_TAG, "position: "+String.valueOf(position)+"id: "+String.valueOf(id));
			
			setUpGameItemActivity(adapterView.getItemAtPosition(position));
		}
		
	};
	
	/*
	 * list all games with com.clz.partyhelper.game
	 */
	private void listAllGames(){

        SimpleAdapter adapterList = 
        		new SimpleAdapter(
        				this, 
        				getData(),
        				R.xml.search_list,
        				new String[]{"name", "age", "people_number", "image"},
        				new int[]{R.id.list_item_title, R.id.list_age, R.id.list_people, R.id.list_image});    
        itemLists.setAdapter(adapterList);
        
        Log.d(LOG_TAG, "listView id :"+itemLists.toString() + "height" + itemLists.getHeight());
        itemLists.setOnItemClickListener(itemClickListener);
        
	}
	private List<Map<String, Object>> getData(){
		//TODO getData from database
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
//		map = new HashMap<String, Object>();
//		map.put("name", "Game1");
//		map.put("age", "2-5");
//		map.put("people_number", "20-30");
//		map.put("image", R.drawable.game_pic1);
//		list.add(map);
		
		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		List<Game> listGame = gameSource.getAllGames();
		for(Game game: listGame){
			Log.d(LOG_TAG, game.toString());
			map = new HashMap<String, Object>();
			map.put("name", game.getName());
			map.put("age", game.getAgeRange().toString());
			map.put("people_number", game.getPeopleNumRange().toString());
			map.put("image", game.getImg());
			list.add(map);
		}
		return list;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_menu, menu);
		
		//Log.d(LOG_TAG, "create the list action bar");
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
		case R.id.list_action_search:
			doMySearch("this is a sql query");
			Log.d(LOG_TAG, "Search selected");
			return true;
		case android.R.id.home:	
			/*return to Main Activity, 
			 * finish this
			 * */
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void doMySearch(String query){
		//TODO do search with sqlite
		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		Log.d(LOG_TAG, "GamesDataSource created");
//		Game game = gameSource.randomPick();
//		Log.d(LOG_TAG, game.toString());
//		List<Game> games = gameSource.getGames(null, null, null, null, null, null, Game.Place.indoor);
		List<Game> games = gameSource.getAllGames();
		for(Game game: games){
			Log.d(LOG_TAG, game.toString());
		}

        
	}
	
}
