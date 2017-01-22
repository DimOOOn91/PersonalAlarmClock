package com.example.dima.personalalarmclock.entity;

import android.text.TextUtils;

public enum WeekDay {
    SUNDAY("SU"),
    MONDAY("MO"),
    TUESDAY("TU"),
    WEDNESDAY("WE"),
    THURSDAY("TH"),
    FRIDAY("FR"),
    SATURDAY("SA");

    private String value;

    WeekDay(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public WeekDay fromValue(String value) {
        if (!TextUtils.isEmpty(value)) {
            return null;
        }
        for (WeekDay weekDay : WeekDay.values()) {
            if (value.equals(weekDay.value)) {
                return weekDay;
            }
        }
        return null;
    }
}
