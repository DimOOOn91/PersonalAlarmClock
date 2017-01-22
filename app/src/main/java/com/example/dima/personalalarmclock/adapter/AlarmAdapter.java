package com.example.dima.personalalarmclock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.dima.personalalarmclock.R;
import com.example.dima.personalalarmclock.entity.Alarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private List<Alarm> mAlarmList;
    private LayoutInflater mInflater;
    private OnAlarmClickListener mClickListener;

    public AlarmAdapter(Context context, List<Alarm> alarmList) {
        mInflater = LayoutInflater.from(context);
        mAlarmList = alarmList;
    }

    public void setClickListener(OnAlarmClickListener clickListener) {
        mClickListener = clickListener;
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
        Alarm alarm = mAlarmList.get(position);
        String time = String.format("%s:%s", alarm.getHours(), alarm.getMinutes());
        boolean isRepeated = alarm.isRepeated();
        String date;
        if (isRepeated) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String weekDay : alarm.getRepeatingDays()) {
                stringBuilder.append(weekDay + ", ");
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
                date = String.format("%1$tD  %1$tH:%1$tM:%1$tS", dateOfAlarm);
            }
        }
        alarmHolder.tvTime.setText(time);
        alarmHolder.tvDate.setText(date);
        alarmHolder.swIsOn.setEnabled(alarm.isEnabled());
        alarmHolder.tvMessage.setText(alarm.getMessage());
        alarmHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(alarmHolder.getAdapterPosition());
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

    public interface OnAlarmClickListener{
        void onClick(int position);
    }

    static class AlarmHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvDate;
        TextView tvMessage;
        Switch swIsOn;
        View view;

        public AlarmHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTime = (TextView) view.findViewById(R.id.item_tv_time);
            tvDate = (TextView) view.findViewById(R.id.item_tv_date);
            tvMessage = (TextView) view.findViewById(R.id.item_tv_message);
            swIsOn = (Switch) view.findViewById(R.id.switcher_alarm);
        }
    }
}
