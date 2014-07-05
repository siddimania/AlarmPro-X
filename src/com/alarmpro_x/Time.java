package com.alarmpro_x;

import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Time extends Activity {

	private static final String TAG = "_tag";
	AlarmManager am;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

		TextView text2 = (TextView) findViewById(R.id.textView2);

		text2.setTypeface(tf);

		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		addListenerOnCancelButton();
		addListenerOnSetAlarmButton();

	}

	@SuppressLint("NewApi")
	private void addListenerOnSetAlarmButton() {
		Button addButton = (Button) findViewById(R.id.button1);
		final TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Calendar cal = Calendar.getInstance();
				long alarmHour = (long) tp.getCurrentHour();
				long alarmMinutes = (long) tp.getCurrentMinute();

				int nowMinutes = cal.get(Calendar.MINUTE);
				int nowHour = cal.get(Calendar.HOUR_OF_DAY);
				int nowSeconds = cal.get(Calendar.SECOND);

				int toastHour = (int) alarmHour;
				String Am_Pm = "AM";

				if (alarmHour > 12) {
					toastHour = (int) alarmHour - 12;
					Am_Pm = "PM";
				} else if (alarmHour == 12) {
					Am_Pm = "PM";
				}

				long seconds;

				if (alarmHour >= nowHour && alarmHour < 24) {
					seconds = (((alarmHour * 60 + alarmMinutes) * 60) - ((nowHour * 60 + nowMinutes) * 60 + nowSeconds)) * 1000;
				} else {
					seconds = (((alarmHour * 60 + alarmMinutes) * 60) + (24 * 60 * 60 - ((nowHour * 60 + nowMinutes) * 60))) * 1000;
				}

				// seconds=alarmMinutes*60*1000;
				System.out.println(seconds);

				Intent intent = new Intent(Time.this, AlarmStart.class);
				intent.putExtra("alarm_time", seconds);

				int intent_id = (int) System.currentTimeMillis();

				PendingIntent alarmPendingIntent = PendingIntent.getActivity(
						getApplicationContext(), intent_id, intent,
						PendingIntent.FLAG_ONE_SHOT);

				DbHelper dbHelper = new DbHelper(getApplicationContext());
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
						SystemClock.elapsedRealtime() + seconds,
						alarmPendingIntent);

				ContentValues values = new ContentValues();
				
				values.put(DbHelper.T_ID_MILLISECOND, intent_id);
				values.put(DbHelper.T_HOUR, (int) toastHour);
				values.put(DbHelper.T_MINUTES, (int) alarmMinutes);
				values.put(DbHelper.T_Am_Pm, Am_Pm);

				db.insertWithOnConflict(DbHelper.TABLE, null, values,SQLiteDatabase.CONFLICT_REPLACE);

				Toast.makeText(
						Time.this,
						"Your Alarm is Set at " + toastHour + ":"
								+ alarmMinutes + Am_Pm, Toast.LENGTH_LONG)
						.show();

				
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
				        .setContentTitle("Alarm is Set")
				        .setSmallIcon(R.drawable.notification_icon)
				        .setContentText("at "+toastHour+ ":"+ alarmMinutes+ ","+ Am_Pm);
				
				// Creates an explicit intent for an Activity in your app
				Intent resultIntent = new Intent(Time.this, alarm_list.class);
				
				//mBuilder.setAutoCancel(true);

				// The stack builder object will contain an artificial back stack for the
				// started Activity.
				// This ensures that navigating backward from the Activity leads out of
				// your application to the Home screen.
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
				// Adds the back stack for the Intent (but not the Intent itself)
				stackBuilder.addParentStack(alarm_list.class);
				// Adds the Intent that starts the Activity to the top of the stack
				
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
				
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				
				
				// mId allows you to update the notification later on.
				
				
				int mId=10;
				mNotificationManager.notify(mId, mBuilder.build());
			
				db.close();
				dbHelper.close();

				finish();
			}
		});

	}

	private void addListenerOnCancelButton() {
		Button addButton = (Button) findViewById(R.id.button2);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(Time.this, "Alarm Cancelled", Toast.LENGTH_SHORT)
						.show();
				finish();

			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
