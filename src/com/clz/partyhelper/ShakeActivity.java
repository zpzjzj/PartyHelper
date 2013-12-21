package com.clz.partyhelper;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.clz.partyhelper.game.Game;
import com.clz.partyhelper.game.GamesDataSource;

@SuppressLint("HandlerLeak")
public class ShakeActivity extends Activity {
	private static final String LOG_TAG="ShakeActivity";
	private static final int SENSOR_SHAKE = 10;
	
	private SensorManager sensorManager;
	private Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

	}
	/*Handle the motion*/
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:

				pickGame();
				break;
			}
		}		
	};
	

	/*randomly pick a game*/
	private void pickGame(){
		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		Game game = gameSource.randomPick();
		gameSource.close();
		
		Intent intent = new Intent(this, GameItemActivity.class);
		Bundle bundle = new Bundle();

		HashMap<String, Object> mapItem = new HashMap<String, Object>();
		mapItem.put("name", game.getName());
		bundle.putSerializable("item", mapItem);
		intent.putExtra("param", bundle);
		startActivity(intent);
		finish();
		
		Log.i(LOG_TAG, "Shake detected!");
	}
	
	/*accelerate and gravity sensor*/
	private SensorEventListener sensorEventListener = new SensorEventListener(){

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {			
			float[] values = event.values;
			float x = values[0]; // x, right +, left -
			float y = values[1]; // y, front +, back -
			float z = values[2]; // z, up +, down -;


			int threshold = 15;
			if (Math.abs(x) > threshold || Math.abs(y) > threshold) {
				Log.i(LOG_TAG, "x :" + x + ", y :" + y + ", z :" + z);
				vibrator.vibrate(200);
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}

		
	};
	
	@Override
	protected void onResume(){
		super.onResume();
		if (sensorManager != null){
			sensorManager.registerListener(
					sensorEventListener, 
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (sensorManager!=null){
			/*remove the listener*/
			sensorManager.unregisterListener(sensorEventListener);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shake, menu);
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
