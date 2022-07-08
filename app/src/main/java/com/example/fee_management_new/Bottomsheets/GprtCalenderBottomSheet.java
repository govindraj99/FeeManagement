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

public class GprtCalenderBottomSheet extends BottomSheetDialogFragment {
    private CalendarView calendarViewTwo;
    private static final String TAG = "GprtCalenderBottomSheet";
    public interface CalendarBottomsheetListener {
        void onDueDateSelected(String selectedDate);
    }

    private GprtCalenderBottomSheet.CalendarBottomsheetListener calendarBottomsheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_calendar, container, false);
        calendarViewTwo = view.findViewById(R.id.calander_view);
        calendarViewTwo.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarViewTwo, int year, int month, int date) {
                Log.i(TAG, "onSelectedDayChange:i " + year);
                Log.i(TAG, "onSelectedDayChange:i1 " + month);
                Log.i(TAG, "onSelectedDayChange:i2 " + date);
                String dueDate = (new StringBuilder().append(date).append("/").append(month + 1).append("/").append(year).toString());
                Log.i(TAG, "onSelectedDayChange:dueDate "+dueDate);
                calendarBottomsheetListener.onDueDateSelected(dueDate);
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            calendarBottomsheetListener = (GprtCalenderBottomSheet.CalendarBottomsheetListener) getTargetFragment();
        }catch (ClassCastException c){
            Log.i(TAG, "onAttach: ClassCastException"+c.getMessage());
        }
        super.onAttach(context);
    }
}
