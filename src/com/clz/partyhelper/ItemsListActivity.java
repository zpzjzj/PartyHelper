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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TextView;

import com.clz.partyhelper.game.AgeRange;
import com.clz.partyhelper.game.Game;
import com.clz.partyhelper.game.Game.Place;
import com.clz.partyhelper.game.Game.Type;
import com.clz.partyhelper.game.GamesDataSource;
import com.clz.partyhelper.game.PeopleNumRange;

public class ItemsListActivity extends Activity{
	private static String LOG_TAG="ItemsListActivity";

	private TextView typeView;	
	private ListView itemLists;

	private Spinner spinnerAge;
	private Spinner spinnerPlace;
	private Spinner spinnerCounts;
	private SearchView searchView;

	private Integer p_minAge;

	private Integer p_maxAge;

	private Integer p_minNum;

	private Integer p_maxNum;

	private Place p_place;

	private Type p_type;

	private String query;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);
		itemLists = (ListView)findViewById(R.id.itemList);  

		query = null;

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		p_minAge = null;
		p_maxAge = null;
		p_minNum = null;
		p_maxNum = null;

		spinnerAge = (Spinner)findViewById(R.id.spinnerAge);
		ArrayAdapter<CharSequence> adapterAge = 
				ArrayAdapter.createFromResource(
						this, 
						R.array.array_age, 
						R.xml.static_spinner);
		adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAge.setOnItemSelectedListener(spinnerListener);
		spinnerAge.setAdapter(adapterAge);

		spinnerPlace = (Spinner)findViewById(R.id.spinnerPlace);
		ArrayAdapter<CharSequence> adapterPlace=
				ArrayAdapter.createFromResource(
						this, 
						R.array.array_place,
						R.xml.static_spinner);

		adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPlace.setOnItemSelectedListener(spinnerListener);
		spinnerPlace.setAdapter(adapterPlace);

		spinnerCounts = (Spinner)findViewById(R.id.spinnerCounts);
		ArrayAdapter<CharSequence> adapterCount = 
				ArrayAdapter.createFromResource(
						this, 
						R.array.array_counts, 
						R.xml.static_spinner);

		adapterCount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCounts.setOnItemSelectedListener(spinnerListener);

		spinnerCounts.setAdapter(adapterCount);

		handleIntent(getIntent());

	}

	@Override
	public void onStop(){
		Log.d("OnStop", "stop!!!!!!!!!!!!!!!!!!");
		super.onStop();
		//finish();
	}

	private void handleIntent(Intent intent) {
		Log.d("handle", "ACTION: "+intent.getAction());
		if (Intent.ACTION_VIEW.equals(intent.getAction())){
			Log.d("handle", "ACTION_VIEW");

		} else if(Intent.ACTION_SEARCH.equals(intent.getAction())){

			query = intent.getStringExtra(SearchManager.QUERY);
			Log.d("handle", "ACTION_SEARCH:"+ query);
			listGames(query, null, null, null, null, null, null);
		}
		else {
			if (intent.getStringExtra(MainActivity.EXTRA_MESSAGE) != null)
				typeMsg = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

			p_type = null;
			Log.d(LOG_TAG, "message: "+typeMsg);
			if (typeMsg.equals(getString(R.string.adult_games))){
				p_type = Type.heavyTaste;
			}
			else if (typeMsg.equals(getString(R.string.child_games))){
				p_type = Type.fresh;
			}
			else if (typeMsg.equals(getString(R.string.all_type))){
				p_type = null;
			}			
			listAllGames();
		}

	}

	private OnItemSelectedListener spinnerListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> adapter, View view, int position,
				long id) {
			switch (adapter.getId()){
			case R.id.spinnerAge:
				AgeRange age =SpinnerMap.getAgeRange(position);
				if (age!=null){
					p_minAge = age.getMin();
					p_maxAge = age.getMax();
				}
				break;
			case R.id.spinnerCounts:
				PeopleNumRange num = SpinnerMap.getNumberRange(position);
				if (num!=null){
					p_minNum = num.getMin();
					p_maxNum = num.getMax();
				}
			case R.id.spinnerPlace:
				p_place = SpinnerMap.getPlace(position);					
			}

			Log.d("handle", "Update Game List");
			updateGameList();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};


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

	private String typeMsg;


	/*update the list, when:
	 * spinners changed
	 * or search_aciton triggered
	 */
	private void updateGameList(){
		listGames(query, p_minAge, p_maxAge, p_minNum, p_maxNum, p_type, p_place);
	}

	/*
	 * list all games with com.clz.partyhelper.game
	 */
	private void listAllGames(){
		listGames(null, null, null, null, null, null, null);
	}

	/*
	 * list game with search condition
	 */
	private void listGames(String name, 
			Integer minAge, Integer maxAge, 
			Integer minNum, Integer maxNum,
			Game.Type type, Game.Place place){

		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		Log.d("SearchInfo", 
				"name :"+ name+
				"minAge: "+ minAge+
				"maxAge: "+maxAge+
				"minNum:" + minNum+
				"maxNum:" + maxNum);
		Log.d("SearchInfo", 
				"type: "+type+
				"place: "+place);
		List<Game> listGame = gameSource.getGames(name, minAge, maxAge, minNum, maxNum, type, place);

		SimpleAdapter adapterList = 
				new SimpleAdapter(
						this, 
						getData(listGame),
						R.xml.search_list,
						new String[]{"name", "age", "people_number", "image"},
						new int[]{R.id.list_item_title, R.id.list_age, R.id.list_people, R.id.list_image});    

		adapterList.setViewBinder(new ViewBinder(){
			@Override
			public boolean setViewValue(View view, Object data, String text){
				if ((view instanceof ImageView) & (data instanceof Bitmap)){
					ImageView iv = (ImageView) view;
					Bitmap bm = (Bitmap)data;
					iv.setImageBitmap(bm);
					return true;
				}
				return false;
			}
		});
		gameSource.close();
		itemLists.setAdapter(adapterList);

		Log.d(LOG_TAG, "listView id :"+itemLists.toString() + "height" + itemLists.getHeight());
		itemLists.setOnItemClickListener(itemClickListener);

	}
	private List<Map<String, Object>> getData(List<Game> listGame){
		//TODO getData from database
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		for(Game game: listGame){
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
		searchView = (SearchView)menu.findItem(R.id.list_action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
		searchView.setOnQueryTextListener(new OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO add search suggestion
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				Log.d("handle", "ACTION QUERY TEXT SUBMIT" + query);

				listGames(query, null, null, null, null, null, null);
				return false;
			}
			
		});
		typeView = (TextView)menu.findItem(R.id.list_action_type).getActionView();
		typeView.setGravity(Gravity.CENTER);
		typeView.setWidth(300);
		typeView.setTextSize(20);
		typeView.setText(typeMsg);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		case R.id.list_action_search:
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
}
