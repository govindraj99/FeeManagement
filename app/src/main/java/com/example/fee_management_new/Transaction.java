package com.example.fee_management_new;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.TransactionAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Api.Item2;
import com.example.fee_management_new.Modalclass.TransactionData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    String paymentmode;
    CheckBox offlinepayment, onlinepayment;
    TextView today_pd,yesterdey_pd,thisMonth_pd,pastMonth_pd,pastThreeMonth_pd,customisedate_pd;


    ApiService apiService;
    NavController navController;
    LinearLayout Paymentdate_Bottomsheet, TypeofPayment_layout;


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
        rvTransaction = view.findViewById(R.id.TransactionRv);
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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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
                if (b){
                    onlinepayment.setChecked(false);
                    getOfflineResponse();
                    paymentmode = "offline";
                        }



            }
        });
        onlinepayment.setChecked(getActivity().getSharedPreferences("online", Context.MODE_PRIVATE).getBoolean("checkBox", false));
        onlinepayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                getActivity().getSharedPreferences("online", Context.MODE_PRIVATE).edit().putBoolean("checkBox", c).commit();
                if (c){
                   offlinepayment.setChecked(false);
                   getOnlineResponse();
                   paymentmode = "online";
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

    private void getOnlineResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode","online");

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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

    private void getOfflineResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode","offline");

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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
                todaypaymentResponse();
            }
        });
        yesterdey_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesterdayPaymentResponse();
            }
        });
        thisMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thismonthPaymentResponse();
            }
        });
        pastMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastMonthpdResponse();
            }
        });
        pastThreeMonth_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastThreeMothPdResponse();
            }
        });

    }

    private void pastThreeMothPdResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode",paymentmode);

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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

    private void pastMonthpdResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode",paymentmode);

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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

    private void thismonthPaymentResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode",paymentmode);

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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

    private void yesterdayPaymentResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode",paymentmode);

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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

    private void todaypaymentResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("allRecords", "true");
        params.put("status", "paidAndRefunded");
        params.put("mode",paymentmode);

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
                            if (item2ArrayList.get(i).getStandardId() == -1) {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), "no data", "no data", String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
                            } else {
                                transactionDataAll.add(new TransactionData(item2ArrayList.get(i).getUser().getName(), item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(), item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(), String.valueOf(item2ArrayList.get(i).getAmountPayable()), item2ArrayList.get(i).getPaymentDate(), item2ArrayList.get(i).getNote(), item2ArrayList.get(i).getPayment_type()));
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


    private void buildTransactionCard(ArrayList<TransactionData> transactionDatas) {
        rvTransaction.setHasFixedSize(true);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransaction.setAdapter(new TransactionAdapter(transactionDatas, getContext(), getParentFragmentManager()));
        for (int i=0;i<transactionDatas.size();i++){
            Log.i(TAG, "buildTransactionCard: "+transactionDatas.get(i).getPaymenttype()+" "+i);

        }

    }


}