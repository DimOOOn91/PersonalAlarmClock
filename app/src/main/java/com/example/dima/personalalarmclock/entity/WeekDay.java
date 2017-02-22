package com.example.dima.personalalarmclock.entity;

import android.support.annotation.NonNull;

import com.example.dima.personalalarmclock.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public enum WeekDay {
    SU(R.string.sunday, Calendar.SUNDAY, 7),
    MO(R.string.monday, Calendar.MONDAY, 0),
    TU(R.string.tuesday, Calendar.TUESDAY, 1),
    WE(R.string.wednesday, Calendar.WEDNESDAY, 2),
    TH(R.string.thursday, Calendar.THURSDAY, 3),
    FR(R.string.friday, Calendar.FRIDAY, 4),
    SA(R.string.saturday, Calendar.SATURDAY, 5);
//    SA(Resources.getSystem().getString(R.string.saturday), Calendar.SATURDAY, 5);

    private int value;
    private int weekDayDataItemPosition;
    private int name;

    //change this variable if the first day should be Sunday
    public static boolean sFirstDayMonday = true;

    WeekDay(int name, int value, int weekDayDataItemPosition) {
        this.value = value;
        this.weekDayDataItemPosition = weekDayDataItemPosition;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public static WeekDay fromValue(@NonNull String name) {
        for (WeekDay weekDay : WeekDay.values()) {
            if (name.equals(weekDay.getName())) {
                return weekDay;
            }
        }
        return null;
    }

    public int getName() {
        return name;
    }

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

    // TODO refactoring
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
        return new ArrayList<String>(Arrays.asList(repeatingDaysArray));
    }
}
