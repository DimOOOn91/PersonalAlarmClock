package com.example.dima.personalalarmclock.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.dima.personalalarmclock.broadcast.AlarmReceiver;
import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.entity.WeekDay;

import java.util.Calendar;

public class AlarmHelper {

    public static final String ALARM_ACTION = "alarm_action";

    public static void setAlarm(Context context, Alarm alarm) {

        // TODO refactoring setting alarm

        // create intent for alarm receiver, set extras to inform that it should turn on
        Intent alarmRingtoneIntent = new Intent(context, AlarmReceiver.class);
        alarmRingtoneIntent.putExtra(ALARM_ACTION, "alarm_on");
        // create pending intent which create or update already existed intent
        PendingIntent alarmRingtonePenIntent = PendingIntent.getBroadcast(context,
                alarm.getId(), alarmRingtoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set alarm manager and fill it based on the fact is it repeated
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar currentDay = Calendar.getInstance();

        Calendar dayOfAlarm = alarm.getDate();
        dayOfAlarm.set(Calendar.HOUR_OF_DAY, alarm.getHours());
        dayOfAlarm.set(Calendar.MINUTE, alarm.getMinutes());
        dayOfAlarm.set(Calendar.SECOND, 0);

        if (alarm.getRepeatingDays().size() == 0 && dayOfAlarm.before(currentDay)) {
            // finish this method without setting the alarm if alarm is not repeating
            // and should be performed in the past
            return;
        } else if (alarm.getRepeatingDays().size() == 0 && dayOfAlarm.after(currentDay)) {
            // set alarm and finish this method if alarm is not repeating
            // and will be performed in future
            alarmManager.set(AlarmManager.RTC_WAKEUP, dayOfAlarm.getTimeInMillis(), alarmRingtonePenIntent);
            return;
        }

        dayOfAlarm.set(Calendar.YEAR, currentDay.get(Calendar.YEAR));
        dayOfAlarm.set(Calendar.MONTH, currentDay.get(Calendar.MONTH));
        dayOfAlarm.set(Calendar.DAY_OF_MONTH, currentDay.get(Calendar.DAY_OF_MONTH));

        // check if alarm should be performed on this week later
        for (String day : alarm.getRepeatingDays()) {
            int dayPoint = WeekDay.valueOf(day).getValue();
            int differenceInDays = dayPoint - currentDay.get(Calendar.DAY_OF_WEEK);

            if (differenceInDays == 0 && dayOfAlarm.after(currentDay)) {
                // set alarm and finish the method if alarm should be later today
                alarmManager.set(AlarmManager.RTC_WAKEUP, dayOfAlarm.getTimeInMillis(), alarmRingtonePenIntent);
                return;
            } else if (differenceInDays > 0) {
                // if alarm is on this week later set alarm and finish this method
                dayOfAlarm.add(Calendar.DAY_OF_YEAR, differenceInDays);
                alarmManager.set(AlarmManager.RTC_WAKEUP, dayOfAlarm.getTimeInMillis(), alarmRingtonePenIntent);
                return;
            }
        }

        // if method comes to this line the alarm in not on this week
        // set alarm to the nearest day on the next week
        int dayPoint = WeekDay.valueOf(alarm.getRepeatingDays().get(0)).getValue();
        int differenceInDays = dayPoint - currentDay.get(Calendar.DAY_OF_WEEK) + 7;
        dayOfAlarm.add(Calendar.DAY_OF_YEAR, differenceInDays);
        alarmManager.set(AlarmManager.RTC_WAKEUP, dayOfAlarm.getTimeInMillis(), alarmRingtonePenIntent);
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
        context.sendBroadcast(alarmRingtoneIntent);

    }

}
