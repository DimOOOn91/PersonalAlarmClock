package com.example.dima.personalalarmclock.util;

import android.support.annotation.NonNull;

import java.util.Calendar;

public class DateAndTimeHelper {

    public static final String DATE_PICKER_FRAGMENT_DIALOG = "DatePickerFragmentDialog";
    public static final String TIME_PICKER_FRAGMENT_DIALOG = "TimePickerFragmentDialog";
    public static final String DATE_PARSE_REG_EXP = "%1$te/%1$tm/%1$tY";
    public static final String TIME_PARSE_REG_EXP = "%1$tH:%1$tM";

    public static String parseDateToString(Calendar date) {
        return String.format(AppConstants.LOCALE, DATE_PARSE_REG_EXP, date);
    }

    public static String parseTimeToString(Calendar time) {
        return String.format(AppConstants.LOCALE, TIME_PARSE_REG_EXP, time);
    }

    @NonNull
    public static String parseNumberToString(int number) {
        String result = String.valueOf(number);
        if (number < 10) {
            result = "0" + result;
        }
        return result;
    }

}
