package com.alarmpro_x;

import com.alarmpro_x.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class alarm_list extends Activity {

	private static final String TAG = "_tag";
	 DbHelper dbHelper ;
	 SQLiteDatabase db ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_list);
		
		dbHelper= new DbHelper(getApplicationContext());
		db= dbHelper.getReadableDatabase();
		
		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

		TextView text2 = (TextView) findViewById(R.id.deleteAlarms);
		text2.setTypeface(tf);

		gettingdata();
		addListenerOnBack();
		addListenerOnImageButtonList();
		addListenerOnDeleteAllAlarm();
	}

	private void addListenerOnDeleteAllAlarm() {
		RelativeLayout ry = (RelativeLayout) findViewById(R.id.relativeEnd);
		ry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				new AlertDialog.Builder(alarm_list.this)
						.setTitle("Remove Alarms")
						.setIcon(R.drawable.weather_icon)
						.setMessage("You Want To Remove All Alarms: ")

						.setNegativeButton("Remove",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,int which) {

										DbHelper dbHelper = new DbHelper(getApplicationContext());
										SQLiteDatabase db = dbHelper.getWritableDatabase();

										Cursor cursorRemoveAlarms = db.query(DbHelper.TABLE,new String[] {
																									DbHelper._ID,
																									DbHelper.T_ID_MILLISECOND,
																									DbHelper.T_HOUR,
																									DbHelper.T_MINUTES,
																									DbHelper.T_Am_Pm },
																									null, null, null, null,null);

										int getMilliColumIndex = cursorRemoveAlarms
												.getColumnIndex(dbHelper.T_ID_MILLISECOND);
										int getHoursColumIndex = cursorRemoveAlarms
												.getColumnIndex(dbHelper.T_HOUR);
										int getMinutesColumIndex = cursorRemoveAlarms
												.getColumnIndex(dbHelper.T_MINUTES);

										while (cursorRemoveAlarms.moveToNext()) {
											int hours = cursorRemoveAlarms
													.getInt(getHoursColumIndex);
											int milliseconds = cursorRemoveAlarms
													.getInt(getMilliColumIndex);
											int minutes = cursorRemoveAlarms
													.getInt(getMinutesColumIndex);
											long seconds = System.currentTimeMillis()+ hours* 60* 60* 1000 + minutes * 60 * 1000;

											Intent intent = new Intent(alarm_list.this, AlarmStart.class);
											intent.putExtra("alarm_time", seconds);
											PendingIntent.getActivity(alarm_list.this, milliseconds,intent, PendingIntent.FLAG_ONE_SHOT).cancel();
											Log.d(TAG, "In the while loop cancelling the alarms");
										}

										db.delete(dbHelper.TABLE, null, null);

										Toast.makeText(alarm_list.this," All Alarms Removed ",Toast.LENGTH_SHORT).show();
										db.close();
										dbHelper.close();
										
										NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
										mNotificationManager.cancel(10);
										
										mNotificationManager.cancel(9);

										Intent intent = new Intent(v.getContext(), alarm_list.class);
										startActivityForResult(intent, 0);
										finish();

									}
								})
						.setPositiveButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(alarm_list.this,
												" Cancelled ", Toast.LENGTH_SHORT)
												.show();

									}
								}).create().show();

			}
		});

	}

	public void gettingdata() {
		try {
			
			ListView tv = (ListView) findViewById(R.id.ListViewAlarmList);

			Cursor cursor = db.query(DbHelper.TABLE, new String[] {
					DbHelper._ID, DbHelper.T_ID_MILLISECOND, DbHelper.T_HOUR,
					DbHelper.T_MINUTES, DbHelper.T_Am_Pm }, null, null, null,
					null, null);

			startManagingCursor(cursor);
			SimpleCursorAdapter adapter;

			String[] from = { dbHelper.T_HOUR, dbHelper.T_MINUTES,
					dbHelper.T_Am_Pm };
			int[] to = { R.id.hoursId, R.id.minutesId, R.id.Am_Pm };

			adapter = new SimpleCursorAdapter(getApplicationContext(),
					R.layout.alarm_time_layout, cursor, from, to, 0);

			tv.setAdapter(adapter);
			Log.d(TAG, "SET SIMPLE CURSOR");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addListenerOnImageButtonList() {

		ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.isFocusable();
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(),information.class);
				startActivityForResult(intent, 0);
			}
		});

	}
	
	private void addListenerOnBack() {
		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton2);
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}
	@Override
	public void onBackPressed() {
	   Log.d(TAG, "onBackPressed Called");
	   	dbHelper.close();
		db.close();
	   finish();
	}

}
