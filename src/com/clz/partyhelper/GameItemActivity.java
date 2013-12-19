package com.clz.partyhelper;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class GameItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_item);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("param");
		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapItem = (HashMap<String, Object>) bundle.getSerializable("item");
		
		TextView title = (TextView) findViewById(R.id.game_item_title);
		title.setText((CharSequence) mapItem.get("title"));
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
