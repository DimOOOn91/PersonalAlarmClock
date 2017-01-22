package com.example.dima.personalalarmclock.dao;


import com.example.dima.personalalarmclock.entity.Alarm;

import java.util.ArrayList;

public class AlarmDaoImpl implements AlarmDao {

    private static AlarmDaoImpl instance;

    private static ArrayList<Alarm> mAlarmList;

    private AlarmDaoImpl() {
        mAlarmList = new ArrayList<>();
    }

    public static AlarmDaoImpl getInstance() {
        if (instance == null) {
            instance = new AlarmDaoImpl();
        }
        return instance;
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
            return mAlarmList.remove(alarmInDb);
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
