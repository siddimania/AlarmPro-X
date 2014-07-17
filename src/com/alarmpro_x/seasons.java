/*
siddhartha dimania
*/
package com.alarmpro_x;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class seasons extends Activity implements OnClickListener {

	private static final String TAG = DbHelper.class.getSimpleName();
	AlarmManager am;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seasons);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);
		
	
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		TextView text2 = (TextView) findViewById(R.id.textView2);
		text2.setTypeface(tf);

		TextView text3 = (TextView) findViewById(R.id.textView3);
		text3.setTypeface(tf);

		TextView text4 = (TextView) findViewById(R.id.textView4);
		text4.setTypeface(tf);

		TextView text5 = (TextView) findViewById(R.id.textView5);
		text5.setTypeface(tf);

		TextView text6 = (TextView) findViewById(R.id.textView6);
		text6.setTypeface(tf);

		CheckBox ch1 = (CheckBox) findViewById(R.id.checkBox1);
		CheckBox ch2 = (CheckBox) findViewById(R.id.checkBox2);
		CheckBox ch3 = (CheckBox) findViewById(R.id.checkBox3);
		CheckBox ch4 = (CheckBox) findViewById(R.id.checkBox4);

		Log.d(TAG, "GETTING THE VALUE");
		
		ch1.setOnClickListener(this);
		ch2.setOnClickListener(this);
		ch3.setOnClickListener(this);
		ch4.setOnClickListener(this);

	}

	public void onClick(View view) {

		boolean checked = ((CheckBox) view).isChecked();
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteDatabase db2= dbHelper.getWritableDatabase();
		
		int newMinute = 0, hour = 0, minutes = 0;
		String am_pm = "AM";

		Calendar cal = Calendar.getInstance();
		int nowMinutes = cal.get(Calendar.MINUTE);
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowSeconds=cal.get(Calendar.SECOND);
		
		Cursor c2 = db.query(DbHelper.TABLE, new String[] { dbHelper.T_HOUR,
				dbHelper.T_MINUTES, dbHelper.T_Am_Pm }, null, null, null, null,null);

		while (c2.moveToNext()) {
			hour = c2.getInt(c2.getColumnIndex(dbHelper.T_HOUR));
			minutes = c2.getInt(c2.getColumnIndex(dbHelper.T_MINUTES));
			am_pm = c2.getString(c2.getColumnIndex(dbHelper.T_Am_Pm));
			
		}
		System.out.println(hour + "  "+ minutes + "  "+ am_pm );
		
		if(nowHour>12){
			nowHour= nowHour-12;
		}
		
		long seconds;
		if(nowHour >  hour){
			seconds= (long)Math.abs((((hour * 60 + minutes) * 60) + (12 * 60 * 60 -((nowHour * 60 + nowMinutes) * 60 + nowSeconds))) * 1000);
			System.out.println("now time is greater than alarm hour");
		}
			else if(nowHour <= hour ) {
			seconds= (long)Math.abs((((hour * 60 + minutes) * 60) - (((nowHour) * 60 + nowMinutes) * 60 + nowSeconds)) * 1000);
			System.out.println("now time is less than hour and now hour is greater than 12");
			}
			else {
			seconds= (long)Math.abs((((hour * 60 + minutes) * 60) - (((nowHour) * 60 + nowMinutes) * 60 + nowSeconds)) * 1000);
			System.out.println("now time is less than 12");
			}
		newMinute = minutes;
		ContentValues values2 = new ContentValues();
		int intent_id = (int) System.currentTimeMillis();
		switch (view.getId()) {
		case R.id.checkBox1:
			if (checked) {

				Intent intent = new Intent(seasons.this, AlarmStart.class);
				intent.putExtra("alarm_time",seconds);

				intent_id = intent_id + 5;
				
				System.out.println(seconds);
				
				PendingIntent alarmPendingIntent = PendingIntent.getActivity(
						getApplicationContext(), intent_id, intent,
						PendingIntent.FLAG_ONE_SHOT);

				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
						SystemClock.elapsedRealtime() + seconds + 5*60*1000, alarmPendingIntent);			

				values2.put(DbHelper.T_ID_MILLISECOND, intent_id);

				if ((newMinute + 5 )>= 60) {
					newMinute = 5 - (60 - minutes);
					hour += 1;
				} else
					newMinute += 5;

				values2.put(DbHelper.T_HOUR, hour);
				values2.put(DbHelper.T_MINUTES, newMinute);
				values2.put(DbHelper.T_Am_Pm, am_pm);

				db2.insertWithOnConflict(DbHelper.TABLE, null, values2,
						SQLiteDatabase.CONFLICT_REPLACE);

			}
			break;
		case R.id.checkBox2:
			if (checked) {

				for (int i = 5; i <= 10; i += 5) {
					Intent intent = new Intent(seasons.this, AlarmStart.class);
//					intent.putExtra("alarm_time", hour * 60 * 60 * 1000
//							+ (minutes + i) * 60 * 1000);

					if (i == 5) {
						intent_id = intent_id + 5;
						
						PendingIntent alarmPendingIntent1 = PendingIntent
								.getActivity(getApplicationContext(),intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);
						
						System.out.println(seconds);
						
						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() + seconds + 5*60*1000,alarmPendingIntent1);
						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 5;
					} 
					if (i == 10) {
						intent_id = intent_id + 10;
						PendingIntent alarmPendingIntent2 = PendingIntent
								.getActivity(getApplicationContext(),intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);
						System.out.println(seconds);
						
						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() + seconds + 10*60*1000,
								alarmPendingIntent2);
						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 5;

					}

					if (newMinute >= 60) {
						newMinute = i - (60 - minutes);
						hour += 1;
					} 
					
					

					values2.put(DbHelper.T_HOUR, hour);
					values2.put(DbHelper.T_MINUTES, newMinute);
					values2.put(DbHelper.T_Am_Pm, am_pm);

					db2.insertWithOnConflict(DbHelper.TABLE, null, values2,
							SQLiteDatabase.CONFLICT_REPLACE);
				}
			}
			break;
		case R.id.checkBox3:
			if (checked) {

				for (int i = 3; i <= 9; i += 3) {
					Intent intent = new Intent(seasons.this, AlarmStart.class);
//					intent.putExtra("alarm_time", hour * 60 * 60 * 1000
//							+ (minutes + i) * 60 * 1000);

					if (i == 3) {

						intent_id = intent_id + 3;
						PendingIntent alarmPendingIntent1 = PendingIntent
								.getActivity(getApplicationContext(),intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);

						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + seconds + 3*60*1000,
								alarmPendingIntent1);
						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 3;
					} else if (i == 6) {

						intent_id = intent_id + 6;
						PendingIntent alarmPendingIntent2 = PendingIntent
								.getActivity(getApplicationContext(),
										intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);

						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() + seconds + 6*60*1000,
								alarmPendingIntent2);

						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 3;

					}

					else if (i == 9) {
						intent_id = intent_id + 9;
						PendingIntent alarmPendingIntent3 = PendingIntent
								.getActivity(getApplicationContext(),
										intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);

						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() + seconds + 9*60*1000,
								alarmPendingIntent3);

						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 3;
					}

					if (newMinute >= 60) {
						newMinute = i - (60 - minutes);
						hour += 1;
					} 
						

					values2.put(DbHelper.T_HOUR, hour);
					values2.put(DbHelper.T_MINUTES, newMinute);
					values2.put(DbHelper.T_Am_Pm, am_pm);

					db2.insertWithOnConflict(DbHelper.TABLE, null, values2,
							SQLiteDatabase.CONFLICT_REPLACE);
				}
			}
			break;
		case R.id.checkBox4:
			if (checked) {

				for (int i = 7; i <= 14; i += 7) {
					Intent intent = new Intent(seasons.this, AlarmStart.class);
//					intent.putExtra("alarm_time", hour * 60 * 60 * 1000
//							+ (minutes + i) * 60 * 1000);

					if (i == 7) {
						intent_id = intent_id + 7;
						PendingIntent alarmPendingIntent1 = PendingIntent
								.getActivity(getApplicationContext(),
										intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);

						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() + seconds + 7*60*1000,
								alarmPendingIntent1);
						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 7;
					} else if (i == 14) {
						intent_id = intent_id + 14;
						PendingIntent alarmPendingIntent2 = PendingIntent
								.getActivity(getApplicationContext(),
										intent_id, intent,
										PendingIntent.FLAG_ONE_SHOT);

						am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
								SystemClock.elapsedRealtime() +seconds + 14*60*1000,
								alarmPendingIntent2);
						values2.put(DbHelper.T_ID_MILLISECOND, intent_id);
						newMinute += 7;
					}

					if (newMinute >= 60) {
						newMinute = i - (60 - minutes);
						hour += 1;
					}
						

					values2.put(DbHelper.T_HOUR, hour);
					values2.put(DbHelper.T_MINUTES, newMinute);
					values2.put(DbHelper.T_Am_Pm, am_pm);

					db2.insertWithOnConflict(DbHelper.TABLE, null, values2,
							SQLiteDatabase.CONFLICT_REPLACE);
				}
			}
			break;
		}

		db.close();
		db2.close();
		dbHelper.close();
		finish();

	}

}
