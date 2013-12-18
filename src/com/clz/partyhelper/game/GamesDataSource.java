package com.clz.partyhelper.game;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GamesDataSource {
	private SQLiteDatabase database;
	private SQLHelper dbHelper;
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
		Game newComment = cursorToGame(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteGame(Game game) {
		long id = game.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(SQLHelper.TABLE_GAMES, SQLHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Game> getAllGames() {
		List<Game> games = new ArrayList<Game>();

		Cursor cursor = database.query(SQLHelper.TABLE_GAMES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Game game = cursorToGame(cursor);
			games.add(game);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return games;
	}
	
	private Game cursorToGame(Cursor cursor) {
		Game game = new Game();
		game.setId(cursor.getLong(cursor.getColumnIndex(SQLHelper.COLUMN_ID)));
		game.setName(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_NAME)));
		game.setAgeRange(new AgeRange(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MIN_AGE)),
					cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MAX_AGE))));
		game.setPeopleNumRange(new PeopleNumRange(cursor.getInt(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MIN_PEOPLE_NUM))),
				cursor.getInt(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_MAX_PEOPLE_NUM)))));
		game.setType(Game.Type.values()[cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_TYPE))]);
		game.setPlace(Game.Place.values()[cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_PLACE))]);
		byte[] blob = cursor.getBlob(cursor.getColumnIndex(SQLHelper.COLUMN_IMG));
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        game.setImg(bmp);
		game.setDetail(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_DETAIL)));
		
		return game;
	}
}
