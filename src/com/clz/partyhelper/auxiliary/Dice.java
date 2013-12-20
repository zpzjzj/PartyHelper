package com.example.saizi;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.saizi.R.id;

public class Dice extends Activity {
	private ImageView imView;
	private Button start;
	private Button end;
	private Handler handler;
	private Runnable update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dice);
		getViews();
		handler =new Handler();
		  update=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int s=(int) (Math.random()*6);
				switch (s) {
				case 1:
					imView.setImageResource(R.drawable.s1);
					break;
				case 2:
					imView.setImageResource(R.drawable.s2);
					
					break;
				case 3:
					imView.setImageResource(R.drawable.s3);
					
					break;
				case 4:
					imView.setImageResource(R.drawable.s4);
					
					break;
				case 5:
					imView.setImageResource(R.drawable.s5);
					
					break;
				case 6:
					imView.setImageResource(R.drawable.s6);
					
					break;

				default:
					break;
				}
				handler.postDelayed(update, 3);
			}
		};
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handler.post(update);
				start.setEnabled(false);
			}
		});
		 end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handler.removeCallbacks(update);
				start.setEnabled(true);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_picture, menu);
		return true;
	}
	private void getViews(){
		imView=(ImageView) findViewById(id.imageView1);
		start=(Button) findViewById(id.main_btn_start);
		end=(Button) findViewById(id.main_btn_stop);
	}
}
