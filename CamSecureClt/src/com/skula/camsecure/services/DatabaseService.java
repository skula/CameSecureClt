package com.skula.camsecure.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseService {
	private static final String DATABASE_NAME = "camsecure.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_PARAMETER = "parameter";
	
	public static final String PARAMETER_IP = "serverip";
	public static final String PARAMETER_PORT = "serverport";
	public static final String PARAMETER_MAILADDRESS = "mailaddress";
	public static final String PARAMETER_LOGIN = "login";

	private Context context;
	private SQLiteDatabase database;
	private SQLiteStatement statement;

	public DatabaseService(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.database = openHelper.getWritableDatabase();
	}

	public void bouchon() {				
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETER);
		database.execSQL("CREATE TABLE " + TABLE_PARAMETER + "(key TEXT PRIMARY KEY, label TEXT)");

		database.execSQL("insert into " + TABLE_PARAMETER + "(key, label) values('" + PARAMETER_IP + "', '')");
		database.execSQL("insert into " + TABLE_PARAMETER + "(key, label) values('" + PARAMETER_PORT + "', '')");
		database.execSQL("insert into " + TABLE_PARAMETER + "(key, label) values('" + PARAMETER_LOGIN + "', '')");
		database.execSQL("insert into " + TABLE_PARAMETER + "(key, label) values('" + PARAMETER_MAILADDRESS + "', '')");
	}
	
	

	/*****************************************************/
	/******************* PARAMETER ***********************/
	/*****************************************************/
	public void insertParameter(String key, String label) {
		String sql = "insert into " + TABLE_PARAMETER + "(key, label) values (?,?)";
		statement = database.compileStatement(sql);
		statement.bindString(1, key);
		statement.bindString(2, label);
		statement.executeInsert();
	}
	
	public void updateParameter(String key, String label) {
		ContentValues args = new ContentValues();
		args.put("label", label);;
		database.update(TABLE_PARAMETER, args, "key='" + key + "'", null);
	}

	public void deleteParameter(String key) {
		database.delete(TABLE_PARAMETER, "key='" + key + "'", null);
	}

	public String getParameterLabel(String key) {
		Cursor cursor = database.query(TABLE_PARAMETER,
				new String[] { "label" }, "key='" + key + "'", null,
				null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String res = cursor.getString(0);
				return cursor.getString(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return null;
	}
	
	private static class OpenHelper extends SQLiteOpenHelper {
		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("CREATE TABLE " + TABLE_PARAMETER + "(key TEXT PRIMARY KEY, label TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETER);
			onCreate(database);
		}
	}
}