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
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	public final static String EXTRA_MESSAGE = "com.clz.partyhelper.MESSAGE";
	private final static String LOG_TAG = "MainActivity";
	private TextView puzzleGameView;
	private TextView heavyGameView;
	private TextView childGameView;
	private TextView shuffleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "create main activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		Log.d(LOG_TAG, actionBar.toString());
		
		puzzleGameView = (TextView)findViewById(R.id.imgBtnType_1);
		puzzleGameView.setOnClickListener(this);
		heavyGameView = (TextView)findViewById(R.id.imgBtnType_2);
		heavyGameView.setOnClickListener(this);
		childGameView = (TextView)findViewById(R.id.imgBtnType_3);
		childGameView.setOnClickListener(this);
		shuffleView = (TextView)findViewById(R.id.imgBtnShuffle);
		shuffleView.setOnClickListener(this);
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
	
	@Override
	public void onClick(View view) {
		Log.d(LOG_TAG, String.valueOf(((TextView)view).getText()));
		
		switch (view.getId()){
		case R.id.imgBtnType_1:goTypeList((TextView)view);
			break;
		case R.id.imgBtnType_2:goTypeList((TextView)view);
			break;
		case R.id.imgBtnType_3:goTypeList((TextView)view);
			break;
		case R.id.imgBtnShuffle:
			Intent intentShake = new Intent(this, ShakeActivity.class);
			startActivity(intentShake);
			break;
		case R.id.imgBtnDice:
			//TODO new a Intent to Dice
			break;
		case R.id.imgBtnClock:
			//TODO new a Intent to Clock
			break;
			default:break;
		}
	}
	
	private void goTypeList(TextView view){
		String message = String.valueOf(view.getText());
		Intent intent = new Intent(this, ItemsListActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
		//finish();
	}
	private void openSearch() {
		String message = new String("全部类型");
		Intent intent = new Intent(this, ItemsListActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);	
		//finish();
	}

	private void openSetting() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(View view){

	}



}
