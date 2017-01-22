package com.example.dima.personalalarmclock.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.dima.personalalarmclock.R;
import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.util.AppConstants;

import java.util.ArrayList;


public class AlarmFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Alarm mAlarmClock;

    private LinearLayout repeatDaysLayout;

    public AlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        //TODO get alarmClock from Bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mAlarmClock = bundle.getParcelable(AppConstants.ALARM_OBJ_KEY);
        } else {
            mAlarmClock = new Alarm();
        }
        repeatDaysLayout = (LinearLayout) view.findViewById(R.id.alFr_layout_repeatDays);

        CheckBox chBoxIsRepeated = (CheckBox) view.findViewById(R.id.alFr_check_repeat);
        chBoxIsRepeated.setOnCheckedChangeListener(this);

        Button btnSunday = (Button) view.findViewById(R.id.alFr_btn_sunday);
        Button btnMonday = (Button) view.findViewById(R.id.alFr_btn_monday);
        Button btnTuesday = (Button) view.findViewById(R.id.alFr_btn_tuesday);
        Button btnFriday = (Button) view.findViewById(R.id.alFr_btn_friday);
        Button btnWednesday = (Button) view.findViewById(R.id.alFr_btn_wednesday);
        Button btnThursday = (Button) view.findViewById(R.id.alFr_btn_thursday);
        Button btnSaturday = (Button) view.findViewById(R.id.alFr_btn_saturday);
        Button btnSet = (Button) view.findViewById(R.id.alFr_btn_set);
        Button btnDelete = (Button) view.findViewById(R.id.alFr_btn_delete);

        ArrayList<View> listOfViewsForClickListener = new ArrayList<>();
        listOfViewsForClickListener.add(btnSunday);
        listOfViewsForClickListener.add(btnMonday);
        listOfViewsForClickListener.add(btnTuesday);
        listOfViewsForClickListener.add(btnWednesday);
        listOfViewsForClickListener.add(btnThursday);
        listOfViewsForClickListener.add(btnFriday);
        listOfViewsForClickListener.add(btnSaturday);
        listOfViewsForClickListener.add(btnSet);
        listOfViewsForClickListener.add(btnDelete);

        setClickListenerForAllViews(listOfViewsForClickListener);

        return view;
    }

    private void setClickListenerForAllViews(ArrayList<View> list) {
        for (View view : list) {
            view.setOnClickListener(AlarmFragment.this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alFr_btn_sunday:
                view.setBackgroundResource(R.drawable.sunday_48_clicked);
                break;
            case R.id.alFr_btn_monday:
                view.setBackgroundResource(R.drawable.monday_48_clicked);
                break;
            case R.id.alFr_btn_tuesday:
                view.setBackgroundResource(R.drawable.tuesday_48_clicked);
                break;
            case R.id.alFr_btn_wednesday:
                view.setBackgroundResource(R.drawable.wednesday_48_clicked);
                break;
            case R.id.alFr_btn_thursday:
                view.setBackgroundResource(R.drawable.thursday_48_clicked);
                break;
            case R.id.alFr_btn_friday:
                view.setBackgroundResource(R.drawable.friday_48_clicked);
                break;
            case R.id.alFr_btn_saturday:
                view.setBackgroundResource(R.drawable.saturday_48_clicked);
                break;
            case R.id.alFr_btn_set:
                setAlarm();
                break;
            case R.id.alFr_btn_delete:
                deleteAlarm();
                break;
            default:
                break;
        }
    }

    private void deleteAlarm() {
        //TODO send intent ForResult()
        closeFragment();
    }

    private void setAlarm() {
        //TODO send intent ForResult()
        closeFragment();
    }

    private void closeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            repeatDaysLayout.setVisibility(View.VISIBLE);
        } else {
            repeatDaysLayout.setVisibility(View.GONE);
        }

    }
}
