package com.example.saizi;

import com.example.saizi.Draw_Path;
import com.example.saizi.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class Picture extends Activity {

	private Draw_Path dPath;

	Button draw_btn;//选择图形的按钮
	Button btnRest;
	
	/* 设置每个view的布局大小
	 * This set of layout parameters defaults the width and the height 
	 * 
	 * LayoutParams.MATCH_PARENT和xml中定义控件大小的match_parent属性一样
	 * height：设置为700，如果设置为match_parent、wrap_content则view画占满全屏挡住draw_btn按钮控件
	 */
	LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, 580);
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        
        btnRest = (Button) findViewById(R.id.btnReset);
        
        draw_btn = (Button)findViewById(R.id.draw_button);
        
        draw_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(dPath == null){
				dPath = new Draw_Path(getApplicationContext());
				addContentView(dPath, lParams);
				}
				else{
					dPath = null;
					dPath = new Draw_Path(getApplicationContext());
					addContentView(dPath, lParams);
				}
			}
		});
        
		btnRest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Paint clearPaint = new Paint();
				clearPaint.setColor(Color.WHITE);
				dPath.canvas.drawPaint(clearPaint);
				dPath=null;
				dPath = new Draw_Path(getApplicationContext());
				addContentView(dPath, lParams);

			}

		});
        
    }

    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_picture, menu);
        return true;
    }

}
