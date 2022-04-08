package com.example.fee_management_new.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.AllActivitesRecentAdapter;
import com.example.fee_management_new.Adapters.AllActivitiesTwoAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllActivitiesTwoFragment extends Fragment {
    LinearLayout issueDate, status;
    RecyclerView allActivitytwoRecyclerView;
    int sectionid;
    TextView issueDataeTV;
    View view;
    ArrayList<AllActivitesTwoModal> allActivitesTwoModals;
    ApiService apiService;
    String payment_status,start_date,end_date;
    CalendarView calendarViewTwo;
    AppCompatButton cancel_btn,applybtn;
    TextInputEditText startDate,endDate;
    TextView Today, yesterday, this_Month, past_Month, past_Three_month, customiseDateRange;
    CheckBox paidCheckbox, pendingCheckbox, overdueCheckbox, refundCheckbox;
    private static final String TAG = "AllActivitiesTwoFragmen";


    public AllActivitiesTwoFragment() {
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
        view = inflater.inflate(R.layout.fragment_all_activities, container, false);
        apiService = ApiClient.getLoginService();
        sectionid = AllActivitiesTwoFragmentArgs.fromBundle(getArguments()).getId();
        Log.i("Sectionid", "onCreateView: " + sectionid);
        initWidgets();
        getAllTransaction();

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusBttomsheet();
            }
        });
        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                issueDateBottomsheet();
            }
        });
        return view;
    }

    private void getAllTransaction() {
        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.AllActivityTransactions(sectionid);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void buildRecylerviewforAllActitvitytwo() {
        allActivitytwoRecyclerView.setHasFixedSize(true);
        allActivitytwoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allActivitytwoRecyclerView.setAdapter(new AllActivitiesTwoAdapter(getContext(), allActivitesTwoModals));


    }

    private void initWidgets() {
        allActivitytwoRecyclerView = view.findViewById(R.id.allActivityRecyclerView);
        issueDate = view.findViewById(R.id.issuedate_layout);
        issueDataeTV = view.findViewById(R.id.iussuedate_tv);
        status = view.findViewById(R.id.status_layout);
    }

    private void statusBttomsheet() {
        view = getLayoutInflater().inflate(R.layout.status_bottomsheet, null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        paidCheckbox = view.findViewById(R.id.paid_checkbox);
        pendingCheckbox = view.findViewById(R.id.pending_checkbox);
        overdueCheckbox = view.findViewById(R.id.overdue_checkbox);
        refundCheckbox = view.findViewById(R.id.refund_checkbox);

        paidCheckbox.setChecked(getActivity().getSharedPreferences("Mypaid", Context.MODE_PRIVATE).getBoolean("checkBox", false));


        paidCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().getSharedPreferences("Mypaid", Context.MODE_PRIVATE).edit().putBoolean("checkBox", b).commit();
                if (b) {
                    paidCheckboxResponse();
                    pendingCheckbox.setChecked(false);
                    overdueCheckbox.setChecked(false);
                    refundCheckbox.setChecked(false);

                    payment_status = "Paid";
                }
//                else {
//                    getAllTransaction();
//
//                }
            }
        });
        pendingCheckbox.setChecked(getActivity().getSharedPreferences("Mypending", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        pendingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                getActivity().getSharedPreferences("Mypending", Context.MODE_PRIVATE).edit().putBoolean("checkBox", c).commit();
                if (c) {
                    pendingCheckboxResponse();
                    paidCheckbox.setChecked(false);
                    overdueCheckbox.setChecked(false);
                    refundCheckbox.setChecked(false);
                    payment_status = "Pending";
                }
//                else {
//                   getAllTransaction();
//
//                }
            }
        });
        overdueCheckbox.setChecked(getActivity().getSharedPreferences("Myoverdue", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        overdueCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean d) {
                getActivity().getSharedPreferences("Myoverdue", Context.MODE_PRIVATE).edit().putBoolean("checkBox", d).commit();
                if (d) {
                    overdueCheckboxResponse();
                    paidCheckbox.setChecked(false);
                    pendingCheckbox.setChecked(false);
                    refundCheckbox.setChecked(false);

                    payment_status = "Overdue";
                }
//                else {
//                   getAllTransaction();
//                }

            }
        });
        refundCheckbox.setChecked(getActivity().getSharedPreferences("Myrefund", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        refundCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean e) {
                getActivity().getSharedPreferences("Myrefund", Context.MODE_PRIVATE).edit().putBoolean("checkBox", e).commit();
                if (e) {
                    refundCheckboxResponse();
                    pendingCheckbox.setChecked(false);
                    paidCheckbox.setChecked(false);
                    overdueCheckbox.setChecked(false);
                    payment_status = "Refunded";
                }
//                else {
//                  getAllTransaction();
//                }
            }
        });

        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
    }

    private void refundCheckboxResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "Refunded");
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void overdueCheckboxResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "Overdue");
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void pendingCheckboxResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "Pending");
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void paidCheckboxResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "Paid");
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void issueDateBottomsheet() {
        view = getLayoutInflater().inflate(R.layout.issuedate_bottomsheet, null);
        Today = view.findViewById(R.id.today);
        yesterday = view.findViewById(R.id.yesterdaytv);
        this_Month = view.findViewById(R.id.this_month);
        past_Month = view.findViewById(R.id.past_month);
        past_Three_month = view.findViewById(R.id.past_three_month);
        customiseDateRange = view.findViewById(R.id.customizeDate_range);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todaytransactionResponse();
            }
        });
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yestardaytransactionResponse();
            }
        });
        this_Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisMothtransactionResponse();
            }
        });
        past_Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastMonthtransactionResponse();
            }
        });
        past_Three_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastthreeMonthtrnsactionResponse();
            }
        });
        customiseDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customdateRangebottomsheet();


            }
        });
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
    }

    private void customdateRangebottomsheet() {
        view = getLayoutInflater().inflate(R.layout.customisedaterangebottomsheet, null);
        calendarViewTwo = view.findViewById(R.id.calander_view_two);
        startDate = view.findViewById(R.id.startdate_tv);
        endDate = view.findViewById(R.id.enddate_tv);
        applybtn = view.findViewById(R.id.apply_btn);
        cancel_btn = view.findViewById(R.id.cancelbtn);
        BottomSheetDialog btn = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        btn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        btn.setContentView(view);
        btn.setCanceledOnTouchOutside(true);
        btn.getWindow().setGravity(Gravity.BOTTOM);
        btn.setCanceledOnTouchOutside(true);
        btn.show();
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectdateRangeResponse();
                btn.dismiss();
            }
        });

        calendarViewTwo.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                if (startDate.getText().length() <= 0) {
                    startDate.requestFocus();
                    startDate.setText((new StringBuilder().append(date).append("/").append(month + 1).append("/").append(year).toString()));
                    start_date = startDate.getText().toString();
                } else if (startDate.getText().length()!=0 && endDate.getText().length()<=0){
                    endDate.requestFocus();
                    endDate.setText((new StringBuilder().append(date).append("/").append(month + 1).append("/").append(year).toString()));
                    end_date = endDate.getText().toString();
                }
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDate.setText("");
                        endDate.setText("");
                    }
                });
            }
        });
    }

    private void getSelectdateRangeResponse() {

        Log.i(TAG, "getSelectdateRangeResponse: startdate"+start_date);
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "Customise range");
        params.put("startDate",start_date);
        params.put("endDate",end_date);

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void pastthreeMonthtrnsactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "threemonths");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void pastMonthtransactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "lastmonth");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void thisMothtransactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "month");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void yestardaytransactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "yesterday");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });

    }

    private void todaytransactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("status", payment_status);
        params.put("section[]", String.valueOf(sectionid));
        params.put("allRecords", "true");
        params.put("filter", "today");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
                int length = getAllTransactionResponse.getResponse().items.size();
                allActivitesTwoModals = new ArrayList<>();
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {
                        allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()), String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()), getAllTransactionResponse.getResponse().items.get(i).getNote(), getAllTransactionResponse.getResponse().items.get(i).getDate(), getAllTransactionResponse.getResponse().items.get(i).getStatus(), getAllTransactionResponse.getResponse().items.get(i).getId()));
                    }
                }
                Log.i(TAG, "onResponse:allActivitesTwoModalsize " + allActivitesTwoModals.size());
                buildRecylerviewforAllActitvitytwo();


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

}