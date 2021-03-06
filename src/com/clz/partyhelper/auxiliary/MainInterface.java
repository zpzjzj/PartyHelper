package com.clz.partyhelper.auxiliary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.clz.partyhelper.R;



public class MainInterface extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_interface);
		
		TextView btnDice = (TextView) findViewById(R.id.btnDice);

		TextView btnPicture = (TextView) findViewById(R.id.btnPicture);

		TextView btnTimer = (TextView) findViewById(R.id.btnTimer);
		
		btnDice.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainInterface.this, Dice.class);
				startActivity(intent);
			}
			
		});
		
		
		btnPicture.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainInterface.this, Picture.class);
				startActivity(intent);
			}
			
		});
		
		
		btnTimer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainInterface.this, ChronometerDemoActivity.class);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_interface, menu);
		return true;
	}

}
