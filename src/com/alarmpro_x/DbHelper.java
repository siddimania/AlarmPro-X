package com.alarmpro_x;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();
	public static final String DB_NAME = "AlarmDatabase.db";
	public static final int DB_VERSION = 1;

	public static final String TABLE = "alarm_times";
	public static final String _ID = "_id";
	public static final String T_ID_MILLISECOND = "alarm_milliseconds";
	public static final String T_HOUR = "alarm_hour";
	public static final String T_MINUTES = "alarm_minutes";
	public static final String T_Am_Pm = "am_pm";
	
	public static final String TABLE2="toggleOnOff";
	public static final String _ID2="_id";
	public static final String TOGGLE_ON_OFF="alarm_toggle";
	
	public static final String TABLE3="snoozeTable";
	public static final String _ID3="_id";
	public static final String SNOOZE_TIME="snooze_time";
	
	public static final String TABLE4="seasonsTable";
	public static final String _ID4="_id";
	public static final String SEASONS_TIME="seasons_time";

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE + "(" + _ID
				+ " INTEGER PRIMARY KEY, " + T_ID_MILLISECOND + " INTEGER, "
				+ T_HOUR + " INTEGER, " + T_MINUTES + " INTEGER, " + T_Am_Pm
				+ " TEXT );";

		String toggleButton="CREATE TABLE " + TABLE2 + "(" +_ID2 + " INTEGER PRIMARY KEY, "+TOGGLE_ON_OFF + " INTEGER );";
		
		String snooze = " CREATE TABLE " + TABLE3 + "("+ _ID3 + " INTEGER PRIMARY KEY, "+ SNOOZE_TIME + " INTEGER );";
		
		String seasons = "CREATE TABLE " + TABLE4 + "("+ _ID4 + " INTEGER PRIMARY KEY, "+ SEASONS_TIME + " INTEGER );";
		
		
		
		Log.d(TAG, "CREATING SQL " + sql);
		db.execSQL(sql);
		Log.d(TAG,"CREATEING TOGGLE "+ toggleButton);
		db.execSQL(toggleButton);
		Log.d(TAG,"CREATEING snooze "+ snooze);
		db.execSQL(snooze);
		Log.d(TAG,"CREATING seasons  "+ seasons);
		db.execSQL(seasons);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "TABLE UPGRADED ");

	}

}
