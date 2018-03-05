package com.example.andrey.newtmpclient.fragments.timepicker;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.storage.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "extra_date";
    public static final String ARGS_DATE = "date";
    int year;
    int month;
    int day;
    DateUtil dateUtil = new DateUtil();

    private TimePicker timePicker;

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DATE, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Date date = (Date) getArguments().getSerializable(ARGS_DATE);
        System.out.println("получили дату в таймпикере" + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int hours = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hours);
            timePicker.setMinute(minutes);
        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выбери время")
                .setPositiveButton("Подтвердить", (dialog1, which) -> {
                    int hours1 =0;
                    int minutes1 =0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hours1 = timePicker.getHour();
                        minutes1 = timePicker.getMinute();
                    }else{
                        hours1 = timePicker.getCurrentHour();
                        minutes1 = timePicker.getCurrentMinute();
                    }
                    Date date1 = new GregorianCalendar(year, month, day, hours1, minutes1).getTime();
                    Button activityChooseDate = (Button)getActivity().findViewById(R.id.choose_date);
                    activityChooseDate.setText(dateUtil.getDDFromYY(dateUtil.dateForServer(date1)));
                })
                .create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        });
        return dialog;
    }
}

