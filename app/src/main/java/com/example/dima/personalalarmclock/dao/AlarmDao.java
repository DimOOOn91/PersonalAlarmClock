package com.example.dima.personalalarmclock.dao;

import com.example.dima.personalalarmclock.entity.Alarm;

import java.util.ArrayList;

public interface AlarmDao {

    Alarm createAlarm();

    Alarm saveAlarm(Alarm alarm);

    boolean deleteAlarmById(int id);

    Alarm getById(int id);

    ArrayList<Alarm> getAll();

    void setTheFirstDayMonday(boolean firstDayMonday);

}
