package com.clz.partyhelper.game;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/**
 * for accessing data of games
 * usage example:
 *
		GamesDataSource gameSource = new GamesDataSource(this);
		gameSource.open();
		Log.d(LOG_TAG, "GamesDataSource created");
//		Game game = gameSource.randomPick();
//		Log.d(LOG_TAG, game.toString());
//		List<Game> games = gameSource.getGames(null, null, null, null, null, null, Game.Place.indoor);
		List<Game> games = gameSource.getAllGames();
		for(Game game: games){
			Log.d(LOG_TAG, game.toString());
		}
		gameSource.close();
 * @author zhaoping
 *
 */
public class GamesDataSource {
	private SQLiteDatabase database = null;
	private SQLHelper dbHelper = null;
	private static String TAG = "GamesDataSource";
	
	private String[] allColumns = { SQLHelper.COLUMN_ID, SQLHelper.COLUMN_NAME,
			SQLHelper.COLUMN_MIN_AGE, SQLHelper.COLUMN_MAX_AGE,
			SQLHelper.COLUMN_MIN_PEOPLE_NUM, SQLHelper.COLUMN_MAX_PEOPLE_NUM,
			SQLHelper.COLUMN_TYPE, SQLHelper.COLUMN_PLACE,
			SQLHelper.COLUMN_DETAIL, SQLHelper.COLUMN_IMG };

	public GamesDataSource(Context context) {
		dbHelper = new SQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.d(TAG, ("open() called, database is null? " + (database == null)));
	}

	public void close() {
		dbHelper.close();
	}

	public Game createGame(String name, AgeRange ageRange,
			PeopleNumRange peopleNumRange, Game.Type type, Game.Place place,
			String detail, Bitmap img) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		img.compress(Bitmap.CompressFormat.PNG, 100, out);
		ContentValues cv = new ContentValues();
		cv.put(SQLHelper.COLUMN_NAME, name);
		cv.put(SQLHelper.COLUMN_MIN_AGE, ageRange.getMin());
		cv.put(SQLHelper.COLUMN_MAX_AGE, ageRange.getMax());
		cv.put(SQLHelper.COLUMN_MIN_PEOPLE_NUM, peopleNumRange.getMin());
		cv.put(SQLHelper.COLUMN_MAX_PEOPLE_NUM, peopleNumRange.getMax());
		cv.put(SQLHelper.COLUMN_TYPE, type.ordinal());
		cv.put(SQLHelper.COLUMN_PLACE, place.ordinal());
		cv.put(SQLHelper.COLUMN_DETAIL, detail);
		cv.put(SQLHelper.COLUMN_IMG, out.toByteArray());

		long insertId = database.insert(SQLHelper.TABLE_GAMES, null, cv);
		Cursor cursor = database.query(SQLHelper.TABLE_GAMES, allColumns, 
				SQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Game game = cursorToGame(cursor);
		cursor.close();
		return game;
	}

	public void deleteGame(Game game) {
		long id = game.getId();
		System.out.println("Game deleted with id: " + id);
		database.delete(SQLHelper.TABLE_GAMES, SQLHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Game> getAllGames() {
		return getGames(database.query(SQLHelper.TABLE_GAMES,
				allColumns, null, null, null, null, null));
	}
	
	/**
	 * randomly pick a tuple from the database
	 * @return game picked
	 */
	public Game randomPick(){
		Cursor mCount= database.rawQuery("select count(*) from " + SQLHelper.TABLE_GAMES, null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		
		int random = (int)(Math.random() * count + 1);
		Cursor cursor = database.query(SQLHelper.TABLE_GAMES,
				allColumns, null, null, null, null, null);
		for(int i = 0; i != random; i++){
			cursor.moveToNext();
		}
		return cursorToGame(cursor);
	}
	
	/**
	 * the following options can be 
	 * @param name		name of game (substring is enough)
	 * @param minAge	minimal age bound, games returned are within range, below likely the same
	 * @param maxAge
	 * @param minNum
	 * @param maxNum
	 * @param type		enum value of Game
	 * @param place
	 * @return
	 */
	public List<Game> getGames(String name, Integer minAge, Integer maxAge, Integer minNum, Integer maxNum,
			Game.Type type, Game.Place place){
		String selection = null;
		Boolean hasFormer = false;
		{
			String defaultStr = new String("");
			HashMap<Object, String> map = new HashMap<Object, String>(); 
			map.put(name, SQLHelper.COLUMN_NAME + " LIKE '%" + (name != null ?name + "%'": defaultStr));
			map.put(minAge, SQLHelper.COLUMN_MIN_AGE + " <= " + (minAge != null ? minAge.toString(): defaultStr));
			map.put(maxAge, SQLHelper.COLUMN_MAX_AGE + " >= " + (maxAge != null ? maxAge.toString(): defaultStr));
			map.put(minNum, SQLHelper.COLUMN_MIN_PEOPLE_NUM + " <= " + (minNum != null ? minNum.toString(): defaultStr));
			map.put(maxNum, SQLHelper.COLUMN_MAX_PEOPLE_NUM + " >= " + (maxNum != null ? maxNum.toString(): defaultStr));
			map.put(type, SQLHelper.COLUMN_TYPE + " == " + (type != null ? Integer.toString(type.ordinal()): defaultStr));
			map.put(place, SQLHelper.COLUMN_PLACE + " == " + (place != null ? Integer.toString(place.ordinal()): defaultStr));	
		
			for(Entry<Object, String> entry : map.entrySet()){
				if(entry.getKey() != null){
					if(hasFormer)
						selection += " and " + entry.getValue();
					else{
						selection = new String(entry.getValue());
						hasFormer = true;
					}
				}
			}
		}
		Log.d(TAG, "selection: " + selection);
		Cursor cursor = database.query(SQLHelper.TABLE_GAMES, allColumns, selection, null, null, null, null);
		return getGames(cursor);
	}

	//from cursor returned by database query to a list of games 
	private List<Game> getGames(Cursor cursor) {
		cursor.moveToFirst();
		List<Game> games = new ArrayList<Game>();
		while (!cursor.isAfterLast()) {
			Game game = cursorToGame(cursor);
			games.add(game);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return games;
	}
	
	//convert from data of database to game
	private Game cursorToGame(Cursor cursor) {
		Game game = new Game();
		game.setId(cursor.getLong(cursor.getColumnIndex(SQLHelper.COLUMN_ID)));
		game.setName(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_NAME)));
		game.setAgeRange(new AgeRange(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MIN_AGE)),
					cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MAX_AGE))));
		PeopleNumRange t = new PeopleNumRange(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MIN_PEOPLE_NUM)),
				cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MAX_PEOPLE_NUM))); //TODO bug
		Log.d(TAG, "range from " + t.getMin() + " to " + t.getMax());
		game.setPeopleNumRange(t);
		game.setType(Game.Type.values()[cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_TYPE))]);
		game.setPlace(Game.Place.values()[cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_PLACE))]);
		byte[] blob = cursor.getBlob(cursor.getColumnIndex(SQLHelper.COLUMN_IMG));
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        game.setImg(bmp);
		game.setDetail(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_DETAIL)));
		
		return game;
	}
}
