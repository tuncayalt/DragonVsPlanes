package com.tuncay.dragonvsplanes.dblayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class CustomDBAdapter {
	private static final String DATABASE_NAME = "BubblesEra.db";
	private static final String DATABASE_TABLE = "BubblesEraScoreTable";
	private static final int DATABASE_VERSION = 2;
	
	public static final String KEY_ID="_id";
	
	public static final String KEY_NAME="name";
	public static final int NAME_COLUMN = 1;
	
	public static final String KEY_SCORE="score";
	public static final int SCORE_COLUMN = 2;
	
	private static final String DATABASE_CREATE = "create table " +
								DATABASE_TABLE + " (" + KEY_ID +
								" integer primary key autoincrement, " +
								KEY_NAME + " text not null, " +
								KEY_SCORE + " integer not null);";
	
	private SQLiteDatabase db;
	private dbHelper dbHelper;
	
	public CustomDBAdapter(Context context) 
	{
		dbHelper = new dbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	 
	private void open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
	}
	
	 
	public void close() 
	{
		db.close();
	}
	
	public String getNameColumn()
	{
		return KEY_NAME;
	}
	
	public String getScoreColumn()
	{
		return KEY_SCORE;
	}
	
	public long insertScore(String name, int score)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_SCORE, score);
		open();
		long id = db.insert(DATABASE_TABLE, null, newValues);
		close();
		return id;
	}
	
	 
	public boolean isScoreInTop(int score) 
	{
		Cursor cur = getScores();
		if(cur.moveToLast() && cur.getCount() >= 10 && cur.getInt(SCORE_COLUMN) >= score)
		{
			close();
			return false;
		}
		close();
		return true;
	}
	
	 
	public Cursor getScores() 
	{
		open();
		Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_SCORE}, 
				null, null, null, null, KEY_SCORE + " DESC", "10");
		return cursor;
	}
	
	public Cursor getLastEnteredName() 
	{
		open();
		Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_SCORE}, 
				null, null, null, null, KEY_ID + " DESC", "1");
		return cursor;
	}
	 
	public static class dbHelper extends SQLiteOpenHelper 
	{		
		public dbHelper(Context context, String name, CursorFactory factory, int version) 
		{
			super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase _db) 
		{
			_db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
		{
			Log.w("TaskDBAdapter", "Upgrading from version " +
					_oldVersion + " to " +
					_newVersion + ", which will destroy all old data");
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(_db);			
		}
	}
}
