/*
siddhartha dimania
*/
package com.alarmpro_x;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Calendar cal = Calendar.getInstance();
	int minute = cal.get(Calendar.MINUTE);
	int hour = cal.get(Calendar.HOUR);
	TextView text2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values= new ContentValues();
		
		values.put(DbHelper._ID2, 23);
		values.put(DbHelper.TOGGLE_ON_OFF,7);
		
		db.close();
		dbHelper.close();
		
		
		setContentView(R.layout.activity_main);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

		text2 = (TextView) findViewById(R.id.textView2);
		TextView text3 = (TextView) findViewById(R.id.textView3);
		TextView text4 = (TextView) findViewById(R.id.textView4);

		

		int year = cal.get(Calendar.YEAR);
		int am_pm = cal.get(Calendar.AM_PM);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		int day = cal.getFirstDayOfWeek();
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);

		String monthofyear = null;
		String weekday = null;
		String Am_Pm = null;

		switch (month) {
		case 0:
			monthofyear = "January";
			break;
		case 1:
			monthofyear = "Faburary";
			break;
		case 2:
			monthofyear = "March";
			break;
		case 3:
			monthofyear = "April";
			break;
		case 4:
			monthofyear = "May";
			break;
		case 5:
			monthofyear = "June";
			break;
		case 6:
			monthofyear = "July";
			break;
		case 7:
			monthofyear = "August";
			break;
		case 8:
			monthofyear = "September";
			break;
		case 9:
			monthofyear = "October";
			break;
		case 10:
			monthofyear = "November";
			break;
		case 11:
			monthofyear = "December";
			break;

		default:
			break;
		}
		switch (dayofweek) {
		case 1:
			weekday = "Sunday";
			break;
		case 2:
			weekday = "Monday";
			break;
		case 3:
			weekday = "Tuesday";
			break;
		case 4:
			weekday = "Wednesday";
			break;
		case 5:
			weekday = "Thursday";
			break;
		case 6:
			weekday = "Friday";
			break;
		case 7:
			weekday = "Saturday";
			break;
		default:
			break;
		}

		switch (am_pm) {
		case 0:
			Am_Pm = "AM";
			break;
		case 1:
			Am_Pm = "PM";
			break;
		}

		if (hour == 0)
			hour = 12;

		if(minute<10)
			text2.setText(hour + ":0" + minute);
		else
			text2.setText(hour + ":" + minute);
		
		
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		text2.setTypeface(tf2);
		text3.setText(Am_Pm);
		text3.setTypeface(tf);
		text4.setText(weekday + ",  " + date + " " + monthofyear + "  " + year);
		text4.setTypeface(tf);

		// text2.setText(new SimpleDateFormat("h:mm a dd,MMMM yy").format(new
		// Date()));
		// text2.setText(DateFormat.getDateTimeInstance().format(new
		// Date(1000)));

		// text2.setText(new Date().toString());

		addListenerOnAddAlarm();
		andListenerOnPlus();
		addListenerOnSettings();
		addListenerOnBack();
		addListenerOnList();

	}
	

	private void addListenerOnList() {
		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton4);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), alarm_list.class);
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

	private void addListenerOnSettings() {
		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton5);
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), settings.class);
				startActivityForResult(intent, 0);
				// finish();

			}
		});

	}

	private void addListenerOnAddAlarm() {
		Button addButton = (Button) findViewById(R.id.button1);
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), add_alarm.class);
				startActivityForResult(intent, 0);
				// finish();

			}
		});

	}

	private void andListenerOnPlus() {

		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton1);
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), add_alarm.class);
				startActivityForResult(intent, 0);

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
