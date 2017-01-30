package com.example.dima.personalalarmclock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.dima.personalalarmclock.R;
import com.example.dima.personalalarmclock.entity.Alarm;
import com.example.dima.personalalarmclock.util.AppConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private List<Alarm> mAlarmList;
    private LayoutInflater mInflater;
    private OnAlarmClickListener mClickListener;
    private OnSwitchCheckListener mSwitchCheckListener;

    public AlarmAdapter(Context context, List<Alarm> alarmList) {
        mInflater = LayoutInflater.from(context);
        mAlarmList = alarmList;
    }

    public void setClickListener(OnAlarmClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setSwitchCheckListener(OnSwitchCheckListener switchCheckListener) {
        mSwitchCheckListener = switchCheckListener;
    }

    public void updateList(ArrayList<Alarm> list) {
        mAlarmList = list;
        notifyDataSetChanged();
    }

    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlarmHolder(mInflater.inflate(R.layout.item_alarm_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final AlarmHolder alarmHolder, int position) {
        final Alarm alarm = mAlarmList.get(position);
        String time = String.format(AppConstants.LOCALE, "%d:%d", alarm.getHours(), alarm.getMinutes());
        ArrayList<String> repeatingDays = alarm.getRepeatingDays();
        boolean isRepeated = repeatingDays.size() > 0;
        String date;
        if (isRepeated) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < repeatingDays.size(); i++) {
                String weekDay = repeatingDays.get(i);
                if (i == repeatingDays.size() - 1) {
                    stringBuilder.append(weekDay);
                } else {
                    stringBuilder.append(weekDay + ", ");
                }
            }
            date = stringBuilder.toString();
        } else {
            Calendar currentDate = Calendar.getInstance();
            Calendar dateOfAlarm = alarm.getDate();
            int daysToAlarm = getEndOfDay(currentDate).get(Calendar.DATE) - getEndOfDay(dateOfAlarm).get(Calendar.DATE);
            if (daysToAlarm >= 0 && daysToAlarm < 1) {
                date = "Today";
            } else if (daysToAlarm >= 1 && daysToAlarm < 2) {
                date = "Tomorrow";
            } else {
                date = String.format(AppConstants.LOCALE, "%1$te/%1$tm/%1$tY", dateOfAlarm);
            }
        }
        alarmHolder.tvTime.setText(time);
        alarmHolder.tvDate.setText(date);
        alarmHolder.swIsOn.setChecked(alarm.isEnabled());
        alarmHolder.swIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSwitchCheckListener.onCheckedChanged(alarmHolder.getAdapterPosition(), b);
            }
        });
        alarmHolder.tvMessage.setText(alarm.getMessage());
        alarmHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view, alarmHolder.getAdapterPosition());
            }
        });
        alarmHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view, alarmHolder.getAdapterPosition());
            }
        });
    }

    private Calendar getEndOfDay(Calendar calendar) {
        Calendar result = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        result.set(year, month, day, 23, 59, 59);
        return result;
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    @Override
    public long getItemId(int position) {
        return mAlarmList.get(position).getId();
    }

    public interface OnSwitchCheckListener {
        void onCheckedChanged(int position, boolean isSwitchedOn);
    }

    public interface OnAlarmClickListener {
        void onClick(View view, int position);
    }

    static class AlarmHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvDate;
        TextView tvMessage;
        Switch swIsOn;
        Button btnDelete;
        View view;

        public AlarmHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTime = (TextView) view.findViewById(R.id.item_tv_time);
            tvDate = (TextView) view.findViewById(R.id.item_tv_date);
            tvMessage = (TextView) view.findViewById(R.id.item_tv_message);
            swIsOn = (Switch) view.findViewById(R.id.switcher_alarm);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
        }
    }
}
