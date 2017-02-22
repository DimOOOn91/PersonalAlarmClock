package com.example.dima.personalalarmclock.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.dima.personalalarmclock.R;
import com.example.dima.personalalarmclock.util.AlarmHelper;
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
//        Intent intent1 = new Intent()
        context.startService(alarmRingtoneIntent);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSound(Uri.parse("android:resource://raw/ic_alarm_music_test"))
                .setContentText("My alarm test")
                .setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[] {500,500,500,500,500,500,500,500});
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(11, builder.build());

    }

    private String getIntentStringExtra(Intent intent, String key) {
        return intent.getStringExtra(key);
    }
}
