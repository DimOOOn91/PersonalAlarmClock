package com.example.dima.personalalarmclock.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.dima.personalalarmclock.broadcast.AlarmReceiver;
import com.example.dima.personalalarmclock.entity.Alarm;

import java.util.Calendar;

public class AlarmHelper {

    public static final String ALARM_ACTION = "alarm_action";

    public static void setAlarm(Context context, Alarm alarm) {

        Calendar calendar = alarm.getDate();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());
        calendar.set(Calendar.SECOND, 0);

        // create intent for alarm receiver, set extras to inform that it should turn on
        Intent alarmRingtoneIntent = new Intent(context, AlarmReceiver.class);
        alarmRingtoneIntent.putExtra(ALARM_ACTION, "alarm_on");
        // create pending intent which create or update already existed intent
        PendingIntent alarmRingtonePenIntent = PendingIntent.getBroadcast(context,
                alarm.getId(), alarmRingtoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set alarm manager and fill it based on the fact is it repeated
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm.getRepeatingDays().size() > 0) {
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), );
            // TODO set alarm manager for intervals
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmRingtonePenIntent);
        }
    }

    public static void cancelAlarm(Context context, Alarm alarm) {
        // create intent for alarm receiver, set extras to inform that it should turn off
        Intent alarmRingtoneIntent = new Intent(context, AlarmReceiver.class);
        alarmRingtoneIntent.putExtra(ALARM_ACTION, "alarm_off");
        // create pending intent which create or update already existed intent
        PendingIntent alarmRingtonePenIntent = PendingIntent.getBroadcast(context,
                alarm.getId(), alarmRingtoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // ger alarm manager and delete the necessary pending intent
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmRingtonePenIntent);

        // stop the ringtone
        stopRingtone(context, alarmRingtoneIntent);

    }

    private static void stopRingtone(Context context, Intent intent) {
        context.sendBroadcast(intent);
    }
}
