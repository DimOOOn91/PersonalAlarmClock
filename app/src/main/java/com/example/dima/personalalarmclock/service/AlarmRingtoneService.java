package com.example.dima.personalalarmclock.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dima.personalalarmclock.R;

public class AlarmRingtoneService extends Service {

    private MediaPlayer mMediaPlayer;
    private String mAlarmAction;
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // fetch the extras boolean value
        mAlarmAction = intent.getExtras().getString("alarm_action");

        // set the action for each type of putted alarm action
        assert mAlarmAction != null;
        switch (mAlarmAction) {
            case "alarm_on":
                // check if media player is not running now
                if (!isRunning) {
                    // create an instance of media player
                    mMediaPlayer = MediaPlayer.create(this, R.raw.ic_alarm_music_test);

                    // start the ringtone
                    mMediaPlayer.start();

                    // change the actual status of the alarm
                    isRunning = true;
                }
                break;

            case "alarm_off":
                // check if media player is running now
                if (isRunning) {
                    // stop media player
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();

                    // stop service
                    stopSelf();

                    // change actual status of the alarm
                    isRunning = false;
                }
                break;

            default:
                break;

        }

        return START_NOT_STICKY;
    }
}
