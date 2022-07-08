package com.example.fee_management_new.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.TransactionAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Api.Item2;
import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.Modalclass.TransactionData;
import com.example.fee_management_new.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Transaction extends Fragment {
    View view;
    RecyclerView rvTransaction;
    ArrayList<TransactionData> transactionDataAll;
    private static final String TAG = "Transaction";
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    CardView TransacCardview;
    String paymentmode, start_date_pd, end_date_pd, filter = null;
    CalendarView calendarViewThree;
    AppCompatButton cancel_btn_pd, applybtn_pd;
    TextInputEditText startDate, endDate;
    CheckBox offlinepayment, onlinepayment;
    TransactionAdapter transactionAdapter;
    TextView today_pd, yesterdey_pd, thisMonth_pd, pastMonth_pd, pastThreeMonth_pd, customisedate_pd;
    ImageView today_pdIV, yesterday_pdIV, thismonth_pdIV, pastmonth_pdIV, past3month_pdIV, customise_date_pdIV;


    ApiService apiService;
    NavController navController;
    LinearLayout Paymentdate_Bottomsheet, TypeofPayment_layout;
    ConstraintLayout nodataLayout;


    public Transaction() {
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
        view = inflater.inflate(R.layout.fragment_transaction, container, false);
        apiService = ApiClient.getLoginService();
        setHasOptionsMenu(true);
        rvTransaction = view.findViewById(R.id.TransactionRv);
        nodataLayout = view.findViewById(R.id.contl);
        getTransactionResponse();
//        CreateTransactionCard();
//        buildTransactionCard();
        Paymentdate_Bottomsheet = view.findViewById(R.id.payment_datelayout);
        TypeofPayment_layout = view.findViewById(R.id.typeofPayment_layout);
        TransacCardview = view.findViewById(R.id.cardViewtrasc);

        TypeofPayment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetTypeofPayment();
            }
        });
        Paymentdate_Bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetPaymentdate();
            }
        });

        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(getActivity(), R.id.activity_main_nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    private void getTransactionResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: " + response.code());
                }
                try {
                    GetAllTransactionResponse getAllTransactionResponse = response.body();
                    int length = getAllTransactionResponse.getResponse().items.size();
                    Log.i(TAG, "onResponse: length" + length);
                    transactionDataAll = new ArrayList<>();
                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
                    if (length != 0) {
                        for (int i = 0; i < item2ArrayList.size(); i++) {
                            try {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                            } catch (Exception e) {
                                transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                            }
                        }
                    }
                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                    buildTransactionCard(transactionDataAll);
                } catch (
                        Exception e) {
                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void bottomsheetTypeofPayment() {
        view = getLayoutInflater().inflate(R.layout.type_of_paymentbottomsheet, null);
        offlinepayment = view.findViewById(R.id.offline_checkbox);
        onlinepayment = view.findViewById(R.id.online_checkbox);
        offlinepayment.setChecked(getActivity().getSharedPreferences("offline", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        offlinepayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().getSharedPreferences("offline", Context.MODE_PRIVATE).edit().putBoolean("checkBox", b).commit();
                if (b) {
                    onlinepayment.setChecked(false);
                    paymentmode = "offline";
                    getOfflineResponse_onlineResponse();
                    Toast.makeText(getContext(), paymentmode, Toast.LENGTH_SHORT).show();

                }


            }
        });
        onlinepayment.setChecked(getActivity().getSharedPreferences("online", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        onlinepayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                getActivity().getSharedPreferences("online", Context.MODE_PRIVATE).edit().putBoolean("checkBox", c).commit();
                if (c) {
                    offlinepayment.setChecked(false);
                    paymentmode = "online";
                    Toast.makeText(getContext(), paymentmode, Toast.LENGTH_SHORT).show();
                    onlineresponse();

                }
            }
        });

        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();

    }

    private void getOfflineResponse_onlineResponse() {

        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode", "offline");
//        params.put("filter", filter);

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: " + response.code());
                }
                try {
                    GetAllTransactionResponse getAllTransactionResponse = response.body();
                    int length = getAllTransactionResponse.getResponse().items.size();
                    Log.i(TAG, "onResponse: length" + length);
                    transactionDataAll = new ArrayList<>();
                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
                    if (length != 0) {
                        for (int i = 0; i < item2ArrayList.size(); i++) {
                            try {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                            } catch (Exception e) {
                                transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                            }
                        }
                    }
                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                    buildTransactionCard(transactionDataAll);
                } catch (
                        Exception e) {
                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void onlineresponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode", "online");
//        params.put("filter", filter);

        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse
                    (Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: " + response.code());
                }
                try {
                    GetAllTransactionResponse getAllTransactionResponse = response.body();
                    int length = getAllTransactionResponse.getResponse().items.size();
                    Log.i(TAG, "onResponse: length" + length);
                    transactionDataAll = new ArrayList<>();
                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
                    if (length != 0) {
                        for (int i = 0; i < item2ArrayList.size(); i++) {
                            try {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                            } catch (Exception e) {
                                transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                            }
                        }
                    }
                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                    buildTransactionCard(transactionDataAll);
                } catch (
                        Exception e) {
                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }


    private void bottomsheetPaymentdate() {
        view = getLayoutInflater().inflate(R.layout.paymentdate_bottomsheet, null);
        today_pd = view.findViewById(R.id.today_paymentdate);
        yesterdey_pd = view.findViewById(R.id.yesterday_paymentdate);
        thisMonth_pd = view.findViewById(R.id.this_month_paymentdate);
        pastMonth_pd = view.findViewById(R.id.past_month_paymentdate);
        pastThreeMonth_pd = view.findViewById(R.id.past_three_month_pd);
        customisedate_pd = view.findViewById(R.id.customise_pd);
        today_pdIV = view.findViewById(R.id.today_iv_pd);
        yesterday_pdIV = view.findViewById(R.id.yesterday_iv_pd);
        thismonth_pdIV = view.findViewById(R.id.thismonth_iv_pd);
        pastmonth_pdIV = view.findViewById(R.id.pastmonth_iv_pd);
        past3month_pdIV = view.findViewById(R.id.past3months_iv_pd);
        customise_date_pdIV = view.findViewById(R.id.customisedate_iv_pd);


        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
        today_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Today clicked", Toast.LENGTH_SHORT).show();
                filter = "today";
                timeFilter();
                today_pdIV.setVisibility(View.VISIBLE);
                yesterday_pdIV.setVisibility(View.GONE);
                thismonth_pdIV.setVisibility(View.GONE);
                pastmonth_pdIV.setVisibility(View.GONE);
                past3month_pdIV.setVisibility(View.GONE);
                customise_date_pdIV.setVisibility(View.GONE);

                today_pd.setTypeface(Typeface.DEFAULT_BOLD);
                yesterdey_pd.setTypeface(Typeface.DEFAULT);
                thisMonth_pd.setTypeface(Typeface.DEFAULT);
                pastMonth_pd.setTypeface(Typeface.DEFAULT);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT);
                customisedate_pd.setTypeface(Typeface.DEFAULT);

            }
        });
        yesterdey_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "yesterday";
                timeFilter();
                today_pdIV.setVisibility(View.GONE);
                yesterday_pdIV.setVisibility(View.VISIBLE);
                thismonth_pdIV.setVisibility(View.GONE);
                pastmonth_pdIV.setVisibility(View.GONE);
                past3month_pdIV.setVisibility(View.GONE);
                customise_date_pdIV.setVisibility(View.GONE);

                today_pd.setTypeface(Typeface.DEFAULT);
                yesterdey_pd.setTypeface(Typeface.DEFAULT_BOLD);
                thisMonth_pd.setTypeface(Typeface.DEFAULT);
                pastMonth_pd.setTypeface(Typeface.DEFAULT);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT);
                customisedate_pd.setTypeface(Typeface.DEFAULT);

            }
        });
        thisMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "month";
                timeFilter();
                today_pdIV.setVisibility(View.GONE);
                yesterday_pdIV.setVisibility(View.GONE);
                thismonth_pdIV.setVisibility(View.VISIBLE);
                pastmonth_pdIV.setVisibility(View.GONE);
                past3month_pdIV.setVisibility(View.GONE);
                customise_date_pdIV.setVisibility(View.GONE);

                today_pd.setTypeface(Typeface.DEFAULT);
                yesterdey_pd.setTypeface(Typeface.DEFAULT);
                thisMonth_pd.setTypeface(Typeface.DEFAULT_BOLD);
                pastMonth_pd.setTypeface(Typeface.DEFAULT);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT);
                customisedate_pd.setTypeface(Typeface.DEFAULT);

            }
        });
        pastMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "lastmonth";
                timeFilter();
                today_pdIV.setVisibility(View.GONE);
                yesterday_pdIV.setVisibility(View.GONE);
                thismonth_pdIV.setVisibility(View.GONE);
                pastmonth_pdIV.setVisibility(View.VISIBLE);
                past3month_pdIV.setVisibility(View.GONE);
                customise_date_pdIV.setVisibility(View.GONE);

                today_pd.setTypeface(Typeface.DEFAULT);
                yesterdey_pd.setTypeface(Typeface.DEFAULT);
                thisMonth_pd.setTypeface(Typeface.DEFAULT);
                pastMonth_pd.setTypeface(Typeface.DEFAULT_BOLD);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT);
                customisedate_pd.setTypeface(Typeface.DEFAULT);


            }
        });
        pastThreeMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "threemonths";
                timeFilter();
                today_pdIV.setVisibility(View.GONE);
                yesterday_pdIV.setVisibility(View.GONE);
                thismonth_pdIV.setVisibility(View.GONE);
                pastmonth_pdIV.setVisibility(View.GONE);
                past3month_pdIV.setVisibility(View.VISIBLE);
                customise_date_pdIV.setVisibility(View.GONE);

                today_pd.setTypeface(Typeface.DEFAULT);
                yesterdey_pd.setTypeface(Typeface.DEFAULT);
                thisMonth_pd.setTypeface(Typeface.DEFAULT);
                pastMonth_pd.setTypeface(Typeface.DEFAULT);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT_BOLD);
                customisedate_pd.setTypeface(Typeface.DEFAULT);
            }
        });
        customisedate_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customdateRangebottomsheet();
                today_pdIV.setVisibility(View.GONE);
                yesterday_pdIV.setVisibility(View.GONE);
                thismonth_pdIV.setVisibility(View.GONE);
                pastmonth_pdIV.setVisibility(View.GONE);
                past3month_pdIV.setVisibility(View.GONE);
                customise_date_pdIV.setVisibility(View.VISIBLE);

                today_pd.setTypeface(Typeface.DEFAULT);
                yesterdey_pd.setTypeface(Typeface.DEFAULT);
                thisMonth_pd.setTypeface(Typeface.DEFAULT);
                pastMonth_pd.setTypeface(Typeface.DEFAULT);
                pastThreeMonth_pd.setTypeface(Typeface.DEFAULT);
                customisedate_pd.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
    }

    private void customdateRangebottomsheet() {
        view = getLayoutInflater().inflate(R.layout.customisedaterangebottomsheet, null);
        calendarViewThree = view.findViewById(R.id.calander_view_two);
        startDate = view.findViewById(R.id.startdate_tv);
        endDate = view.findViewById(R.id.enddate_tv);
        applybtn_pd = view.findViewById(R.id.apply_btn);
        cancel_btn_pd = view.findViewById(R.id.cancelbtn);
        BottomSheetDialog btn = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        btn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        btn.setContentView(view);
        btn.setCanceledOnTouchOutside(true);
        btn.getWindow().setGravity(Gravity.BOTTOM);
        btn.setCanceledOnTouchOutside(true);
        btn.show();
        applybtn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectdateRangeResponse();
                btn.dismiss();
            }
        });

        calendarViewThree.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                if (startDate.getText().length() <= 0) {
                    startDate.requestFocus();
                    startDate.setText((new StringBuilder().append(year).append("/").append(month + 1).append("/").append(date).toString()));
                    start_date_pd = startDate.getText().toString();
                } else if (startDate.getText().length() != 0 && endDate.getText().length() <= 0) {
                    endDate.requestFocus();
                    endDate.setText((new StringBuilder().append(year).append("/").append(month + 1).append("/").append(date).toString()));
                    end_date_pd = endDate.getText().toString();
                }
                cancel_btn_pd.setOnClickListener(new View.OnClickListener() {
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
        if (paymentmode == null) {
            Log.i(TAG, "getSelectdateRangeResponse: startdate" + start_date_pd);
            Map<String, String> params = new HashMap<>();
            params.put("allRecords", "true");
            params.put("status", "paidAndRefunded");
            params.put("filter", "Customise range");
            params.put("startDate", start_date_pd);
            params.put("endDate", end_date_pd);


            Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
            allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
                @Override
                public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: " + response.code());
                    }
                    try {
                        GetAllTransactionResponse getAllTransactionResponse = response.body();
                        int length = getAllTransactionResponse.getResponse().items.size();
                        Log.i(TAG, "onResponse: length" + length);
                        transactionDataAll = new ArrayList<>();
                        ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                        Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                        Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                        //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                        if (length != 0) {
                        for (int i = 0; i < item2ArrayList.size(); i++) {
                            try {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                            } catch (Exception e) {
                                transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                            }
                        }

                        Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                        buildTransactionCard(transactionDataAll);
                    } catch (
                            Exception e) {
                        Log.i(TAG, "onResponse:Catch " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

                }
            });
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("mode", paymentmode);
            params.put("allRecords", "true");
            params.put("filter", "Customise range");
            params.put("startDate", start_date_pd);
            params.put("endDate", end_date_pd);

            Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
            allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
                @Override
                public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: " + response.code());
                    }
                    try {
                        GetAllTransactionResponse getAllTransactionResponse = response.body();
                        int length = getAllTransactionResponse.getResponse().items.size();
                        Log.i(TAG, "onResponse: length" + length);
                        transactionDataAll = new ArrayList<>();
                        ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                        Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                        Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                        //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                        if (length != 0) {
                        for (int i = 0; i < item2ArrayList.size(); i++) {
                            try {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                            } catch (Exception e) {
                                transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                            }
                        }

                        Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                        buildTransactionCard(transactionDataAll);
                    } catch (
                            Exception e) {
                        Log.i(TAG, "onResponse:Catch " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

                }
            });
        }
    }


    private void timeFilter() {
        if (paymentmode == null) {
            Map<String, String> params = new HashMap<>();
            params.put("allRecords", "true");
            params.put("status", "paidAndRefunded");
//            params.put("mode", paymentmode);
            params.put("filter", filter);

            Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
            allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
                @Override
                public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: " + response.code());
                    }
                    try {
                        nodataLayout.setVisibility(View.GONE);
                        rvTransaction.setVisibility(View.VISIBLE);
                        GetAllTransactionResponse getAllTransactionResponse = response.body();
                        int length = getAllTransactionResponse.getResponse().items.size();
                        Log.i(TAG, "onResponse: length" + length);
                        transactionDataAll = new ArrayList<>();
                        ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                        Log.i(TAG, "onResponse: size" + item2ArrayList.size());
                        Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                        //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
                        if (length != 0) {
                            for (int i = 0; i < item2ArrayList.size(); i++) {
                                try {
                                    transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                                } catch (Exception e) {
                                    transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));

                                }
                            }
                        }
                        Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                        buildTransactionCard(transactionDataAll);
                    } catch (Exception e) {
                        Log.i(TAG, "onResponse:Catch " + e.getMessage());
                        nodataLayout.setVisibility(View.VISIBLE);
                        rvTransaction.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

                }
            });
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("allRecords", "true");
            params.put("status", "paidAndRefunded");
            params.put("mode", paymentmode);
            params.put("filter", filter);

            Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
            allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
                @Override
                public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: " + response.code());
                    }
                    try {
                        rvTransaction.setVisibility(View.VISIBLE);
                        nodataLayout.setVisibility(View.GONE);
                        GetAllTransactionResponse getAllTransactionResponse = response.body();
                        transactionDataAll = new ArrayList<>();
                        ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
                        int length = item2ArrayList.size();
                        Log.i(TAG, "onResponse: size" + length);
                        Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
                        //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
                        if (length != 0) {
                            for (int i = 0; i < item2ArrayList.size(); i++) {
                                try {
                                    transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
                                } catch (Exception e) {
                                    transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
                                }
                            }
                        }
//                        else {
//                            Toast.makeText(getContext(), "inside else", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "onResponse: inside else");
//                            ConstraintLayout con = view.findViewById(R.id.contl);
//                            con.setVisibility(View.VISIBLE);
////                            LayoutInflater inflater = LayoutInflater.from(getContext());
////                            inflater.inflate(R.layout.nodata_layout, null, false);
//                        }
//                        try {
//                            Toast.makeText(getContext(), "inside if", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "onResponse:sizetry " + item2ArrayList.size());
//                            for (int i = 0; i < item2ArrayList.size(); i++) {
//                                try {
//                                    transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage(), item2ArrayList.get(i).getInvoice()));
//                                } catch (Exception e) {
//                                    transactionDataAll.add(new TransactionData("no data", "no data", "no data", "no data", "no data", "no data", "no data", "no data", "null"));
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.i(TAG, "onResponse:sizecatch " + item2ArrayList.size());
////                            nodataLayout.setVisibility(View.VISIBLE);
//
//                        }
                        Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
                        buildTransactionCard(transactionDataAll);
                    } catch (Exception e) {
                        Log.i(TAG, "onResponse:Catch " + e.getMessage());
                        nodataLayout.setVisibility(View.VISIBLE);
                        rvTransaction.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

                }
            });
        }
    }

//    private void pastMonthpdResponse() {
//        Map<String, String> params = new HashMap<>();
//        params.put("allRecords", "true");
//        params.put("status", "paidAndRefunded");
//        params.put("filter", filter);
//        params.put("mode", paymentmode);
//
//        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
//        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
//            @Override
//            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
//
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                    Log.i("TAG", "onResponse: " + response.code());
//                }
//                try {
//                    GetAllTransactionResponse getAllTransactionResponse = response.body();
//                    int length = getAllTransactionResponse.getResponse().items.size();
//                    Log.i(TAG, "onResponse: length" + length);
//                    transactionDataAll = new ArrayList<>();
//                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
//                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
//                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
//                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                    if (length != 0) {
//                        for (int i = 0; i < item2ArrayList.size(); i++) {
//                            if (item2ArrayList.get(i).getStandardId() == -1) {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            } else {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            }
//                        }
//                    }
//                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
//                    buildTransactionCard();
//                } catch (
//                        Exception e) {
//                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
//
//            }
//        });
//    }

//    private void thismonthPaymentResponse() {
//        Map<String, String> params = new HashMap<>();
//        params.put("allRecords", "true");
//        params.put("status", "paidAndRefunded");
//        params.put("filter", "month");
//        params.put("mode", paymentmode);
//
//        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
//        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
//            @Override
//            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
//
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                    Log.i("TAG", "onResponse: " + response.code());
//                }
//                try {
//                    GetAllTransactionResponse getAllTransactionResponse = response.body();
//                    int length = getAllTransactionResponse.getResponse().items.size();
//                    Log.i(TAG, "onResponse: length" + length);
//                    transactionDataAll = new ArrayList<>();
//                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
//                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
//                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
//                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                    if (length != 0) {
//                        for (int i = 0; i < item2ArrayList.size(); i++) {
//                            if (item2ArrayList.get(i).getStandardId() == -1) {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            } else {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            }
//                        }
//                    }
//                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
//                    buildTransactionCard();
//                } catch (
//                        Exception e) {
//                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
//
//            }
//        });
//    }

//    private void yesterdayPaymentResponse() {
//        Map<String, String> params = new HashMap<>();
//        params.put("allRecords", "true");
//        params.put("status", "paidAndRefunded");
//        params.put("filter", "yesterday");
//        params.put("mode", paymentmode);
//
//        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
//        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
//            @Override
//            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
//
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                    Log.i("TAG", "onResponse: " + response.code());
//                }
//                try {
//                    GetAllTransactionResponse getAllTransactionResponse = response.body();
//                    int length = getAllTransactionResponse.getResponse().items.size();
//                    Log.i(TAG, "onResponse: length" + length);
//                    transactionDataAll = new ArrayList<>();
//                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
//                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
//                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
//                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                    if (length != 0) {
//                        for (int i = 0; i < item2ArrayList.size(); i++) {
//                            if (item2ArrayList.get(i).getStandardId() == -1) {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            } else {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(), item2ArrayList.get(i).getUser().getImage()));
//                            }
//                        }
//                    }
//                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
//                    buildTransactionCard();
//                } catch (
//                        Exception e) {
//                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
//
//            }
//        });
//    }

//    private void todaypaymentResponse() {
//        Map<String, String> params = new HashMap<>();
//        params.put("allRecords", "true");
//        params.put("status", "paidAndRefunded");
//        params.put("filter", "today");
//        params.put("mode", paymentmode);
//
//        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.paidTransactions(params);
//        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
//            @Override
//            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
//
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                    Log.i("TAG", "onResponse: " + response.code());
//                }
//                try {
//                    GetAllTransactionResponse getAllTransactionResponse = response.body();
//                    int length = getAllTransactionResponse.getResponse().items.size();
//                    Log.i(TAG, "onResponse: length" + length);
//                    transactionDataAll = new ArrayList<>();
//                    ArrayList<Item2> item2ArrayList = getAllTransactionResponse.getResponse().getItems();
//                    Log.i(TAG, "onResponse: size" + item2ArrayList.size());
//                    Log.i(TAG, "onResponse:Standard " + item2ArrayList.get(0).getUser().getStudent().getStandardId());
//                    //Log.i(TAG, "onResponse: std"+getAllTra.get(0).getUser().getStudent().getStandard().getStd());
//                    if (length != 0) {
//                        for (int i = 0; i < item2ArrayList.size(); i++) {
//                            if (item2ArrayList.get(i).getStandardId() == -1) {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(),item2ArrayList.get(i).getUser().getImage()));
//                            } else {
//                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type(),item2ArrayList.get(i).getUser().getImage()));
//                            }
//                        }
//                    }
//                    Log.i("TAG", "onResponse:allActivitesTwoModalsize " + transactionDataAll.size());
//                    buildTransactionCard(transactionDataAll);
//                } catch (
//                        Exception e) {
//                    Log.i(TAG, "onResponse:Catch " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void buildTransactionCard(ArrayList<TransactionData> transactionData) {
        rvTransaction.setHasFixedSize(true);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionAdapter = new TransactionAdapter(transactionData, getContext());
        rvTransaction.setAdapter(transactionAdapter);
        transactionAdapter.setOnItemClickListener(new TransactionAdapter.OnItemClickListener() {
            @Override
            public void sendPosition(int position) {
                navController = NavHostFragment.findNavController(Transaction.this);
                String invoiceUrl = transactionDataAll.get(position).getInvoice();
                Log.i(TAG, "sendPosition: invoice----->" + invoiceUrl);
                NavDirections action = TransactionDirections.actionTransactionToInvoiceFragment(invoiceUrl);
                navController.navigate(action);
            }
        });
//        for (int i = 0; i < transactionDatas.size(); i++) {
//            Log.i(TAG, "buildTransactionCard: " + transactionDatas.get(i).getPaymenttype() + " " + i);
//
//        }

    }


}