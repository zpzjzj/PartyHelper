package com.clz.partyhelper.game;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {
	public static final String TABLE_GAMES = "games";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_MIN_AGE = "minAge";
	public static final String COLUMN_MAX_AGE = "maxAge";
	public static final String COLUMN_MIN_PEOPLE_NUM = "minPeopleNum";
	public static final String COLUMN_MAX_PEOPLE_NUM = "maxPeopleNum";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_PLACE = "place";
	public static final String COLUMN_DETAIL = "detail";
	public static final String COLUMN_IMG = "image";
	
	private static final String DATABASE_NAME = "games.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TAG = "SQLHelper";
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GAMES + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NAME + " text not null unique, "
			+ COLUMN_MIN_AGE + " integer,"
			+ COLUMN_MAX_AGE + " integer,"
			+ COLUMN_MIN_PEOPLE_NUM + " integer,"
			+ COLUMN_MAX_PEOPLE_NUM + " integer,"
			+ COLUMN_TYPE + " integer,"
			+ COLUMN_PLACE + " integer,"
			+ COLUMN_DETAIL + " text,"
			+ COLUMN_IMG + " blob not null"
			+ ");";
	// create games(_id integer primary key autoincrement, name text not null, ...);
	
	private Context context;
	public Context getContext(){
		return context;
	}
	
	//split line with delimiter 
	private String[] parseCVSLine(String line){
		final String delimiter  = "#";
		return line.trim().split(delimiter , -1);
	}
	
	public ArrayList<ArrayList<String>> readCVSFile(int resourceId) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(resourceId)));
			String line;
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			while ((line = in.readLine()) != null) {
				ArrayList<String> tuple = new ArrayList<String>(Arrays.asList(parseCVSLine(line)));
				data.add(tuple);
			}
			in.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "read CVS meet exception: " + e.getMessage());
			return null;
		}
	}
	
	/***
	 * parse the strings and insert to database
	 * @param data
	 * @param database
	 */
	public void insert(ArrayList<ArrayList<String>> data, SQLiteDatabase database){
		//debug data is null here
		
		for(ArrayList<String> tuple : data){
			ContentValues cv = new ContentValues();
			int i = 0;
			cv.put(SQLHelper.COLUMN_NAME, tuple.get(i++).trim());
			cv.put(SQLHelper.COLUMN_MIN_AGE, Integer.parseInt(tuple.get(i++).trim()));
			cv.put(SQLHelper.COLUMN_MAX_AGE, Integer.parseInt(tuple.get(i++).trim()));
			cv.put(SQLHelper.COLUMN_MIN_PEOPLE_NUM, Integer.parseInt(tuple.get(i++).trim()));
			cv.put(SQLHelper.COLUMN_MAX_PEOPLE_NUM, Integer.parseInt(tuple.get(i++).trim()));
			cv.put(SQLHelper.COLUMN_TYPE, Game.Type.valueOf(tuple.get(i++).trim()).ordinal());
			//not a constant in Game.Type
			Log.d(TAG, "cv: " + cv.toString() + "$");
			cv.put(SQLHelper.COLUMN_PLACE, Game.Place.valueOf(tuple.get(i++).trim()).ordinal());
			cv.put(SQLHelper.COLUMN_DETAIL, tuple.get(i++).trim());
			String imgName = tuple.get(i++).trim();
			int id = getContext().getResources().getIdentifier(imgName, "drawable", context.getPackageName());
			if(id != 0){
				Bitmap img = BitmapFactory.decodeResource(context.getResources(), id);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				img.compress(Bitmap.CompressFormat.PNG, 100, out);
				cv.put(SQLHelper.COLUMN_IMG, out.toByteArray());	//image
				
				database.insert(SQLHelper.TABLE_GAMES, null, cv);
			}else
				Log.e(TAG, "img resource cannot find: " + imgName);
		}
	}
	
	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;	//save context
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		Log.d(TAG, "database created");
		initialize(database);
	}
	
	private void initialize(SQLiteDatabase database){
		int id = getContext().getResources().getIdentifier("raw/import_data", "raw", context.getPackageName());
		if(id != 0)
			insert(readCVSFile(id), database);
		else
			Log.e(TAG, "database init file cannot open");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
		onCreate(db);
	}
}
