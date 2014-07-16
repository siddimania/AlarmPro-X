/*
siddhartha dimania
*/
package com.alarmpro_x;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class information extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);

		TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);

		TextView text2 = (TextView) findViewById(R.id.textView2);
		text2.setTypeface(tf);

		addListenerOnArrowBack();
		addListenerOnGooglePlay();

	}

	private void addListenerOnGooglePlay() {
		ImageButton imagebutton = (ImageButton) findViewById(R.id.imageButton3);
		imagebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
				String page = "https://play.google.com/store/apps/details?id=com.alarmpro_x&hl=en";
				myWebLink.setData(Uri.parse(page));
				startActivity(myWebLink);

			}
		});

	}

	private void addListenerOnArrowBack() {
		ImageButton addButton = (ImageButton) findViewById(R.id.imageButton1);
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					finish();
			}
		});

	}
}
