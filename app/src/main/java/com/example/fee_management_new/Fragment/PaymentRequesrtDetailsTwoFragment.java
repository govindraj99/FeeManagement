package com.example.fee_management_new.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.CancelRequest;
import com.example.fee_management_new.Api.CancelRequestResponse;
import com.example.fee_management_new.Api.PaymentRequestDetailsTwoResponse;
import com.example.fee_management_new.DiscountRoomDatabase.AdditionData;
import com.example.fee_management_new.DiscountRoomDatabase.AddtionDetailAdapter;
import com.example.fee_management_new.DiscountRoomDatabase.DiscountDetailAdapter;
import com.example.fee_management_new.DiscountRoomDatabase.RoomDB;
import com.example.fee_management_new.DiscountRoomDatabase.DiscountData;
import com.example.fee_management_new.DiscountRoomDatabase.RoomDBTwo;
//import com.example.fee_management_new.PaymentRequesrtDetailsTwoFragmentArgs;
import com.example.fee_management_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentRequesrtDetailsTwoFragment extends Fragment {
    View view;
    private static final String TAG = "PaymentRequesrtDetailsT";
    int uid;
    ApiService apiService;
    ImageView pdt_image;
    TextView pdt_Name, pdt_Std, pdt_Section, pdt_Pending, pdt_two_Paid, pdt_two_Refund, pdt_two_overdue, pdt_two_Cancelled, pdt_Amount, pdt_Issuedate, pdt_Duedate, pdt_Description, pdt_Feeamount, pdt_Discounttitle, pdt_Discountamount, pdt_Additiontitle, pdt_Additionamount, pdt_Totalamount, action, canceltv, two_Cancel, cancelreq;
    Button cancel;
    RecyclerView recyclerView, recyclerViewtwo;
    ArrayList<String> discountdetail;
    List<DiscountData> discountData;
    List<AdditionData> additionData;
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    RoomDBTwo roomDBTwo;
    DiscountDetailAdapter discountAdapter;
    int id;
    AddtionDetailAdapter addtionDetailAdapter;


    public PaymentRequesrtDetailsTwoFragment() {
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
        view = inflater.inflate(R.layout.fragment_payment_requesrt_details_two, container, false);
        initWidgets();
        apiService = ApiClient.getLoginService();
        uid = PaymentRequesrtDetailsTwoFragmentArgs.fromBundle(getArguments()).getUid();
        Log.i(TAG, "onCreateView: id--->" + uid);
        getPaymentDetailsResponse();
//        id = PaymentRequesrtDetailsTwoFragmentArgs.fromBundle(getArguments()).getUid();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelrequest();
            }
        });
        database = RoomDB.getInstance(getContext());
        roomDBTwo = RoomDBTwo.getInstance(getContext());
//        additionData.clear();
//        discountData.clear();
        discountData = database.mainDao().getAlldiscountdata();
        additionData = roomDBTwo.mainDaotwo().getAlladditiondata();


        return view;
    }

    private void cancelrequest() {
        CancelRequest cancelRequest = new CancelRequest(uid);
        Log.i(TAG, "cancelrequest: "+uid);
        Call<CancelRequestResponse> cancelRequestResponseCall = apiService.CANCEL_REQUEST_RESPONSE_CALL(uid);
        cancelRequestResponseCall.enqueue(new Callback<CancelRequestResponse>() {
            @Override
            public void onResponse(Call<CancelRequestResponse> call, Response<CancelRequestResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:not successful " + response.code());
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                }
                CancelRequestResponse cancelRequestResponse = response.body();
                try {
                    if (cancelRequestResponse != null) {
                        Toast.makeText(getContext(), "" + cancelRequestResponse.getShow().getType(), Toast.LENGTH_LONG).show();
                    }
//                    else {
//                        Toast.makeText(getContext(), "unable to cancel", Toast.LENGTH_SHORT).show();
//                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "" + cancelRequestResponse.getShow().getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: error-->"+e);
                }

            }

            @Override
            public void onFailure(Call<CancelRequestResponse> call, Throwable t) {

            }
        });
    }

    private void initWidgets() {
        pdt_Name = view.findViewById(R.id.pdt_profilename);
        pdt_Std = view.findViewById(R.id.pdt_std);
        recyclerView = view.findViewById(R.id.discount_RV);
        recyclerViewtwo = view.findViewById(R.id.additionDetail_RV);
        pdt_Section = view.findViewById(R.id.pdt_section);
        pdt_Pending = view.findViewById(R.id.pdt_pending);
        pdt_Amount = view.findViewById(R.id.pdt_amount);
        pdt_Issuedate = view.findViewById(R.id.pdt_issuedate);
        pdt_Duedate = view.findViewById(R.id.pdt_duedate);
        pdt_Description = view.findViewById(R.id.pdt_description);
        pdt_Feeamount = view.findViewById(R.id.pdt_feeamount);
        pdt_Discounttitle = view.findViewById(R.id.pdt_discounttitle);
        pdt_Discountamount = view.findViewById(R.id.pdt_discountamount);
        pdt_Additiontitle = view.findViewById(R.id.pdt_additiontitle);
        pdt_Additionamount = view.findViewById(R.id.pdt_additionamount);
        pdt_Totalamount = view.findViewById(R.id.pdt_total_amount);
        cancel = view.findViewById(R.id.pdt_cancel);
        recyclerView = view.findViewById(R.id.discount_RV);
        pdt_two_Cancelled = view.findViewById(R.id.pdt_two_cancelled);
        pdt_two_overdue = view.findViewById(R.id.pdt_two_overdue);
        pdt_two_Paid = view.findViewById(R.id.pdt_two_paid);
        pdt_two_Refund = view.findViewById(R.id.pdt_two_refund);
        action = view.findViewById(R.id.two_action);
        canceltv = view.findViewById(R.id.Cancel_TV);
        two_Cancel = view.findViewById(R.id.two_cancel);


    }

    private void getPaymentDetailsResponse() {
        Log.i(TAG, "getPaymentDetailsResponse: ---->"+uid);
        Call<PaymentRequestDetailsTwoResponse> paymentDetailsResponse = apiService.PAYMENT_REQUEST_DETAILS_TWO_RESPONSE_CALL(uid);
        paymentDetailsResponse.enqueue(new Callback<PaymentRequestDetailsTwoResponse>() {
            @Override
            public void onResponse(Call<PaymentRequestDetailsTwoResponse> call, Response<PaymentRequestDetailsTwoResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                PaymentRequestDetailsTwoResponse paymentreqDetailsTwoResponse = response.body();
                RoomDB roomDB = RoomDB.getInstance(getContext());
                RoomDBTwo roomDBTwo = RoomDBTwo.getInstance(getContext());
                pdt_Name.setText(paymentreqDetailsTwoResponse.getUser().getName());
                pdt_Std.setText(paymentreqDetailsTwoResponse.getUser().getStudent().getStandard().getStd());
                pdt_Section.setText(paymentreqDetailsTwoResponse.getUser().getStudent().getStandard().getSection());
                pdt_Amount.setText(paymentreqDetailsTwoResponse.getAmount());
                pdt_Issuedate.setText(paymentreqDetailsTwoResponse.getDate());
                pdt_Duedate.setText(paymentreqDetailsTwoResponse.getDueDate());
                pdt_Description.setText(paymentreqDetailsTwoResponse.getNote());

                float amount_payable = paymentreqDetailsTwoResponse.getAmountPayable();
                float total_discount = paymentreqDetailsTwoResponse.getTotal_discount();
                int total_addition = paymentreqDetailsTwoResponse.getTotal_addition();
                String fee_amount = String.valueOf((amount_payable + total_discount) - total_addition);
                pdt_Feeamount.setText(fee_amount);
                pdt_Totalamount.setText(paymentreqDetailsTwoResponse.getAmount());
                if (paymentreqDetailsTwoResponse.getStatus().equals("Link Sent")) {
                    cancel.setVisibility(View.VISIBLE);
                    action.setVisibility(View.VISIBLE);
                    canceltv.setVisibility(View.VISIBLE);
                    two_Cancel.setVisibility(View.VISIBLE);
                } else {
                    cancel.setVisibility(View.GONE);
                    action.setVisibility(View.GONE);
//                    pdt_Pending.setVisibility(View.VISIBLE);
                    canceltv.setVisibility(View.GONE);
                    two_Cancel.setVisibility(View.GONE);
                }
                Log.i(TAG, "onResponse: status-->" + paymentreqDetailsTwoResponse.getStatus());
                switch (paymentreqDetailsTwoResponse.getStatus()) {
                    case "Paid":
                        pdt_two_Paid.setVisibility(View.VISIBLE);
                        pdt_Pending.setVisibility(View.GONE);
                        pdt_two_Cancelled.setVisibility(View.GONE);
                        pdt_two_Refund.setVisibility(View.GONE);
                        pdt_two_overdue.setVisibility(View.GONE);
                        break;
                    case "Pending":
                        pdt_two_Paid.setVisibility(View.GONE);
                        pdt_Pending.setVisibility(View.VISIBLE);
                        pdt_two_Cancelled.setVisibility(View.GONE);
                        pdt_two_Refund.setVisibility(View.GONE);
                        pdt_two_overdue.setVisibility(View.GONE);
                        break;
                    case "Cancelled":
                        pdt_two_Paid.setVisibility(View.GONE);
                        pdt_Pending.setVisibility(View.GONE);
                        pdt_two_Cancelled.setVisibility(View.VISIBLE);
                        pdt_two_Refund.setVisibility(View.GONE);
                        pdt_two_overdue.setVisibility(View.GONE);
                        break;
                    case "Refund":
                        pdt_two_Paid.setVisibility(View.GONE);
                        pdt_Pending.setVisibility(View.GONE);
                        pdt_two_Cancelled.setVisibility(View.GONE);
                        pdt_two_Refund.setVisibility(View.VISIBLE);
                        pdt_two_overdue.setVisibility(View.GONE);
                        break;
                    case "Overdue":
                        pdt_two_Paid.setVisibility(View.GONE);
                        pdt_Pending.setVisibility(View.GONE);
                        pdt_two_Cancelled.setVisibility(View.GONE);
                        pdt_two_Refund.setVisibility(View.GONE);
                        pdt_two_overdue.setVisibility(View.VISIBLE);
                        break;
                }


                String jsonString = String.valueOf(paymentreqDetailsTwoResponse.getDiscount_details());
                try {
                    JSONArray array = new JSONArray(jsonString);
                    discountData = new ArrayList<>();
                    DiscountData data = new DiscountData();
                    database.mainDao().deleteAll();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Log.i(TAG, "onResponse: name" + object.getString("name"));
                        data.setDiscountdetail(object.getString("name"));
                        data.setDiscountAmount(object.getString("amount"));
                        roomDB.mainDao().insert(data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                discountData.addAll(roomDB.mainDao().getAlldiscountdata());
//                discountAdapter.notifyDataSetChanged();


                Log.i(TAG, "onResponse:siuze " + discountData.size());
                buildiscountdata_RV();

                String jsonString2 = String.valueOf(paymentreqDetailsTwoResponse.getAddition_details());
                try {
                    JSONArray jsonArray = new JSONArray(jsonString2);
                    additionData = new ArrayList<>();
                    AdditionData adata = new AdditionData();
                    Log.i(TAG, "onResponse: Length" + jsonArray.length());
                    roomDBTwo.mainDaotwo().deleteAlldata();
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject object2 = jsonArray.getJSONObject(j);
                        adata.setAdditionDetail(object2.getString("name"));
                        adata.setAdditionAmount(object2.getString("amount"));
                        roomDBTwo.mainDaotwo().insert(adata);

                    }


//                    addtionDetailAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                additionData.addAll(roomDBTwo.mainDaotwo().getAlladditiondata());

                Log.i(TAG, "onResponse: Lenth" + additionData.size());
                buildadditionData_RV();


            }

            @Override
            public void onFailure(Call<PaymentRequestDetailsTwoResponse> call, Throwable t) {

            }
        });
    }

    private void buildadditionData_RV() {
        recyclerViewtwo.setHasFixedSize(true);
        recyclerViewtwo.setLayoutManager(new LinearLayoutManager(getContext()));
        addtionDetailAdapter = new AddtionDetailAdapter(additionData, getContext());
        recyclerViewtwo.setAdapter(addtionDetailAdapter);

    }

    private void buildiscountdata_RV() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DiscountDetailAdapter(discountData, getContext()));
    }
}