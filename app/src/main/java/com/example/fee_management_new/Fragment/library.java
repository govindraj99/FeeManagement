package com.example.fee_management_new.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fee_management_new.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class library extends Fragment {
    View view;
    private static final float MAX_X_VALUE = 7f;
    private static final float MAX_Y_VALUE = 4000f;
    private static final float MIN_Y_VALUE = 0f;
    private static final String SET_LABEL = "Average Temperature";
    private static final String[] DAYS = {"11/07", "12/07", "13/07", "14/07", "15/07", "16/07", "17/07"};
    private BarChart chart;
    float y;
Button button;

    public library() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library, container, false);
        button = view.findViewById(R.id.button);
        NavController navController = NavHostFragment.findNavController(this);


                NavDirections action5 = libraryDirections.actionLibrary2ToLibraryFragmenttwo();
                navController.navigate(R.id.action_library2_to_libraryFragmenttwo);



        return view;


    }
}