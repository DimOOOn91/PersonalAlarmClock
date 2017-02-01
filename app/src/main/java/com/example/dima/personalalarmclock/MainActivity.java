package com.example.dima.personalalarmclock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dima.personalalarmclock.fragment.AlarmListFragment;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(new AlarmListFragment());

    }

    @Override
    public void onClick(View view) {
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void startFragmentForResult(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

}
