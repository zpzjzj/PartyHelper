package com.clz.partyhelper;


import com.clz.partyhelper.auxiliary.ChronometerDemoActivity;
import com.clz.partyhelper.auxiliary.Dice;
import com.clz.partyhelper.auxiliary.MainInterface;

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
/*
 * main activity
 */
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
		
		TextView diceView = (TextView)findViewById(R.id.imgBtnDice);
		diceView.setOnClickListener(this);
		
		TextView clockView = (TextView)findViewById(R.id.imgBtnClock);
		clockView.setOnClickListener(this);	
		
		TextView toolView = (TextView)findViewById(R.id.image_button_tools);
		toolView.setOnClickListener(this);
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
			Intent intentDice = new Intent(this, Dice.class);
			startActivity(intentDice);
			break;
		case R.id.imgBtnClock:
			Intent intentClock = new Intent(this, ChronometerDemoActivity.class);
			startActivity(intentClock);
			break;
		case R.id.image_button_tools:
			Intent intentTools = new Intent(this, MainInterface.class);
			startActivity(intentTools);
			default:break;
		}
	}
	
	private void goTypeList(TextView view){
		String message = String.valueOf(view.getText());
		Intent intent = new Intent(this, ItemsListActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	private void openSearch() {
		/*actually do no search in MainActivity, jump to itemlistActivity*/
		String message = this.getString(R.string.all_type);
		
		Intent intent = new Intent(this, ItemsListActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);	
	}

}
