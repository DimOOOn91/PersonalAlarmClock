package com.example.dima.personalalarmclock.dao;


import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.util.AlarmCounter;

import java.util.ArrayList;

public class AlarmDaoImpl implements AlarmDao {

    private static AlarmDaoImpl instance;
    private static ArrayList<Alarm> mAlarmList;
    private AlarmCounter alarmCounter;

    private AlarmDaoImpl() {
        mAlarmList = new ArrayList<>();
        alarmCounter = AlarmCounter.getInstance();
    }

    public static AlarmDaoImpl getInstance() {
        if (instance == null) {
            instance = new AlarmDaoImpl();
        }
        return instance;
    }

    @Override
    public Alarm createAlarm() {
        Alarm alarm = new Alarm(alarmCounter.getId());
        return saveAlarm(alarm);
    }

    @Override
    public Alarm saveAlarm(Alarm alarm) {
        if (alarm == null) {
            return null;
        }
        Alarm alarmInDb = getById(alarm.getId());
        if (alarmInDb == null) {
            mAlarmList.add(alarm);
        } else {
            int index = mAlarmList.indexOf(alarmInDb);
            mAlarmList.set(index, alarm);
        }
        return alarm;
    }

    @Override
    public boolean deleteAlarmById(int id) {
        Alarm alarmInDb = getById(id);
        if (alarmInDb == null) {
            return false;
        } else {
            mAlarmList.remove(alarmInDb);
            alarmCounter.deleteId(id);
            return true;
        }
    }

    @Override
    public Alarm getById(int id) {
        for (Alarm alarm : mAlarmList) {
            if (id == alarm.getId()) {
                return alarm;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Alarm> getAll() {
        return mAlarmList;
    }
}
