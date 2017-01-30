package com.example.dima.personalalarmclock.util;

import android.util.SparseBooleanArray;

public class AlarmCounter {

    private static int count = -1;
    private static SparseBooleanArray journal = new SparseBooleanArray();

    private static AlarmCounter instance;

    private AlarmCounter() {}

    public static AlarmCounter getInstance() {
        if (instance == null) {
            instance = new AlarmCounter();
        }
        return instance;
    }
    public int getId() {
        for (int i = 0; i < journal.size(); i++) {
            if (journal.get(i) == Boolean.FALSE) {
                journal.put(i, Boolean.TRUE);
                return i;
            }
        }
        int id = ++count;
        journal.append(id, Boolean.TRUE);
        return id;
    }

    public void deleteId(int id) {
        journal.put(id, Boolean.FALSE);
    }

}
