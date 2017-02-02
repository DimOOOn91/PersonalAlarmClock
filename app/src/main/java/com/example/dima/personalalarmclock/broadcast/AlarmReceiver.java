package com.example.dima.personalalarmclock.broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.dima.personalalarmclock.helper.AlarmHelper;
import com.example.dima.personalalarmclock.service.AlarmRingtoneService;


public class AlarmReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        // create intent to alarm ringtone service
        Intent alarmRingtoneIntent = new Intent(context, AlarmRingtoneService.class);

        // pass the extra string from Main Activity to the Ringtone Playing Service
        alarmRingtoneIntent.putExtra(AlarmHelper.ALARM_ACTION,
                getIntentStringExtra(intent, AlarmHelper.ALARM_ACTION));

        // start alarm ringtone service
        context.startService(alarmRingtoneIntent);
    }

    private String getIntentStringExtra(Intent intent, String key) {
        return intent.getStringExtra(key);
    }
}
