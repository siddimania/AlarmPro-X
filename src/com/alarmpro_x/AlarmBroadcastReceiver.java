/*
siddhartha dimania
*/

package com.alarmpro_x;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

	private Bundle savedInstanceState;

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Running the new Activity", Toast.LENGTH_LONG)
				.show();
		new AlarmStart().onCreate(savedInstanceState);
	}
}
