package com.alarmpro_x;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
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

public class snooze_duration extends Activity implements OnClickListener {

	private static final String TAG = DbHelper.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.snooze_duration);
		int tick =0;
		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);
		
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor c = db.query(DbHelper.TABLE3,new String []{DbHelper.SNOOZE_TIME}, null, null,null, null, null);
		
		
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
		
		//ch1.setChecked(true);
		
		while(c.moveToNext()){
			tick = c.getInt(c.getColumnIndex(DbHelper.SNOOZE_TIME));
		}
		
		Log.d(TAG, "GETTING THE VALUE");
		System.out.println(tick);
		if(tick>0){
			switch(tick){
			case 1:ch1.setChecked(true);
				break ;
			case 2:ch2.setChecked(true);
				break ;
			case 3: ch3.setChecked(true);
				break ;
			case 4:ch4.setChecked(true);
				break;
				
			
			}
		}

		db.close();
		dbHelper.close();
		
		ch1.setOnClickListener(this);
		ch2.setOnClickListener(this);
		ch3.setOnClickListener(this);
		ch4.setOnClickListener(this);
	}
	
	public void onClick(View view) {

		boolean checked = ((CheckBox) view).isChecked();
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int tick=0;
		db.delete(DbHelper.TABLE3, null, null);
		ContentValues values = new ContentValues();
		switch (view.getId()) {
			case R.id.checkBox1:
				if (checked) {
					tick = 1;
				}
				break;
			case R.id.checkBox2:
				if (checked) {
					tick = 2;
				}
				break;
			case R.id.checkBox3:
				if (checked) {
					tick = 3;
				}
				break;
			case R.id.checkBox4:
				if (checked) {
					tick = 4;
				}
				break;
		}
		
		values.put(DbHelper.SNOOZE_TIME, tick );
		values.put(DbHelper._ID3, 23);
		db.insertWithOnConflict(DbHelper.TABLE3,null,values,SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
		dbHelper.close();
		finish();

	}

}
