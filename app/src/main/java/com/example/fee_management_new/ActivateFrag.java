package com.example.fee_management_new;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.RecyclerviewRecentActivityAdapter;
import com.example.fee_management_new.Adapters.SettlementsAdapter;
import com.example.fee_management_new.Adapters.TransactionAdapter;
import com.example.fee_management_new.Api.AllTransactionList;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.Item;
import com.example.fee_management_new.Api.OverViewResponse;
import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.example.fee_management_new.Modalclass.SettelmentModel;
import com.example.fee_management_new.Modalclass.TransactionData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivateFrag extends Fragment {
    View view;
    RecyclerView recyclerViewforActivitycard, recViewForSettelmentCard, rvTransaction;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private static final String TAG = "ActivateFrag";
    ArrayList<RecentActivitiesone> recentActivitiesoneArrayList;
    private Retrofit retrofit;
    private ApiService apiService;
    TextView totalpaymentpendingTV, PaymentPendingToday, PaymentPendingAmount, PaymentPaidTV, PaymentPaid_Amount, PaymentpaidToday, PaymentOverdue_TV, overdue_AmountTV, overdue_TodayTv, paymentCancelTV, canclled_AmountTV, canclledPaymentTodayTV, refunded_TodayTV, refund_AmountTV, payment_RefundTV, total_Payment_Requested, Total_amt, NOofRequests, Payment_request_activate,ViewAll_TV;

    String data, total_amt, payment_request;
    ArrayList<SettelmentModel> settelmentModels;

    public ActivateFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activate, container, false);

//        buildRecentActivity();
//        createSettelmentCard();
//        buildSettelmentCard();
        apiInit();
        overviewcardResponse();
        return view;
    }

    private void apiInit() {
        retrofit = ApiClient.getRetrofit();
        apiService = ApiClient.getLoginService();
    }

    private void overviewcardResponse() {
//        String startDate="2022-03-20";
//        String endDate="2022-03-26";
//        String type = "week";
        Map<String, String> param = new HashMap<>();
        Call<OverViewResponse> overViewResponseCall = apiService.OVER_VIEW_RESPONSE_CALL(param);
        overViewResponseCall.enqueue(new Callback<OverViewResponse>() {
            @Override
            public void onResponse(Call<OverViewResponse> call, Response<OverViewResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                try {
                    OverViewResponse overViewResponse = response.body();
                    ArrayList<AllTransactionList> allTransactionLists = overViewResponse.getRecentTransactions().allTransactionList;
                    ArrayList<Item> itemArrayList = overViewResponse.getSettlementLists().getItems();
                    Toast.makeText(getContext(), String.valueOf(overViewResponse.totalPaymentPending.getCount()), Toast.LENGTH_LONG).show();
                    totalpaymentpendingTV.setText(String.valueOf(overViewResponse.totalPaymentPending.getCount()));
                    PaymentPendingToday.setText(String.valueOf(overViewResponse.totalPaymentPending.getTodayCount()));
                    PaymentPendingAmount.setText("\u20B9 " + String.valueOf(overViewResponse.totalPaymentPending.getAmount()));
                    PaymentPaidTV.setText(String.valueOf(overViewResponse.totalPaymentPaid.getCount()));
                    PaymentPaid_Amount.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentPaid.getAmount())).toString());
                    PaymentpaidToday.setText(String.valueOf(overViewResponse.totalPaymentPaid.getTodayCount()));
                    overdue_TodayTv.setText(String.valueOf(overViewResponse.totalPaymentOverDue.getTodayCount()));
                    overdue_AmountTV.setText(new StringBuilder().append("\u20B9").append(String.valueOf(overViewResponse.totalPaymentOverDue.getAmount())).toString());
                    PaymentOverdue_TV.setText(String.valueOf(overViewResponse.totalPaymentOverDue.getCount()));
                    paymentCancelTV.setText(String.valueOf(overViewResponse.totalPaymentCancelled.getCount()));
                    canclled_AmountTV.setText(new StringBuilder().append("\u20B9").append(String.valueOf(overViewResponse.totalPaymentCancelled.getAmount())).toString());
                    canclledPaymentTodayTV.setText(String.valueOf(overViewResponse.totalPaymentCancelled.getTodayCount()));
                    payment_RefundTV.setText(String.valueOf(overViewResponse.totalPaymentRefunded.getCount()));
                    refunded_TodayTV.setText(String.valueOf(overViewResponse.totalPaymentRefunded.getTodayCount()));
                    refund_AmountTV.setText(new StringBuilder().append("\u20B9").append(String.valueOf(overViewResponse.totalPaymentRefunded.getAmount())).toString());
                    Payment_request_activate.setText(new StringBuilder().append(String.valueOf(overViewResponse.totalTransactionRequested.getTodayCount())).append(" payments requested today").toString());
                    Total_amt.setText(new StringBuilder().append("\u20B9").append(String.valueOf(overViewResponse.totalTransactionRequested.getAmount())).toString());
                    total_Payment_Requested.setText(String.valueOf(overViewResponse.totalTransactionRequested.getCount()));
                    //Log.i("date", itemArrayList.get(0).getOpenedDate().toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy,hh:mm aa");
                    settelmentModels = new ArrayList<>();

                    for (Item i :
                            itemArrayList) {
                        settelmentModels.add(new SettelmentModel(i.getTotal(),
                                simpleDateFormat.format(i.getOpenedDate()),
                                i.getRefNo()));
                    }
                    buildSettelmentCard();
                    recentActivitiesoneArrayList = new ArrayList<>();
                    for (AllTransactionList j : allTransactionLists) {
                        if (j.getUser() == null) {
                            recentActivitiesoneArrayList.add(new RecentActivitiesone("No data", "No data", "No data", j.getAmount(), j.getDate(), j.getStatus(), j.getNote()));

                        }else {
                            recentActivitiesoneArrayList.add(new RecentActivitiesone(j.getUser().getName(), j.getUser().getStudent().getStandard().getStd(), j.getUser().getStudent().getStandard().getSection(), j.getAmount(), j.getDate(), j.getStatus(), j.getNote()));
                        }

                    }

                    buildRecentActivity();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OverViewResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error :(", Toast.LENGTH_LONG);
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        total_Payment_Requested = view.findViewById(R.id.total_payment_requested);
//        total_AmountTV = view.findViewById(R.id.total_amt_activate);
//        payment_Request_Activate = view.findViewById(R.id.payment_request_activate)
        payment_RefundTV = view.findViewById(R.id.payment_refundtv);
        refund_AmountTV = view.findViewById(R.id.refund_amounttv);
        refunded_TodayTV = view.findViewById(R.id.refunded_todaytv);
        paymentCancelTV = view.findViewById(R.id.paymentcanclled_tv);
        canclled_AmountTV = view.findViewById(R.id.canclled_amount);
        canclledPaymentTodayTV = view.findViewById(R.id.canclled_todaytv);
        TextView Generatepayment_tv = view.findViewById(R.id.GeneratePayment_tv);
        overdue_TodayTv = view.findViewById(R.id.overdue_todaytv);
        overdue_AmountTV = view.findViewById(R.id.overdue_amount);
        PaymentOverdue_TV = view.findViewById(R.id.paymentoverdue_tv);
        totalpaymentpendingTV = view.findViewById(R.id.totalpaymentPending_Tv);
        PaymentPendingToday = view.findViewById(R.id.paymentpendingtoday);
        PaymentPendingAmount = view.findViewById(R.id.paymentPendingAmount);
        PaymentPaidTV = view.findViewById(R.id.paymentPaid_Tv);
        PaymentPaid_Amount = view.findViewById(R.id.paymentpaid_amount);
        ViewAll_TV = view.findViewById(R.id.ra_viewAll);
        PaymentpaidToday = view.findViewById(R.id.paymentpaidToday);
        TextView veiwmore_tv = view.findViewById(R.id.veiwMore_tv);
        Total_amt = view.findViewById(R.id.total_amt_activate);
        LinearLayout AddOffPayment = view.findViewById(R.id.AddOfflinepayment);
        LinearLayout Transaction_layout = view.findViewById(R.id.Transaction_layout);
        LinearLayout AllStudentLaout = view.findViewById(R.id.allStudentslayout);
        total_amt = Total_amt.getText().toString();

        Payment_request_activate = view.findViewById(R.id.payment_request_activate);
        payment_request = Payment_request_activate.getText().toString();
        data = total_Payment_Requested.getText().toString();
        NavController navController = NavHostFragment.findNavController(this);
        ViewAll_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action5 = ActivateFragDirections.actionActivateFragToAllActivityRecentFragment();
                navController.navigate(action5);
            }
        });
        AllStudentLaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action4 = ActivateFragDirections.actionActivateFragToAllStudentFragment();
                navController.navigate(action4);
            }
        });
        Transaction_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action3 = ActivateFragDirections.actionActivateFragToTransaction();
                navController.navigate(action3);
            }
        });

        veiwmore_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections action = ActivateFragDirections.actionActivateFragToOverview(data, total_amt, payment_request);
                navController.navigate(action);
            }
        });
        Generatepayment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action1 = ActivateFragDirections.actionActivateFragToGeneratePaymentRequest();
                navController.navigate(action1);
            }
        });
        AddOffPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action2 = ActivateFragDirections.actionActivateFragToAddOfflinePayment();
                navController.navigate(action2);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    private void buildSettelmentCard() {
        recViewForSettelmentCard = view.findViewById(R.id.settlement_RV);
        recViewForSettelmentCard.setHasFixedSize(true);
        recViewForSettelmentCard.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewForSettelmentCard.setAdapter(new SettlementsAdapter(settelmentModels));

    }

//    private void createSettelmentCard() {
//
//        settelmentModels.add(new SettelmentModel("\u20B9 60,000", "01/12/2021", "12:00 AM", "REF_0438151296"));
//        settelmentModels.add(new SettelmentModel("\u20B9 60,000", "02/12/2021", "11:00 AM", "REF_0438151298"));
//    }

    private void buildRecentActivity() {

        recyclerViewforActivitycard = view.findViewById(R.id.recylerforactivity);
        recyclerViewforActivitycard.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewforActivitycard.setLayoutManager(layoutManager);
        adapter = new RecyclerviewRecentActivityAdapter(recentActivitiesoneArrayList);
        recyclerViewforActivitycard.setAdapter(adapter);

    }

//    private void createRecentActivityCard() {
//
//        recentActivitiesoneArrayList = new ArrayList<>();
//        names.add("Eliza O`Conner");
//        names.add("Trix West");
//        names.add("Brandon G. Jones");
//        names.add("Eliza O`Conner");
//        names.add("Trix West");
//        names.add("Brandon G. Jones");
//
//    }

}