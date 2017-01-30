package com.example.dima.personalalarmclock.helper;

import com.example.dima.personalalarmclock.util.AppConstants;

import java.text.SimpleDateFormat;

public class DateAndTimeHelper {

    public static final String DATE_PICKER_FRAGMENT_DIALOG = "DatePickerFragmentDialog";
    public static final String TIME_PICKER_FRAGMENT_DIALOG = "TimePickerFragmentDialog";


    public static String getDateOnly(long time) {
        return new SimpleDateFormat("dd/MM/yyyy", AppConstants.LOCALE).format(time);
    }

    public static String getTimeOnly(long time) {
        return new SimpleDateFormat("hh:mm", AppConstants.LOCALE).format(time);
    }


}
