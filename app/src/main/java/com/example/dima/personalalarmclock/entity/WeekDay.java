package com.example.dima.personalalarmclock.entity;

import android.text.TextUtils;

import java.util.Calendar;

public enum WeekDay {
    SU(Calendar.SUNDAY),
    MO(Calendar.MONDAY),
    TU(Calendar.TUESDAY),
    WE(Calendar.WEDNESDAY),
    TH(Calendar.THURSDAY),
    FR(Calendar.FRIDAY),
    SA(Calendar.SATURDAY);

    private int value;

    WeekDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WeekDay fromValue(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        for (WeekDay weekDay : WeekDay.values()) {
            if (name.equals(weekDay.name())) {
                return weekDay;
            }
        }
        return null;
    }

}
