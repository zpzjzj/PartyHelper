package com.clz.partyhelper.auxiliary;



import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.clz.partyhelper.R;

public class Picture extends Activity {

	private Draw_Path dPath;

	Button draw_btn;//ѡ��ͼ�εİ�ť
	Button btnRest;
	
	/* ����ÿ��view�Ĳ��ִ�С
	 * This set of layout parameters defaults the width and the height 
	 * 
	 * LayoutParams.MATCH_PARENT��xml�ж���ؼ���С��match_parent����һ��
	 * height������Ϊ700���������Ϊmatch_parent��wrap_content��view��ռ��ȫ����סdraw_btn��ť�ؼ�
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
