package com.clz.partyhelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.clz.partyhelper.MESSAGE";
	private final static String LOG_TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "create main activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		Log.d(LOG_TAG, actionBar.toString());
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		Log.d(LOG_TAG, "create options menu");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		Log.d(LOG_TAG, "options selected");
		switch (item.getItemId()){
		case R.id.action_search:

			openSearch();
			return true;
		case R.id.action_settings:
			openSetting();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private void openSearch() {
		Intent intent = new Intent(this, ItemsListActivity.class);
		startActivity(intent);		
	}

	private void openSetting() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(View view){

	}

}
