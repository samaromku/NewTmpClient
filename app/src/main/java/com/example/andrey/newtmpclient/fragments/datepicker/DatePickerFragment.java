package com.example.andrey.newtmpclient.fragments.datepicker;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.fragments.timepicker.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    public static final String ARG_DATE="date";
    public static final String EXTRA_DATE ="extra_date";
    private static final int REQUEST_DATE = 0;
    private DatePicker datePicker;
    private static final String DIALOG_DATE = "date_dialog";


    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        datePicker.init(year, month, day, null);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выбрать дату")
                .setPositiveButton("Подтвердить", (d, which) -> {
                    int year1 = datePicker.getYear();
                    int month1 = datePicker.getMonth();
                    int day1 = datePicker.getDayOfMonth();
                    Date date1 = new GregorianCalendar(year1, month1, day1).getTime();
                    sendResult(Activity.RESULT_OK, date1);

                }).create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        });
        return dialog;
    }

    private void sendResult(int resultCode, Date date){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        FragmentManager manager = getFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(date);
        dialog.setTargetFragment(this, REQUEST_DATE);
        dialog.show(manager, DIALOG_DATE);
        System.out.println("передаем дату в таймпикер" + date);
    }

}

