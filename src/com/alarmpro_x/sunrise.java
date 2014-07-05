package com.alarmpro_x;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class sunrise extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sunrise);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

		TextView text2 = (TextView) findViewById(R.id.textView2);
		text2.setTypeface(tf);

		TextView text3 = (TextView) findViewById(R.id.textView3);
		text3.setTypeface(tf);

		TextView text4 = (TextView) findViewById(R.id.editText1);
		text4.setTypeface(tf);

		addListenerOnCancelButton();

	}

	private void addListenerOnCancelButton() {
		Button addButton = (Button) findViewById(R.id.button1);
		final EditText editText = (EditText) findViewById(R.id.editText1);

		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String city= null;
				city = editText.getText().toString();
				
				if (city.length()!=0) {
					Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
					String page = "https://www.google.co.in/search?q=sunrise+"
							+ city
							+ "&oq=sunrise+"
							+ city
							+ "&aqs=chrome.0.59j57j5j62l2.3024j0&sourceid=chrome&ie=UTF-8&qscrl=1";

					
					myWebLink.setData(Uri.parse(page));
					startActivity(myWebLink);
					Toast.makeText(sunrise.this, "City:" + city,
							Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(sunrise.this, "Enter Your City",
							Toast.LENGTH_SHORT).show();
				}
					
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
