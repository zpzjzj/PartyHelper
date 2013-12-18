package com.clz.partyhelper.game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GAMES + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NAME + " text not null unique, "
			+ COLUMN_MIN_AGE + "integer,"
			+ COLUMN_MAX_AGE + "integer,"
			+ COLUMN_MIN_PEOPLE_NUM + "integer,"
			+ COLUMN_MAX_PEOPLE_NUM + "integer,"
			+ COLUMN_TYPE + "integer,"
			+ COLUMN_PLACE + "integer,"
			+ COLUMN_DETAIL + "text,"
			+ COLUMN_IMG + "blob not null"
			+ ");";
	// create games(_id integer primary key autoincrement, name text not null, ...);
	
	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
		onCreate(db);
	}
}
