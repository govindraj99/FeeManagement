package com.example.fee_management_new;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.AllStudentsIntegratedAdapter;
import com.example.fee_management_new.Adapters.IntegratedRecentActivityAdapter;
import com.example.fee_management_new.Api.AllStudent;
import com.example.fee_management_new.Api.AllTransactionList;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.OverViewResponse;
import com.example.fee_management_new.Modalclass.AllStudentsIntegratedModal;
import com.example.fee_management_new.Modalclass.IntegratedRecentActivityModalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class IntegeratedBatchAFragment extends Fragment {

    RecyclerView recyclerViewforRecentactivity, recyclerViewforAllSudents;
    RecyclerView.LayoutManager layoutManager;
    NavController navController;
    Retrofit retrofit;
    TextView viewAll;
    int id;
    ApiService apiService;
    RecyclerView.Adapter adapter;
    ArrayList<IntegratedRecentActivityModalClass> IntegratedRAmodel;
    ArrayList<AllStudentsIntegratedModal> allStudentsIntegratedModals;
    TextView IntepaymentPendingTV, IntependingAmount, IntepaymentPending_today, IntePaymentPaid, IntePaidAmount, IntePaymentPaid_today, IntePaymentOverdue, InteOverdueAmount, IntOverdueToday, IntePaymentCancelled, InteCancelledAmount, IntePaymentCancelled_Today, Inte_PaymentReund, Inte_AmountRefund, Inte_PaymentRefund_today, InteTotalPaymentRequest, InteTotalpaymentAmount, InteTotalpaymentRequestToday;
    View view;

    //ArrayList<IntegratedRecentActivityModalClass> integratedRecentActivitylist;
    ArrayList<AllStudentsIntegratedModal> allStudentsIntegratelist;


    public IntegeratedBatchAFragment() {

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
        view = inflater.inflate(R.layout.fragment_integerated_batch_a, container, false);
        viewAll = view.findViewById(R.id.inte_viewall);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = IntegeratedBatchAFragmentDirections.actionIntegeratedBatchAFragmentToAllActivitiesFragment(id);
                Navigation.findNavController(view).navigate(action);
            }
        });
        //Payment Pending
        IntepaymentPendingTV = view.findViewById(R.id.inte_paymentPending_Tv);
        IntependingAmount = view.findViewById(R.id.Inte_PendingAmount);
        IntepaymentPending_today = view.findViewById(R.id.inte_paymentPending_today);

        //Payment Paid
        IntePaymentPaid = view.findViewById(R.id.inte_paymentpaid_tv);
        IntePaidAmount = view.findViewById(R.id.inte_paidamount_tv);
        IntePaymentPaid_today = view.findViewById(R.id.inte_paymentpaidtoday);

        //Payment overdue
        IntePaymentOverdue = view.findViewById(R.id.inte_paymentoverdue_tv);
        InteOverdueAmount = view.findViewById(R.id.inte_overdueamount);
        IntOverdueToday = view.findViewById(R.id.inte_paymentoverdue_today);

        //Payment cancelled

        IntePaymentCancelled = view.findViewById(R.id.inte_paymentcancelled);
        InteCancelledAmount = view.findViewById(R.id.inte_cancelled_amount);
        IntePaymentCancelled_Today = view.findViewById(R.id.inte_paymentcancelled_today);

        //Payment Refunded
        Inte_PaymentReund = view.findViewById(R.id.inte_paymentrefund_tv);
        Inte_AmountRefund = view.findViewById(R.id.inte_amountrefund_tv);
        Inte_PaymentRefund_today = view.findViewById(R.id.inte_paymentrefunded_today);
        //Total payment
        InteTotalPaymentRequest = view.findViewById(R.id.inte_total_payment_request);
        InteTotalpaymentAmount = view.findViewById(R.id.inte_total_payment_amount);
        InteTotalpaymentRequestToday = view.findViewById(R.id.inte_total_payment_request_today);


//        Createcard();
//        CreatecardAllStudentIntegrate();
//        buildIntegratedRecentActivityView();
//        buildAllStudentRecyclerview();
        setHasOptionsMenu(true);
        id = IntegeratedBatchAFragmentArgs.fromBundle(getArguments()).getId();
        String section = IntegeratedBatchAFragmentArgs.fromBundle(getArguments()).getSection();
        String std = IntegeratedBatchAFragmentArgs.fromBundle(getArguments()).getStd();
        Log.i("TAG", String.valueOf(id));
        apiInit();
        overviewforIntegratedRespose();

        return view;
    }


    private void overviewforIntegratedRespose() {
        Map<String, String> param = new HashMap<>();
        param.put("standardId", String.valueOf(id));
        Log.i("std", "overviewforIntegratedRespose: " + id);
        Call<OverViewResponse> overViewResponseCall = apiService.OVER_VIEW_RESPONSE_CALL(param);
        overViewResponseCall.enqueue(new Callback<OverViewResponse>() {
            @Override
            public void onResponse(Call<OverViewResponse> call, Response<OverViewResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                OverViewResponse overViewResponse = response.body();
                ArrayList<AllTransactionList> IAllTransactionLists = overViewResponse.getRecentTransactions().getAllTransactionList();
                ArrayList<AllStudent> allStudents = overViewResponse.getAllStudents();
                //payment pending
                IntepaymentPendingTV.setText(String.valueOf(overViewResponse.totalPaymentPending.getCount()));
                IntependingAmount.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentPending.getAmount())).toString());
                IntepaymentPending_today.setText(String.valueOf(overViewResponse.totalPaymentPending.getTodayCount()));

                //Payment Paid
                IntePaymentPaid.setText(String.valueOf(overViewResponse.totalPaymentPaid.getCount()));
                IntePaidAmount.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentPaid.getAmount())).toString());
                IntePaymentPaid_today.setText(String.valueOf(overViewResponse.totalPaymentPaid.getTodayCount()));

                //Payment Overdue
                IntePaymentOverdue.setText(String.valueOf(overViewResponse.totalPaymentOverDue.getCount()));
                InteOverdueAmount.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentOverDue.getAmount())).toString());
                IntOverdueToday.setText(String.valueOf(overViewResponse.totalPaymentOverDue.getTodayCount()));

                //Payment Cancelled
                IntePaymentCancelled.setText(String.valueOf(overViewResponse.totalPaymentCancelled.getCount()));
                InteCancelledAmount.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentCancelled.getAmount())).toString());
                IntePaymentCancelled_Today.setText(String.valueOf(overViewResponse.totalPaymentCancelled.getTodayCount()));

                //Payment Refunded
                Inte_PaymentReund.setText(String.valueOf(overViewResponse.totalPaymentRefunded.getCount()));
                Inte_AmountRefund.setText(new StringBuilder().append("\u20B9 ").append(String.valueOf(overViewResponse.totalPaymentRefunded.getAmount())).toString());
                Inte_PaymentRefund_today.setText(String.valueOf(overViewResponse.totalPaymentRefunded.getTodayCount()));


                //Total payment
                InteTotalPaymentRequest.setText(String.valueOf(overViewResponse.totalTransactionRequested.getCount()));
                InteTotalpaymentRequestToday.setText(new StringBuilder().append(String.valueOf(overViewResponse.totalTransactionRequested.getTodayCount())).append(" payments requested today").toString());
                InteTotalpaymentAmount.setText(new StringBuilder().append("\u20B9").append(String.valueOf(overViewResponse.totalTransactionRequested.getAmount())).toString());

                IntegratedRAmodel = new ArrayList<>();
                for (AllTransactionList i :
                        IAllTransactionLists) {
                    IntegratedRAmodel.add(new IntegratedRecentActivityModalClass(i.getUser().getName(), String.valueOf(i.getAmountPayable()), i.getDate(),i.getId()));

                }
                buildIntegratedRecentActivityView();

                allStudentsIntegratedModals = new ArrayList<>();
                for (AllStudent j :
                        allStudents) {
                    allStudentsIntegratedModals.add(new AllStudentsIntegratedModal(j.getName(), String.valueOf(j.getRollNo()), String.valueOf(j.getCount()), j.getImage()));

                }
                buildAllStudentRecyclerview();
            }

            @Override
            public void onFailure(Call<OverViewResponse> call, Throwable t) {

            }
        });
    }

    private void apiInit() {
        retrofit = ApiClient.getRetrofit();
        apiService = ApiClient.getLoginService();
    }

    private void buildAllStudentRecyclerview() {
        recyclerViewforAllSudents = view.findViewById(R.id.AllStudentsRv);
        recyclerViewforAllSudents.setHasFixedSize(true);
        recyclerViewforAllSudents.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewforAllSudents.setAdapter(new AllStudentsIntegratedAdapter(allStudentsIntegratedModals, getContext(), getParentFragmentManager()));

    }

    private void buildIntegratedRecentActivityView() {
        recyclerViewforRecentactivity = view.findViewById(R.id.integrateRv);
        recyclerViewforRecentactivity.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewforRecentactivity.setLayoutManager(layoutManager);
        recyclerViewforRecentactivity.setAdapter(new IntegratedRecentActivityAdapter(IntegratedRAmodel, getContext()));

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.integrate_gegenerate_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(getActivity(), R.id.activity_main_nav_host_fragment);

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}