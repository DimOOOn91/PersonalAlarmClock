package com.example.dima.personalalarmclock.controller;

import com.example.dima.personalalarmclock.dao.AlarmDaoImpl;
import com.example.dima.personalalarmclock.entity.Alarm;

import java.util.ArrayList;

public class AlarmController {

    private AlarmDaoImpl mAlarmDao;

    public AlarmController() {
        mAlarmDao = AlarmDaoImpl.getInstance();
    }

    public ArrayList<Alarm> getAllAlarms() {
        return mAlarmDao.getAll();
    }

    public Alarm createAlarm() {
        return mAlarmDao.createAlarm();
    }

    public Alarm saveAlarm(Alarm alarm) {
        return mAlarmDao.saveAlarm(alarm);
    }

    public boolean removeAlarm(Alarm alarm) {
        return mAlarmDao.deleteAlarmById(alarm.getId());
    }

    public Alarm getAlarmById(int id) {
        return mAlarmDao.getById(id);
    }

    public void setTheFirstDayOfWeekMonday(boolean firstDayMonday) {
        mAlarmDao.setTheFirstDayMonday(firstDayMonday);
    }

};
