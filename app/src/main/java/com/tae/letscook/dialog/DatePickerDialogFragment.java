package com.tae.letscook.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.tae.letscook.R;
import com.tae.letscook.activity.ActivityDrawer;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.fragment.FragmentAddEvent;
import com.tae.letscook.listeners.OnGetDatePickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Eduardo on 09/12/2015.
 */
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String FRAGMENT_TAG = "DatePickerDialogFragment";
    private Calendar calendar;
    private OnGetDatePickerListener datePickerListener;

    public static DatePickerDialogFragment newInstance() {
        return new DatePickerDialogFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            FragmentAddEvent fragmentAddEvent = (FragmentAddEvent) getActivity().getSupportFragmentManager()
                    .findFragmentByTag(getResources().getString(R.string.fragment_add_event));
            datePickerListener =  fragmentAddEvent;
        } catch (ClassCastException e) {
            Log.e("DatePicker", "Must implement OnGetDatePickerListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
        datePickerListener.getDate(simpleDateFormat.format(calendar.getTime()));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        datePickerListener = null;
    }
}
