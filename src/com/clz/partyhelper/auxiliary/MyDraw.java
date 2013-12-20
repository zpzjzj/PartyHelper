package com.clz.partyhelper.auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

/*
 * ��view
 * ����ͼ����view�඼�̳�����view
 * 
 * ����view���ж�����ͳһ��paint��bitmap��canvas
 * �Լ���������Ҫ�õ���3����downPoint,movePoint,upPoint
 * 
 */
public class MyDraw extends View {
	
	//public���ͣ�������Ҫ�õ�
	public Point downPoint,movePoint,upPoint;
	public Paint paint;//��������
	public Canvas canvas;//����
	public Bitmap bitmap;//λͼ
	public int downState;
	public int moveState;
	
	
	
	
	
	public MyDraw(Context context){
		super(context);
		// TODO Auto-generated constructor stub
		
		  paint=new Paint(Paint.DITHER_FLAG);//����һ������
		  bitmap = Bitmap.createBitmap(480, 700, Bitmap.Config.ARGB_8888); //����λͼ�Ŀ��
		  canvas=new Canvas(bitmap);
		  
		  //���û���
		  paint.setStyle(Style.STROKE);//���÷����
		  paint.setStrokeWidth(5);//�ʿ�5����
		  paint.setColor(Color.RED);//����Ϊ���
		  paint.setAntiAlias(true);//��ݲ���ʾ
		  
		  downPoint = new Point();
		  movePoint = new Point();
		  upPoint = new Point();
		  
		//  Log.i("MyDraw", "bitmap::::::::::::::::::"+bitmap);
	}
	
	
	 @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}
	 
}
