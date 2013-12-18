package com.clz.partyhelper;

import com.zju.partyhelper.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 *
 */
public class GamePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_page, menu);
		return true;
	}

}
