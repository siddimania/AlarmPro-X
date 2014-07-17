/*
siddhartha dimania
*/
package com.alarmpro_x;

import java.util.TooManyListenersException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class settings extends Activity {
 
	private static final String TAG = DbHelper.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

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
		
		

		addListenerOnArrowBack();
		addListenerOnLinearLayoutMyAppSid();
		addListenerOnToggleButton();
		addListenerOnLinearLayoutAlamSound();
		addListenerOnLinearLayoutSnooze();
		addListenerOnImageButtonList();
		addListenerOnDeleteAllAlarms();
		checkingCursor();
		
		
	}

	void checkingCursor(){
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db2= dbHelper.getReadableDatabase();
		int onoff=0;
		Log.d(TAG, "IN THE CHECKING CURSOR");
		Cursor c = db2.query(DbHelper.TABLE2,new String []{DbHelper.TOGGLE_ON_OFF},null, null, null, null,null);
		
		if (c.moveToFirst()) {
		    do {
		    	onoff = c.getInt(c.getColumnIndex(DbHelper.TOGGLE_ON_OFF));
				System.out.println("values: "+ onoff);
				Log.d(TAG, "IN THE WHILE LOOP");
		    } while (c.moveToNext());
		  }     
		
		if(onoff==1)
			toOnTheStateOfToggleButton();
			
		
		db2.close();
		dbHelper.close();
	}
	
	private void toOnTheStateOfToggleButton() {
		ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
		toggle.setChecked(true);
		
	}

	private void addListenerOnDeleteAllAlarms() {

		LinearLayout removeBtn = (LinearLayout) findViewById(R.id.linearLayout5);

		removeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				new AlertDialog.Builder(settings.this)
						.setTitle("Remove Alarms")
						.setIcon(R.drawable.weather_icon)
						.setMessage("You Want To Remove All Alarms : ")

						.setNegativeButton("Remove",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										DbHelper dbHelper = new DbHelper(getApplicationContext());
										SQLiteDatabase db = dbHelper.getWritableDatabase();
										db.delete(dbHelper.TABLE, null, null);

										Toast.makeText(settings.this,
												" All Alarms Removed ",
												Toast.LENGTH_SHORT).show();
										db.close();
										dbHelper.close();

									}
								})
						.setPositiveButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(settings.this, " Cancelled ",
												Toast.LENGTH_SHORT).show();

									}
								}).create().show();

			}
		});

	}

	private void addListenerOnImageButtonList() {

		ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.isFocusable();
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),information.class);
				startActivityForResult(intent, 0);
				
//				new AlertDialog.Builder(settings.this)
//						.setTitle("Information")
//						.setIcon(R.drawable.weather_icon)
//						.setMessage("Inforamtion About The Page")
//
//						.setNegativeButton("Ok",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										Toast.makeText(settings.this, " Ok ",
//												Toast.LENGTH_SHORT).show();
//
//									}
//								})
//						.setPositiveButton("Cancle",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										Toast.makeText(settings.this, " Cancled ",
//												Toast.LENGTH_SHORT).show();
//
//									}
//								}).create().show();

			}
		});

	}

	private void addListenerOnLinearLayoutSnooze() {
		LinearLayout addButton = (LinearLayout) findViewById(R.id.linearLayout2);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(),
						snooze_duration.class);
				startActivityForResult(intent, 0);

			}
		});

	}

	private void addListenerOnLinearLayoutAlamSound() {
		// TODO Auto-generated method stub

		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout3);

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				try {
					Toast.makeText(settings.this, "Default Sound Is Playing",
							Toast.LENGTH_SHORT).show();
					Uri notification = RingtoneManager
							.getDefaultUri(RingtoneManager.TYPE_ALARM);
					final Ringtone r1 = RingtoneManager.getRingtone(
							getApplicationContext(), notification);

					r1.play();
					// SystemClock.sleep(5000);
					Runnable r = new Runnable() {
						@Override
						public void run() {
							r1.stop();
						}
					};

					Handler h = new Handler();
					h.postDelayed(r, 3000);

				} catch (Exception e) {
				}
			}
		});
	}

	private void addListenerOnToggleButton() {
		// TODO Auto-generated method stub
		
		ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
		final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	
	
		toggle.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int onoffvalue=0;
				DbHelper dbHelper = new DbHelper(getApplicationContext());
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				boolean on = ((ToggleButton) view).isChecked();
				ContentValues values = new ContentValues();
				
				
				db.delete(dbHelper.TABLE2, null, null);
				
				if (on) {
					Toast.makeText(settings.this, " Vibration On ",Toast.LENGTH_SHORT).show();
					onoffvalue=1;
					v.vibrate(200);
					
					
				} else {
						Toast.makeText(settings.this, " Vibration Off ",Toast.LENGTH_SHORT).show();
						onoffvalue=2;
						v.cancel();
					}
				
				values.put(DbHelper.TOGGLE_ON_OFF, onoffvalue);
				
				db.insertWithOnConflict(DbHelper.TABLE2, null, values,SQLiteDatabase.CONFLICT_REPLACE);
				
				db.close();
				dbHelper.close();
				
			}
		});

	}

	private void addListenerOnLinearLayoutMyAppSid() {

		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout6);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(settings.this, " Opening Browser ",
						Toast.LENGTH_SHORT).show();
				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
				myWebLink.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Siddhartha+Dimania"));
				startActivity(myWebLink);
			}
		});

	}

	private void addListenerOnArrowBack() {
		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton2);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
