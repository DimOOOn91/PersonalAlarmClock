package com.example.dima.personalalarmclock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dima.personalalarmclock.R;
import com.example.dima.personalalarmclock.adapter.AlarmAdapter;
import com.example.dima.personalalarmclock.controller.AlarmController;
import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.helper.AlarmHelper;
import com.example.dima.personalalarmclock.util.AppConstants;

public class AlarmListFragment extends BaseFragment implements View.OnClickListener {

    private AlarmController mAlarmController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        // initialize controller
        mAlarmController = new AlarmController();

        // initialize button add alarm and set the click listener
        Button btnAddAlarm = (Button) view.findViewById(R.id.main_btn_addAlarm);
        btnAddAlarm.setOnClickListener(this);

        // initialize recycler view for alarms
        RecyclerView recyclerAlarm = (RecyclerView) view.findViewById(R.id.main_recycler_alarm);

        // create layout manager for alarms' recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // create adapter for alarms' recycler view
        final AlarmAdapter alarmAdapter = new AlarmAdapter(getContext(), mAlarmController.getAllAlarms());
        // set click listener for the each alarm
        alarmAdapter.setClickListener(new AlarmAdapter.OnAlarmClickListener() {
            @Override
            public void onClick(View view, int position) {
                // get alarm id
                int itemId = (int) alarmAdapter.getItemId(position);
                // find alarm by id
                Alarm alarm = mAlarmController.getAlarmById(itemId);

                // check if pressed the button or the whole item
                switch (view.getId()) {
                    case R.id.item_btn_delete:
                        // remove alarm from DB
                        mAlarmController.removeAlarm(alarm);

                        // cancel alarm service
                        AlarmHelper.cancelAlarm(getActivity(), alarm);

                        // update visible list of alarms
                        alarmAdapter.updateList(mAlarmController.getAllAlarms());
                        break;

                    default:
                        // move to the chose alarm
                        startAlarmFragment(mAlarmController.getAlarmById(itemId));
                        break;
                }
            }
        });
        alarmAdapter.setSwitchCheckListener(new AlarmAdapter.OnSwitchCheckListener() {
            @Override
            public void onCheckedChanged(int position, boolean isSwitchedOn) {
                // get alarm id
                int itemId = (int) alarmAdapter.getItemId(position);
                // find alarm by id
                Alarm alarm = mAlarmController.getAlarmById(itemId);
                alarm.setEnabled(isSwitchedOn);

                // set or remove the service according to the switcher position
                if (isSwitchedOn) {
                    AlarmHelper.setAlarm(getContext(), alarm);
                } else {
                    AlarmHelper.cancelAlarm(getContext(), alarm);
                }

                // save changes to DB
                mAlarmController.saveAlarm(alarm);
            }
        });

        // set layout manager and adapter to the alarms' recycler view
        recyclerAlarm.setLayoutManager(linearLayoutManager);
        recyclerAlarm.setAdapter(alarmAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_addAlarm:
                startAlarmFragment(mAlarmController.createAlarm());
                break;
            default:
                break;
        }
    }


    // this method start AlarmFragment and sent it an alarm entity
    private void startAlarmFragment(Alarm alarm) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle alarmBundle = new Bundle();
        alarmBundle.putParcelable(AppConstants.ALARM_ENTITY_KEY, alarm);
        fragment.setArguments(alarmBundle);
        replaceFragment(fragment);
    }
}
