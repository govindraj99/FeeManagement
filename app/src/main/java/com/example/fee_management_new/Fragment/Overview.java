package com.example.fee_management_new.Fragment;

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
import android.widget.Toast;

//import com.example.fee_management_new.OverviewArgs;
import com.example.fee_management_new.Api.AllTransactionList;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.Graph;
import com.example.fee_management_new.Api.OverViewResponse;
import com.example.fee_management_new.R;
import com.example.fee_management_new.XYMarkerView;
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
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Overview extends Fragment implements OnChartValueSelectedListener {
    View view;
    TextView Noofpayoverview;
    BarChart mChart;
    ApiService apiService;
    TextView No_of_payments, Total_Amt, Payment_req, paymentpending, pendingamount, pendingtoday, paymentpaid, paidamounts, paidtoday, paymentOverdue, overdueamount, overdueToday, paymentcancelled, cancelledamount, cancelledtoday, paymenRefunded, refundAmount, refundedToday;
    //    ArrayList<Graph> strings = new ArrayList<Graph>();
    ArrayList<Float> yVlaues;
    ArrayList<String> xVlaues;
    private static final String TAG = "Overview";
    private static final float MAX_X_VALUE = 7f;
    private static final int MAX_YValue = 4000;
    private static final int MIN_YValue = 0;
    private String[] DAYS = null;

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
        initWidigets();
        String name = OverviewArgs.fromBundle(getArguments()).getOverviewdata();
        String Amt = OverviewArgs.fromBundle(getArguments()).getAmount();
        String Payment_Req = OverviewArgs.fromBundle(getArguments()).getPaymentRequest();
        Total_Amt.setText(Amt);
        No_of_payments.setText(name);
        Payment_req.setText(Payment_Req);
        mChart = view.findViewById(R.id.barchart);
        overViewResponseforgraph();


        return view;


    }

    private void initWidigets() {
        apiService = ApiClient.getLoginService();
        No_of_payments = view.findViewById(R.id.No_of_Payments_Requested_overview);
        Total_Amt = view.findViewById(R.id.total_amt_overview);
        Payment_req = view.findViewById(R.id.payment_request_overview);
        paymentpending = view.findViewById(R.id.o_paymentPending_Tv);
        pendingamount = view.findViewById(R.id.o_amount2_tv);
        pendingtoday = view.findViewById(R.id.o_twentyfour_tv);
        paymentpaid = view.findViewById(R.id.o_paymentPaid);
        paidamounts = view.findViewById(R.id.o_amount3_tv);
        paidtoday = view.findViewById(R.id.o_paidtoday);
        paymentOverdue = view.findViewById(R.id.o_paymentOverdue);
        overdueamount = view.findViewById(R.id.amount4_tv);
        overdueToday = view.findViewById(R.id.o_overdue_today);
        paymentcancelled = view.findViewById(R.id.o_paymentcancelled);
        cancelledamount = view.findViewById(R.id.o_amount5_tv);
        cancelledtoday = view.findViewById(R.id.o_cancelledtoday);
        paymenRefunded = view.findViewById(R.id.o_paymentRefund);
        refundAmount = view.findViewById(R.id.o_amount6_tv);
        refundedToday = view.findViewById(R.id.refunded_today);

    }

    private void overViewResponseforgraph() {
        Map<String, String> param = new HashMap<>();
        param.put("type", "week");
        Call<OverViewResponse> overViewResponseCall = apiService.OVER_VIEW_RESPONSE_CALL(param);
        overViewResponseCall.enqueue(new Callback<OverViewResponse>() {
            @Override
            public void onResponse(Call<OverViewResponse> call, Response<OverViewResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                OverViewResponse overViewResponse = response.body();
                ArrayList<Graph> graphs = new ArrayList<>(overViewResponse.getGraph());
                xVlaues = new ArrayList<>();
                yVlaues = new ArrayList<>();

                for (Graph g :
                        graphs) {
                    yVlaues.add(Float.parseFloat(g.getTotalSumRequested()));
                    xVlaues.add(g.getShort_date());
                }
                DAYS = xVlaues.toArray(new String[0]);
                BarData data = createChartData();
                configureChartAppearance();
                prepareChartData(data);
                paymentpending.setText(String.valueOf(overViewResponse.getTotalPaymentPending().getCount()));
                pendingamount.setText(new StringBuffer().append("\u20B9 ").append(overViewResponse.getTotalPaymentPending().getAmount()));
                pendingtoday.setText(overViewResponse.getTotalPaymentPending().getTodayCount());
                paymentpaid.setText(String.valueOf(overViewResponse.getTotalPaymentPaid().getCount()));
                paidamounts.setText(new StringBuffer().append("\u20B9 ").append(overViewResponse.getTotalPaymentPaid().getAmount()));
                paidtoday.setText(overViewResponse.getTotalPaymentPaid().getTodayCount());
                paymentcancelled.setText(String.valueOf(overViewResponse.getTotalPaymentCancelled().getCount()));
                cancelledamount.setText(new StringBuffer().append("\u20B9 ").append(overViewResponse.getTotalPaymentCancelled().getAmount()));
                cancelledtoday.setText(overViewResponse.getTotalPaymentCancelled().getTodayCount());
                paymentOverdue.setText(String.valueOf(overViewResponse.getTotalPaymentOverDue().getCount()));
                overdueamount.setText(new StringBuffer().append("\u20B9 ").append(overViewResponse.getTotalPaymentOverDue().getAmount()));
                overdueToday.setText(overViewResponse.getTotalPaymentOverDue().getTodayCount());
                paymenRefunded.setText(String.valueOf(overViewResponse.getTotalPaymentRefunded().getCount()));
                refundAmount.setText(new StringBuffer().append("\u20B9 ").append(overViewResponse.getTotalPaymentRefunded().getAmount()));
                refundedToday.setText(overViewResponse.getTotalPaymentRefunded().getTodayCount());
            }

            @Override
            public void onFailure(Call<OverViewResponse> call, Throwable t) {

            }
        });
    }

    private void prepareChartData(BarData data) {

        mChart.setData(data);
//            mChart.setScaleEnabled(true);
//           mChart.setVisibleXRangeMaximum(7);
        mChart.invalidate();


        mChart.getLegend().setEnabled(true);


    }

    private BarData createChartData() {
        Float[] Ypoints = yVlaues.toArray(new Float[0]);
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
        String name = OverviewArgs.fromBundle(getArguments()).getOverviewdata();
        Noofpayoverview = view.findViewById(R.id.No_of_Payments_Requested_overview);
        Noofpayoverview.setText(name);


    }


}

