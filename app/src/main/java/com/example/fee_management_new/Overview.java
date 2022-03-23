package com.example.fee_management_new;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


public class Overview extends Fragment implements OnChartValueSelectedListener {
    View view;
    TextView Noofpayoverview;
    BarChart mChart;
    private static final float MAX_X_VALUE = 7f;
    private static final int MAX_YValue = 4000;
    private static final int MIN_YValue = 0;
    private static final String[] DAYS = {"11/07", "12/07", "13/07", "14/07", "15/07", "16/07", "17/07"};


    public Overview() {
        // Required empty public constructor
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;


        mChart.getBarBounds((BarEntry) e);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);


        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_overview, container, false);
        String name = com.example.fee_management_new.OverviewArgs.fromBundle(getArguments()).getOverviewdata();
        String Amt = com.example.fee_management_new.OverviewArgs.fromBundle(getArguments()).getAmount();
        String Payment_Req = OverviewArgs.fromBundle(getArguments()).getPaymentRequest();
        TextView No_of_payments = view.findViewById(R.id.No_of_Payments_Requested_overview);
        TextView Total_Amt = view.findViewById(R.id.total_amt_overview);
        TextView Payment_req = view.findViewById(R.id.payment_request_overview);
        Total_Amt.setText(Amt);
        No_of_payments.setText(name);
        Payment_req.setText(Payment_Req);
        mChart = view.findViewById(R.id.barchart);

        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);


        return view;

    }

    private void prepareChartData(BarData data) {

        mChart.setData(data);
//            mChart.setScaleEnabled(true);
//           mChart.setVisibleXRangeMaximum(7);
        mChart.invalidate();


        mChart.getLegend().setEnabled(true);


    }

    private BarData createChartData() {
        float[] Ypoints = {1000, 600, 1200, 1800, 2400, 2300, 3200};
        ArrayList<BarEntry> barone = new ArrayList<>();
        for (int i = 0; i < MAX_X_VALUE; i++) {
            float x = i;
            float y = Ypoints[i];
            //float y = new Util().randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE);
            barone.add(new BarEntry(x, y));
        }
        BarDataSet set1 = new BarDataSet(barone, "barOne");
        set1.setColor(Color.argb(250, 152, 118, 230));
//            set1.setHighlightEnabled(true);
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        float barWidth = 0.5f;

        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        data.setValueTextSize(12f);
        return data;

    }



    private void configureChartAppearance() {

//      mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setTouchEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.getAxisRight().setEnabled(false);
        mChart.setDrawValueAboveBar(true);
//        private float Randomnum() {
//        y = (float) (Math.random() * ( MAX_Y_VALUE - MIN_Y_VALUE + 1) + MIN_Y_VALUE);
//        DecimalFormat df = new DecimalFormat("#.#");
//        y = Float.parseFloat(df.format(y));
//
//        return y;
//    }


        XAxis xAxis = mChart.getXAxis();
//        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }

        });
//        xAxis.setAxisMaximum(DAYS.length - 0.5f);
//        leftAxis.setAxisMaximum(labels.length - 0.7f);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
//        xAxis.setAxisMinimum();
        xAxis.setGranularity(1);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        mChart.getXAxis().setAxisMinimum(-0.2f);
//        mChart.getXAxis().setAxisMaximum(labels.length);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        //leftAxis.setCenterAxisLabels(true);
//        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
//        leftAxis.setGranularity(1);
//        leftAxis.setValueFormatter(new IndexAxisValueFormatter(Ylabels));
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(35f);
//        leftAxis.setAxisMinimum(1);
//        leftAxis.setAxisMaximum(6);
        XYMarkerView mv = new XYMarkerView(getContext(), new IndexAxisValueFormatter());
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String name = com.example.fee_management_new.OverviewArgs.fromBundle(getArguments()).getOverviewdata();
        Noofpayoverview = view.findViewById(R.id.No_of_Payments_Requested_overview);
        Noofpayoverview.setText(name);


    }


}

