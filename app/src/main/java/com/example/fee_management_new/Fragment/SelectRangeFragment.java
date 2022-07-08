package com.example.fee_management_new.Fragment;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.DownloadReportResponse;
import com.example.fee_management_new.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectRangeFragment extends Fragment {
    private View view;
    private RadioGroup rb;
    private String filter, sdate, edate;
    private ApiService apiService;
    private static final String TAG = "SelectRangeFragment";
    private Button downloadbtn;
    private String report;
    DownloadManager manager;
    TransperentProgressDialog mProgressDialog;
    TextInputEditText startdate, enddate;


    public SelectRangeFragment() {
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
        view = inflater.inflate(R.layout.fragment_select_range, container, false);
        initWidgets();

        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.sRrb1) {
                    filter = "lastweek";
                    downloadReport();
                }
                if (i == R.id.sRrb2) {
                    filter = "lastmonth";
                    downloadReport();
                }
                if (i == R.id.sRrb3) {
                    filter = "lastthreemonths";
                    downloadReport();
                }
                if (i == R.id.sRrb4) {
                    filter = "lastsixmonths";
                    downloadReport();
                }
                if (i == R.id.sRrb5) {
                    filter = "yeartodate";
                    downloadReport();
                }
                if (i == R.id.sRrb6) {
                    sdate = startdate.getText().toString();
                    edate = enddate.getText().toString();
                    downloadReportforend_and_startdate();

                }
            }
        });
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        datePickerListener2,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mProgressDialog == null)
                    mProgressDialog = new TransperentProgressDialog(getContext());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(report);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                long reference = manager.enqueue(request);
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "File downloaded sucessfully", Toast.LENGTH_SHORT).show();


            }
        });
        return view;
    }

    private void downloadReportforend_and_startdate() {
        Map<String, String> param = new HashMap<>();
        param.put("startDate",sdate);
        param.put("endDate",edate);
        param.put("format", "json");
        Call<DownloadReportResponse> downloadReportResponseCall = apiService.DOWNLOAD_REPORT_RESPONSE_CALL(param);
        downloadReportResponseCall.enqueue(new Callback<DownloadReportResponse>() {
            @Override
            public void onResponse(Call<DownloadReportResponse> call, Response<DownloadReportResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:not successful " + response.code());
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                }
                DownloadReportResponse downloadReportResponse = response.body();
                Log.i(TAG, "onResponse:---->" + String.valueOf(Objects.requireNonNull(downloadReportResponse).getFile()));
                report = downloadReportResponse.getFile();
                Log.e("report", report);

            }

            @Override
            public void onFailure(Call<DownloadReportResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure:edate " + t);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            startdate.setText(day1 + "/" + month1 + "/" + year1);
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            enddate.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    private void downloadReport_new() {
//        manager = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(report);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//        long reference = manager.enqueue(request);
        //    downloadReport_new();
    }

    private void downloadReport() {
        Map<String, String> param = new HashMap<>();
        param.put("filter", filter);
        param.put("format", "json");
        Call<DownloadReportResponse> downloadReportResponseCall = apiService.DOWNLOAD_REPORT_RESPONSE_CALL(param);
        downloadReportResponseCall.enqueue(new Callback<DownloadReportResponse>() {
            @Override
            public void onResponse(Call<DownloadReportResponse> call, Response<DownloadReportResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:not successful " + response.code());
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                }
                DownloadReportResponse downloadReportResponse = response.body();
                Log.i(TAG, "onResponse:---->" + String.valueOf(Objects.requireNonNull(downloadReportResponse).getFile()));
                report = downloadReportResponse.getFile();
                Log.e("report", report);

            }

            @Override
            public void onFailure(Call<DownloadReportResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure:noedate " + t);
            }
        });
    }

    private void initWidgets() {
        apiService = ApiClient.getLoginService();
        rb = view.findViewById(R.id.sr_radioGroup);
        downloadbtn = view.findViewById(R.id.download_btn);
        startdate = view.findViewById(R.id.srdatepick1);
        enddate = view.findViewById(R.id.srdatepick);
//        rb2 = view.findViewById(R.id.sRrb2);
//        rb3 = view.findViewById(R.id.sRrb3);
//        rb4 = view.findViewById(R.id.sRrb4);
//        rb5 = view.findViewById(R.id.sRrb5);
//        rb6 = view.findViewById(R.id.sRrb6);
    }
}