package com.example.dima.personalalarmclock.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public enum WeekDay {
    SU(Calendar.SUNDAY, 7),
    MO(Calendar.MONDAY, 0),
    TU(Calendar.TUESDAY, 1),
    WE(Calendar.WEDNESDAY, 2),
    TH(Calendar.THURSDAY, 3),
    FR(Calendar.FRIDAY, 4),
    SA(Calendar.SATURDAY, 5);

    private int value;
    private int weekDayDataItemPosition;

    //change this variable if the first day should be Sunday
    public static boolean sFirstDayMonday = true;

    WeekDay(int value, int weekDayDataItemPosition) {
        this.value = value;
        this.weekDayDataItemPosition = weekDayDataItemPosition;
    }

    public int getValue() {
        return this.value;
    }

    public static WeekDay fromValue(@NonNull String name) {
        for (WeekDay weekDay : WeekDay.values()) {
            if (name.equals(weekDay.name())) {
                return weekDay;
            }
        }
        return null;
    }

    @NonNull
    public int getPosition() {
        if (sFirstDayMonday) {
            return this.weekDayDataItemPosition;
        } else {
            return getDays().indexOf(this);
        }
    }

    @NonNull
    private List<WeekDay> getDays() {
        return Arrays.asList(WeekDay.values());
    }

    public static void setFirstDayMonday(boolean firstDayMonday) {
        sFirstDayMonday = firstDayMonday;
    }

    public static ArrayList<String> sortListOfDays(ArrayList<String> repeatingDays) {
        String[] repeatingDaysArray = repeatingDays.toArray(new String[repeatingDays.size()]);
        Arrays.sort(repeatingDaysArray, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int day1 = WeekDay.fromValue(s).getPosition();
                int day2 = WeekDay.fromValue(t1).getPosition();
                return day1-day2;
            }
        });
        return (ArrayList<String>) Arrays.asList(repeatingDaysArray);
    }
}
