package com.example.dima.personalalarmclock.broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.dima.personalalarmclock.service.AlarmRingtoneService;


public class AlarmReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        // create intent to alarm ringtone service
        Intent alarmRingtoneIntent = new Intent(context, AlarmRingtoneService.class);

        // start alarm ringtone service
        context.startService(alarmRingtoneIntent);
    }
}
