package com.example.dima.personalalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.dima.personalalarmclock.adapter.AlarmAdapter;
import com.example.dima.personalalarmclock.controller.AlarmController;
import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.fragments.AlarmFragment;
import com.example.dima.personalalarmclock.util.AppConstants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AlarmController mAlarmController;

    private AlarmFragment mAlarmFragment;
    private Button mBtnAddAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmController = new AlarmController();

        mBtnAddAlarm = (Button) findViewById(R.id.main_btn_addAlarm);
        mBtnAddAlarm.setOnClickListener(this);

        RecyclerView recyclerAlarm = (RecyclerView) findViewById(R.id.main_recycler_alarm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final AlarmAdapter alarmAdapter = new AlarmAdapter(this, mAlarmController.getAllAlarms());
        alarmAdapter.setClickListener(new AlarmAdapter.OnAlarmClickListener() {
            @Override
            public void onClick(int position) {
                int itemId = (int) alarmAdapter.getItemId(position);
                startAlarmFragment(mAlarmController.getAlarmById(itemId));
            }
        });

        recyclerAlarm.setLayoutManager(linearLayoutManager);
        recyclerAlarm.setAdapter(alarmAdapter);


    }

    private void startAlarmFragment(Alarm alarm) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mAlarmFragment = new AlarmFragment();
        Bundle alarmBundle = new Bundle();
        alarmBundle.putParcelable(AppConstants.ALARM_OBJ_KEY, alarm);
        mAlarmFragment.setArguments(alarmBundle);
        fragmentTransaction.add(R.id.activity_main, mAlarmFragment, "tag");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_addAlarm :
                startAlarmFragment(new Alarm());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
