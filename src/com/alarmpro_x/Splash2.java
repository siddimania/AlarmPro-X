package com.alarmpro_x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Splash2 extends Activity {
	
	MainActivity mainActivity= new MainActivity();
	
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash2);
	    
	    TextView text = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoSlab-Light.ttf");
		text.setTypeface(tf);
		
		TextView text2 =(TextView) findViewById(R.id.textView2);
		text2.setTypeface(tf);
	    
	    //display the logo during 5 secondes,
	    new CountDownTimer(1000,1000){
	    	
	        @Override
	        public void onTick(long millisUntilFinished){} 

	        @Override
	        public void onFinish(){
	        	
	        	Intent i = new Intent(Splash2.this,MainActivity.class);
	        	startActivityForResult(i, 0);
	        	finish();
	        }
	   }.start();
	}
}
