package com.clz.partyhelper;

import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.clz.partyhelper.auxiliary.ChronometerDemoActivity;
import com.clz.partyhelper.auxiliary.Dice;
import com.clz.partyhelper.auxiliary.MainInterface;
import com.clz.partyhelper.game.Game;
import com.clz.partyhelper.game.Game.Place;
import com.clz.partyhelper.game.Game.Type;
import com.clz.partyhelper.game.GamesDataSource;


public class GameItemActivity extends Activity implements OnClickListener{

	private static final String LOG_TAG="GameItemActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_item);
		
		/*enable home button in ActionBar*/
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		/*get the bundle from the selected item 
		 * transferred by ItemsListActivity
		 */
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("param");
		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapItem = (HashMap<String, Object>) bundle.getSerializable("item");
		/*title from mapItem*/
		Log.d(LOG_TAG, String.valueOf(mapItem.get("name")));
		
		String name = (String)mapItem.get("name");
		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		List<Game> gameList = gameSource.getGames(name, null, null, null, null, null, null);
		gameSource.close();
		
		Game game = gameList.get(0);

		TextView textView = (TextView) findViewById(R.id.game_item_title);
		textView.setText((CharSequence) mapItem.get("name"));
		
		ImageView imageView = (ImageView)findViewById(R.id.game_item_image);
		imageView.setImageBitmap(game.getImg());
		
		textView = (TextView)findViewById(R.id.game_item_type_content);
		textView.setText(gameToString(game.getType()));
		textView = (TextView)findViewById(R.id.game_item_age_content);
		textView.setText(game.getAgeRange().toString());
		textView = (TextView)findViewById(R.id.game_item_place_content);
		textView.setText(gameToString(game.getPlace()));
		textView = (TextView)findViewById(R.id.game_item_number_content);
		textView.setText(game.getPeopleNumRange().toString());
		
		textView = (TextView)findViewById(R.id.game_detail);
		//String html= "<html><p>第一行</p><p>第二行</p></html>";
		//textView.setText(Html.fromHtml(html));
		textView.setText(Html.fromHtml(game.getDetail()));
		
		textView = (TextView)findViewById(R.id.image_button_tools);
		textView.setOnClickListener(this);
		
	}

	private String gameToString(Type type){
		switch(type){
		case boyPreffered:return getString(R.string.child_games);
			
		case fresh:return getString(R.string.child_games);
		case girlPreffered:return getString(R.string.girlPreffered);
		case heavyTaste:return getString(R.string.adult_games);
		case puzzle:
			return getString(R.string.puzzle_games);
		default:
			return getString(R.string.all_type);
		}
	}
	private String gameToString(Place place){
		switch (place){
		case indoor:return getString(R.string.indoor);
		case outdoor:return getString(R.string.outdoor);
		default:
			return getString(R.string.all_place);
		
		}
	}
	
	@Override
	public void onClick(View view) {
		Log.d(LOG_TAG, String.valueOf(((TextView)view).getText()));
		
		switch (view.getId()){
		case R.id.image_button_tools:
			Intent intentTools = new Intent(this, MainInterface.class);
			startActivity(intentTools);
			default:break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_item, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
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
