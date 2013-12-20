package com.clz.partyhelper.auxiliary;

import com.clz.partyhelper.R;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

public class ChronometerDemoActivity extends Activity {

	private int startTime = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chronometer_demo);

		final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);

		Button btnStart = (Button) findViewById(R.id.btnStart);

		Button btnStop = (Button) findViewById(R.id.btnStop);

		Button btnRest = (Button) findViewById(R.id.btnReset);

		final EditText edtSetTime = (EditText) findViewById(R.id.edt_settime);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("--��ʼ��ʱ---");
				String ss = edtSetTime.getText().toString();
				if (!(ss.equals("") && ss != null)) {
					startTime = Integer.parseInt(edtSetTime.getText()
							.toString());
				}
				// ���ÿ�ʼ��ʱʱ��
				chronometer.setBase(SystemClock.elapsedRealtime());
				// ��ʼ��ʱ
				chronometer.start();

			}
		});

		btnStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ֹͣ
				chronometer.stop();
			}

		});

		// ����
		btnRest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chronometer.setBase(SystemClock.elapsedRealtime());

			}

		});
		chronometer
				.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
					@Override
					public void onChronometerTick(Chronometer chronometer) {
						// ���ʼ��ʱ�����ڳ�����startime��
						if (SystemClock.elapsedRealtime()
								- chronometer.getBase() > startTime * 1000) {
							chronometer.stop();
							// ���û���ʾ
							showDialog();
						}
					}
				});
	}

	protected void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setIcon(R.drawable.eb28d25);
		builder.setTitle("����").setMessage("ʱ�䵽")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
