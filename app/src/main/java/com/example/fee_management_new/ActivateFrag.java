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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.RecyclerviewRecentActivityAdapter;
import com.example.fee_management_new.Adapters.SettlementsAdapter;
import com.example.fee_management_new.Adapters.TransactionAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.OverViewResponse;
import com.example.fee_management_new.Modalclass.SettelmentModel;
import com.example.fee_management_new.Modalclass.TransactionData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivateFrag extends Fragment {
    View view;
    RecyclerView recyclerViewforActivitycard, recViewForSettelmentCard, rvTransaction;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<String> names;
    private Retrofit retrofit;
    private ApiService apiService;
    TextView paymentpendingTV,PaymentPendingToday,PaymentPendingAmount,PaymentPaidTV,PaymentPaidAmount,PaymentpaidToday;

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
        createRecentActivityCard();
        buildRecentActivity();
        createSettelmentCard();
        buildSettelmentCard();
        apiInit();
        OverviewcardResponse();


        return view;
    }

    private void apiInit() {
        retrofit = ApiClient.getRetrofit();
        apiService = ApiClient.getLoginService();
    }

    private void OverviewcardResponse() {
        String startDate="2022-03-20";
        String endDate="2022-03-26";
        String type = "week";
        Call<OverViewResponse> overViewResponseCall = apiService.OVER_VIEW_RESPONSE_CALL(startDate, endDate, type);
        overViewResponseCall.enqueue(new Callback<OverViewResponse>() {
            @Override
            public void onResponse(Call<OverViewResponse> call, Response<OverViewResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                OverViewResponse overViewResponse = response.body();
                paymentpendingTV.setText(String.valueOf(overViewResponse.totalPaymentPending.getCount()));
                PaymentPendingToday.setText(String.valueOf(overViewResponse.totalPaymentPending.getTodayCount()));
                PaymentPendingAmount.setText("\u20B9 "+String.valueOf(overViewResponse.totalPaymentPending.getAmount()));
                PaymentPaidTV.setText(String.valueOf(overViewResponse.totalPaymentPending.getTodayCount()));





            }

            @Override
            public void onFailure(Call<OverViewResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView Generatepayment_tv = view.findViewById(R.id.GeneratePayment_tv);
        paymentpendingTV = view.findViewById(R.id.paymentPending_Tv);
        PaymentPendingToday = view.findViewById(R.id.paymentpendingtoday);
        PaymentPendingAmount = view.findViewById(R.id.paymentPendingAmount);
        PaymentPaidTV = view.findViewById(R.id.paymentPaid_Tv);
        PaymentPendingAmount = view.findViewById(R.id.paymentPaidAmount);
        PaymentpaidToday = view.findViewById(R.id.paymentpaidToday);
        TextView veiwmore_tv = view.findViewById(R.id.veiwMore_tv);
        TextView Total_amt = view.findViewById(R.id.total_amt_activate);
        LinearLayout AddOffPayment = view.findViewById(R.id.AddOfflinepayment);
        LinearLayout Transaction_layout = view.findViewById(R.id.Transaction_layout);
        LinearLayout AllStudentLaout = view.findViewById(R.id.allStudentslayout);
        total_amt = Total_amt.getText().toString();
        TextView NOofRequests = view.findViewById(R.id.Noof_Payments_requestedTv);
        TextView Payment_request_activate = view.findViewById(R.id.payment_request_activate);
        payment_request = Payment_request_activate.getText().toString();
        data = NOofRequests.getText().toString();
        NavController navController = NavHostFragment.findNavController(this);
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

    private void createSettelmentCard() {
        settelmentModels = new ArrayList<>();
        settelmentModels.add(new SettelmentModel("\u20B9 60,000", "01/12/2021", "12:00 AM", "REF_0438151296"));
        settelmentModels.add(new SettelmentModel("\u20B9 60,000", "02/12/2021", "11:00 AM", "REF_0438151298"));
    }

    private void buildRecentActivity() {

        recyclerViewforActivitycard = view.findViewById(R.id.recylerforactivity);
        recyclerViewforActivitycard.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewforActivitycard.setLayoutManager(layoutManager);
        adapter = new RecyclerviewRecentActivityAdapter(names);
        recyclerViewforActivitycard.setAdapter(adapter);

    }

    private void createRecentActivityCard() {

        names = new ArrayList<>();
        names.add("Eliza O`Conner");
        names.add("Trix West");
        names.add("Brandon G. Jones");
        names.add("Eliza O`Conner");
        names.add("Trix West");
        names.add("Brandon G. Jones");

    }

}