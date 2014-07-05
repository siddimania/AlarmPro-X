package com.alarmpro_x;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class add_alarm extends Activity {

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_alarm);

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
		addListenerOnLinearLayoutTime();
		addListenerOnLinearLayoutSunrise();
		addListenerOnLinearLayoutNap();
		addListenerOnLinearLayoutSeasons();
		addListenerOnImageButtonList();

	}

	private void addListenerOnImageButtonList() {

		ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.isFocusable();
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(),information.class);
				startActivityForResult(intent, 0);
				
//				new AlertDialog.Builder(add_alarm.this)
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
//										Toast.makeText(add_alarm.this, "Ok",
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
//										Toast.makeText(add_alarm.this,
//												"Cancle", Toast.LENGTH_SHORT)
//												.show();
//
//									}
//								}).create().show();

			}
		});

	}

	private void addListenerOnLinearLayoutSeasons() {
		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout4);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), seasons.class);
				startActivityForResult(intent, 0);

			}
		});

	}

	private void addListenerOnLinearLayoutNap() {
		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout6);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), nap.class);
				startActivityForResult(intent, 0);

			}
		});

	}

	private void addListenerOnLinearLayoutSunrise() {
		// TODO Auto-generated method stub

		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout5);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), sunrise.class);
				startActivityForResult(intent, 0);

			}
		});

	}

	private void addListenerOnLinearLayoutTime() {
		// TODO Auto-generated method stub

		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout3);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), Time.class);
				startActivityForResult(intent, 0);

			}
		});

	}

	private void addListenerOnLinearLayoutMyAppSid() {
		// TODO Auto-generated method stub

		LinearLayout btn = (LinearLayout) findViewById(R.id.linearLayout7);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myWebLink = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink.setData(Uri
						.parse("https://play.google.com/store/apps/developer?id=Siddhartha+Dimania"));
				startActivity(myWebLink);
				Toast.makeText(add_alarm.this, " Opening Browser ",
						Toast.LENGTH_SHORT).show();
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

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
