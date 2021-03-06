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
import com.example.dima.personalalarmclock.util.DateAndTimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private List<Alarm> mAlarmList;
    private LayoutInflater mInflater;
    private OnAlarmClickListener mClickListener;
    private OnSwitchCheckListener mSwitchCheckListener;
    private Context mContext;

    public AlarmAdapter(Context context, List<Alarm> alarmList) {
        mInflater = LayoutInflater.from(context);
        mAlarmList = alarmList;
        mContext = context;
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
        // get alarm on the position
        final Alarm alarm = mAlarmList.get(position);
        // parse hours and minutes to string time
        String hours = DateAndTimeHelper.parseNumberToString(alarm.getHours());
        String minutes = DateAndTimeHelper.parseNumberToString(alarm.getMinutes());
        String time = hours + ':' + minutes;

        // check if the alarm is repeated or a single
        // and fill the appropriate textView
        // by the direct date or week days when the alarm should ring
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
            // check if set date is today or tomorrow and set the appropriate text
            Calendar currentDate = Calendar.getInstance();
            Calendar dateOfAlarm = alarm.getDate();
            int daysToAlarm = getEndOfDay(currentDate).get(Calendar.DATE) - getEndOfDay(dateOfAlarm).get(Calendar.DATE);
            if (daysToAlarm >= 0 && daysToAlarm < 1) {
                date = mContext.getResources().getString(R.string.today);
            } else if (daysToAlarm >= 1 && daysToAlarm < 2) {
                date = mContext.getResources().getString(R.string.tomorrow);
            } else {
                date = DateAndTimeHelper.parseDateToString(dateOfAlarm);
            }
        }

        // fill the views
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

    // Set calendar on the end of the day
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
            swIsOn = (Switch) view.findViewById(R.id.item_switcher_alarm);
            btnDelete = (Button) view.findViewById(R.id.item_btn_delete);
        }
    }
}
