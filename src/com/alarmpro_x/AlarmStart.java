package com.alarmpro_x;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AlarmStart extends Activity {
	Ringtone r1;
	int onoff = 0;
	int ringerMode = 3;
	AlarmManager am;
	Vibrator vibrator;
	PowerManager.WakeLock wl;
	AudioManager audioManager;

	MediaPlayer mPlayer;
	
	protected void onCreate(Bundle savedInstanceState) {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		audioManager = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP, "My Tag");
		wl.acquire();

		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor c = db.query(DbHelper.TABLE2,
				new String[] { DbHelper.TOGGLE_ON_OFF }, null, null, null,
				null, null);
		while (c.moveToNext())
			onoff = c.getInt(c.getColumnIndex(DbHelper.TOGGLE_ON_OFF));
		db.close();
		dbHelper.close();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_ring);

		try {

			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_ALARM);
			r1 = RingtoneManager.getRingtone(getApplicationContext(),
					notification);

			mPlayer= new MediaPlayer();
			mPlayer.setDataSource(this,notification);
			
			System.out.println("this is ringer mode "
					+ audioManager.getRingerMode());
			
			int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, 0);

			if (audioManager.getRingerMode() == 1) {
				ringerMode = 1;
			}
			
			mPlayer.setLooping(true);
			mPlayer.prepare();
			mPlayer.start();
			
			if (onoff == 1)
				vibrator.vibrate(10 * 60 * 1000);

			
				//r1.play();

		} catch (Exception e) {
			System.out.println("YOU GET AN ERROR");

		}

		onClickListenerDismiss();
		onClickListenerSnooze();

	}

	private void onClickListenerSnooze() {
		ImageView img = (ImageView) findViewById(R.id.imageView3);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//r1.stop();
				mPlayer.stop();
				wl.release();
				vibrator.cancel();
				snoozeOn();
				if (ringerMode == 1) {
					audioManager
							.setRingerMode(audioManager.RINGER_MODE_VIBRATE);
				}
				System.out.println("This is after ringermode "
						+ audioManager.getRingerMode());
				finish();

			}
		});

	}

	private void onClickListenerDismiss() {

		ImageView img = (ImageView) findViewById(R.id.imageView2);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.cancel(10);
				
				mNotificationManager.cancel(9);

				//r1.stop();
				mPlayer.stop();
				vibrator.cancel();
				if (ringerMode == 1) {
					audioManager
							.setRingerMode(audioManager.RINGER_MODE_VIBRATE);
				}
				System.out.println("This is after ringermode "
						+ audioManager.getRingerMode());
				finish();
				wl.release();

			}
		});
	}

	@Override
	public void onBackPressed() {
		Log.d("CDA", "onBackPressed Called");
		
	}
	
	@Override
	public void onAttachedToWindow() {
		
	}

	private void snoozeOn() {

		int snoozetime = 0;
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor c = db.query(DbHelper.TABLE3,
				new String[] { DbHelper.SNOOZE_TIME }, null, null, null, null,
				null);
		while (c.moveToNext())
			snoozetime = c.getInt(c.getColumnIndex(DbHelper.SNOOZE_TIME));
		db.close();
		dbHelper.close();

		if (snoozetime > 0) {
			Intent intent = new Intent(AlarmStart.this, AlarmStart.class);
			int intentid = (int) System.currentTimeMillis();
			PendingIntent alarmPendingIntent = PendingIntent.getActivity(
					getApplicationContext(), intentid, intent,
					PendingIntent.FLAG_ONE_SHOT);

			am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
					SystemClock.elapsedRealtime() + snoozetime * 5 * 60 * 1000,
					alarmPendingIntent);
			System.out.println("THIS IS SNOOZE ON METHOD");
		}

	}

}
