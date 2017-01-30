package com.example.dima.personalalarmclock.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.dima.personalalarmclock.util.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Alarm implements Parcelable {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", AppConstants.LOCALE);

    private int id;
    private int hours;
    private int minutes;
    private String message;
    private boolean isEnabled;
    private Calendar mDate;
    private ArrayList<String> repeatingDays;

    public Alarm(int id) {
        this.id = id;
        this.mDate = Calendar.getInstance();
        mDate.add(Calendar.MINUTE, 1);
        this.hours = mDate.get(Calendar.HOUR_OF_DAY);
        this.minutes = mDate.get(Calendar.MINUTE);
        this.message = "";
        this.isEnabled = true;
        this.repeatingDays = new ArrayList<>();
    }

    private Alarm(Parcel parcelAlarm) {
        this.id = parcelAlarm.readInt();
        this.hours = parcelAlarm.readInt();
        this.minutes = parcelAlarm.readInt();
        this.message = parcelAlarm.readString();
        this.isEnabled = parcelAlarm.readByte() == 1;
        this.mDate = parseStringToCalendar(parcelAlarm.readString());
        parcelAlarm.readStringList(this.repeatingDays);
    }

    private Calendar parseStringToCalendar(String stringDate) {
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Calendar getDate() {
        return mDate;
    }

    public void setDate(Calendar date) {
        mDate = date;
    }

    public ArrayList<String> getRepeatingDays() {
        return repeatingDays;
    }

    public boolean addOrRemoveDayForRepeat(String nameOfDay) {
        if (repeatingDays.contains(nameOfDay)) {
            repeatingDays.remove(nameOfDay);
            return false;
        } else {
            repeatingDays.add(nameOfDay);
            return true;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(hours);
        parcel.writeInt(minutes);
        parcel.writeString(message);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
        parcel.writeStringList(repeatingDays);
        String dateInString = dateFormat.format(mDate.getTime());
        parcel.writeString(dateInString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        if (id != alarm.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", message='" + message + '\'' +
                ", isEnabled=" + isEnabled +
                ", mDate=" + mDate +
                ", repeatingDays=" + repeatingDays +
                '}';
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

}
