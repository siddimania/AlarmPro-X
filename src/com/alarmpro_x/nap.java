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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class nap extends Activity implements OnClickListener {

	AlarmManager am;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nap);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

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

		TextView text7 = (TextView) findViewById(R.id.textView7);
		text7.setTypeface(tf);

		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		CheckBox ch1 = (CheckBox) findViewById(R.id.checkBox1);
		ch1.setOnClickListener(this);

		CheckBox ch2 = (CheckBox) findViewById(R.id.checkBox2);
		ch2.setOnClickListener(this);

		CheckBox ch3 = (CheckBox) findViewById(R.id.checkBox3);
		ch3.setOnClickListener(this);

		CheckBox ch4 = (CheckBox) findViewById(R.id.checkBox4);
		ch4.setOnClickListener(this);

		CheckBox ch5 = (CheckBox) findViewById(R.id.checkBox5);
		ch5.setOnClickListener(this);

	}

	public void onClick(View view) {

		boolean checked = ((CheckBox) view).isChecked();
		int minutes = 0;
		long seconds = 60 * 1000;
		switch (view.getId()) {
		case R.id.checkBox1:
			if (checked) {
				seconds = seconds * 15;
				minutes = 15;
			}
			break;
		case R.id.checkBox2:
			if (checked) {
				seconds = seconds * 25;
				minutes = 25;
			}
			break;
		case R.id.checkBox3:
			if (checked) {
				seconds = seconds * 30;
				minutes = 30;

			}
			break;
		case R.id.checkBox4:
			if (checked) {
				seconds = seconds * 45;
				minutes = 45;
			}
			break;
		case R.id.checkBox5:
			if (checked) {
				seconds = seconds * 55;
				minutes = 55;
			}
			break;

		}

		Intent intent = new Intent(this, AlarmStart.class);
		int intent_id = (int) System.currentTimeMillis();
		intent.putExtra("alarm_nap", seconds);

		PendingIntent alarmPendingIntent = PendingIntent.getActivity(
				getApplicationContext(), intent_id, intent,
				PendingIntent.FLAG_ONE_SHOT);
		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime() + seconds, alarmPendingIntent);
		Toast.makeText(nap.this,
				"Your Alarm Will Ring After " + minutes + "Minutes",
				Toast.LENGTH_LONG).show();

		Calendar cal = Calendar.getInstance();

		int nowMinutes = cal.get(Calendar.MINUTE);
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);

		String Am_Pm = "AM";

		if (nowHour >= 12) {
			Am_Pm = "PM";
		}
		
		if (nowHour > 12) {
			nowHour = nowHour - 12;
		}

		nowMinutes = nowMinutes + minutes;

		if (nowMinutes >= 60) {
			nowHour = nowHour + 1;
			nowMinutes = nowMinutes - 60;
		}

		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(DbHelper.T_ID_MILLISECOND, intent_id);
		values.put(DbHelper.T_HOUR, (int) nowHour);
		values.put(DbHelper.T_MINUTES, (int) nowMinutes);

		

		values.put(DbHelper.T_Am_Pm, Am_Pm);

		db.insertWithOnConflict(DbHelper.TABLE, null, values,
				SQLiteDatabase.CONFLICT_REPLACE);

		db.close();
		dbHelper.close();

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				getApplicationContext())
				.setContentTitle("Alarm is Set")
				.setSmallIcon(R.drawable.notification_icon)
				.setContentText(
						"at " + nowHour+ ":" + nowMinutes + "," + Am_Pm);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(nap.this, alarm_list.class);
		//mBuilder.setAutoCancel(true);
		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder
				.create(getApplicationContext());
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(alarm_list.class);
		// Adds the Intent that starts the Activity to the top of the stack

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// mId allows you to update the notification later on.

		int mId = 9;
		mNotificationManager.notify(mId, mBuilder.build());

		finish();

	}

}