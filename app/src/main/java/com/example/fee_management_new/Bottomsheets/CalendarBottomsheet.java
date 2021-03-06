package com.example.fee_management_new.Bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fee_management_new.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CalendarBottomsheet extends BottomSheetDialogFragment {
    private CalendarView calendarView;
    private static final String TAG = "CalendarBottomsheet";

    public interface CalendarBottomsheetListener {
        void onDateSelected(String selectedDate);
    }

    private CalendarBottomsheetListener calendarBottomsheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_calendar, container, false);
        calendarView = view.findViewById(R.id.calander_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                Log.i(TAG, "onSelectedDayChange:i " + year);
                Log.i(TAG, "onSelectedDayChange:i1 " + month);
                Log.i(TAG, "onSelectedDayChange:i2 " + date);
                String dueDate = (new StringBuilder().append(date).append("/").append(month + 1).append("/").append(year).toString());
                Log.i(TAG, "onSelectedDayChange:dueDate "+dueDate);
                calendarBottomsheetListener.onDateSelected(dueDate);
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            calendarBottomsheetListener = (CalendarBottomsheetListener) getTargetFragment();
        }catch (ClassCastException c){
            Log.i(TAG, "onAttach: ClassCastException"+c.getMessage());
        }
        super.onAttach(context);
    }
}
