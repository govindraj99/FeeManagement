package com.example.fee_management_new.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fee_management_new.Adapters.PrdThreeAdditionAdapter;
import com.example.fee_management_new.Adapters.PrdThreeDiscountAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.CancelRequest;
import com.example.fee_management_new.Api.CancelRequestResponse;
import com.example.fee_management_new.Api.PaymentRequestDetailsTwoResponse;
import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentRequestDetailsThreeFragment extends Fragment {
    View view;
    TextView pdt_three_Name, pdt_three_Std, pdt_three_Section, pdtPending, pdt_three_Amount, pdt_three_Issuedate, pdt_three_Duedate, pdt_three_Description, pdt_three_Feeamount,pdt_three_Totalamt,pdtpaid,pdtrefunded,pdtoverdue,pdtcancelled,processbyTv,actionTv,cancelTv,canceltextTv;
    int id;
    ImageView pdt_three_iamge;
    AppCompatButton cancelbtn;
    private static final String TAG = "PaymentRequestDetailsTh";
    ApiService apiService;
    ArrayList<DiscountdataModel> discountdataModels;
    ArrayList<AdditionDataModalClass> additionDataModalClasses;
    RecyclerView pdt_three_discountRv, pdt_three_additionRv;
    static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";

    public PaymentRequestDetailsThreeFragment() {
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
        view = inflater.inflate(R.layout.fragment_payment_request_details_three, container, false);
        apiService = ApiClient.getLoginService();
        id = PaymentRequestDetailsThreeFragmentArgs.fromBundle(getArguments()).getId();
        initWidigits();
        getPaymentRequestdetailsResponse();

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelresponse();
            }
        });

        return view;
    }

    private void cancelresponse() {
        Log.i(TAG, "cancelresponse: id--->"+id);
        CancelRequest cancelRequest = new CancelRequest(id);
        Call<CancelRequestResponse> cancelRequestResponseCall = apiService.CANCEL_REQUEST_RESPONSE_CALL(id);
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
                    }else {
                        Toast.makeText(getContext(), "unable to cancel", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.i(TAG, "onResponse: --->"+e);
                    Toast.makeText(getContext(), "" + cancelRequestResponse.getShow().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CancelRequestResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: error--->"+t);
            }
        });
    }

    private void getPaymentRequestdetailsResponse() {
        Call<PaymentRequestDetailsTwoResponse> paymentDetailsResponse = apiService.PAYMENT_REQUEST_DETAILS_TWO_RESPONSE_CALL(id);
        paymentDetailsResponse.enqueue(new Callback<PaymentRequestDetailsTwoResponse>() {
            @Override
            public void onResponse(Call<PaymentRequestDetailsTwoResponse> call, Response<PaymentRequestDetailsTwoResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }

                PaymentRequestDetailsTwoResponse paymentreqDetailsTwoResponse = response.body();
                Glide.with(view).load(baseUrlForImages+paymentreqDetailsTwoResponse.getUser().getImage()).into(pdt_three_iamge);
                pdt_three_Name.setText(paymentreqDetailsTwoResponse.getUser().getName());
                Log.i(TAG, "onResponse: name---->"+paymentreqDetailsTwoResponse.getUser().getName());
                pdt_three_Std.setText(paymentreqDetailsTwoResponse.getUser().getStudent().getStandard().getStd());
                pdt_three_Section.setText(paymentreqDetailsTwoResponse.getUser().getStudent().getStandard().getSection());
                pdt_three_Amount.setText(paymentreqDetailsTwoResponse.getAmount());
                pdt_three_Issuedate.setText(paymentreqDetailsTwoResponse.getDate());
                pdt_three_Duedate.setText(paymentreqDetailsTwoResponse.getDueDate());
                pdt_three_Description.setText(paymentreqDetailsTwoResponse.getNote().trim());
                float totaldiscount = paymentreqDetailsTwoResponse.getTotal_discount();
                float totaladdition = paymentreqDetailsTwoResponse.getTotal_addition();
                float totalamt = Float.parseFloat(paymentreqDetailsTwoResponse.getAmount());
                double feeamount = totalamt + totaldiscount - totaladdition;
                pdt_three_Feeamount.setText(String.valueOf(feeamount));
                pdt_three_Totalamt.setText(String.valueOf(paymentreqDetailsTwoResponse.getAmountPayable()));
                processbyTv.setText(new StringBuilder().append("Processing fee payable by ").append(paymentreqDetailsTwoResponse.getTransactionPaidBy()).toString());
                if (paymentreqDetailsTwoResponse.getStatus().equals("Link Sent")){

                    Log.i(TAG, "onResponse: insideif--->"+paymentreqDetailsTwoResponse.getStatus());
                    cancelbtn.setVisibility(View.VISIBLE);
                    actionTv.setVisibility(View.VISIBLE);
                    canceltextTv.setVisibility(View.VISIBLE);
                    cancelTv.setVisibility(View.VISIBLE);
                }else {
                    cancelbtn.setVisibility(View.GONE);
                    actionTv.setVisibility(View.GONE);
                    canceltextTv.setVisibility(View.GONE);
                    cancelTv.setVisibility(View.GONE);
                }
                Log.i(TAG, "onResponse: status-->"+paymentreqDetailsTwoResponse.getStatus());
                switch (paymentreqDetailsTwoResponse.getStatus()) {
                    case "Paid":
                        pdtpaid.setVisibility(View.VISIBLE);
                        pdtPending.setVisibility(View.GONE);
                        pdtcancelled.setVisibility(View.GONE);
                        pdtrefunded.setVisibility(View.GONE);
                        pdtoverdue.setVisibility(View.GONE);
                        break;
                    case "Pending":
                        pdtpaid.setVisibility(View.GONE);
                        pdtPending.setVisibility(View.VISIBLE);
                        pdtcancelled.setVisibility(View.GONE);
                        pdtrefunded.setVisibility(View.GONE);
                        pdtoverdue.setVisibility(View.GONE);
                        break;
                    case "Cancelled":
                        pdtpaid.setVisibility(View.GONE);
                        pdtPending.setVisibility(View.GONE);
                        pdtcancelled.setVisibility(View.VISIBLE);
                        pdtrefunded.setVisibility(View.GONE);
                        pdtoverdue.setVisibility(View.GONE);
                        break;
                    case "Refund":
                        pdtpaid.setVisibility(View.GONE);
                        pdtPending.setVisibility(View.GONE);
                        pdtcancelled.setVisibility(View.GONE);
                        pdtrefunded.setVisibility(View.VISIBLE);
                        pdtoverdue.setVisibility(View.GONE);
                        break;
                    case "Overdue":
                        pdtpaid.setVisibility(View.GONE);
                        pdtPending.setVisibility(View.GONE);
                        pdtcancelled.setVisibility(View.GONE);
                        pdtrefunded.setVisibility(View.GONE);
                        pdtoverdue.setVisibility(View.VISIBLE);
                        break;
                }


                String jsonString = String.valueOf(paymentreqDetailsTwoResponse.getDiscount_details());
                Log.i(TAG, "onResponse: String "+ jsonString);
                try {
                    JSONArray array = new JSONArray(jsonString);
                    discountdataModels = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String detail_d = object.getString("name");
                        String amount_d = object.getString("amount");
                        String description_d = object.getString("details");
                        Log.i(TAG, "onResponse: detail "+detail_d);
                        Log.i(TAG, "onResponse: amount "+amount_d);
                        discountdataModels.add(new DiscountdataModel(detail_d,amount_d,description_d));
                    }
                    buidRvforDiscount();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String jsonString2 = String.valueOf(paymentreqDetailsTwoResponse.getAddition_details());
                try {
                    JSONArray array = new JSONArray(jsonString2);
                    additionDataModalClasses = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String detail = object.getString("name");
                        String amount = object.getString("amount");
                        String description = object.getString("details");
                        additionDataModalClasses.add(new AdditionDataModalClass(detail,amount,description));

                    }
                    buildRvforAddition();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<PaymentRequestDetailsTwoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: error---->"+t);
            }
        });
    }

    private void buildRvforAddition() {
        pdt_three_additionRv.setHasFixedSize(true);
        pdt_three_additionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        pdt_three_additionRv.setAdapter(new PrdThreeAdditionAdapter(additionDataModalClasses,getContext()));

    }

    private void buidRvforDiscount() {
        pdt_three_discountRv.setHasFixedSize(true);
        pdt_three_discountRv.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG, "buidRvforDiscount: inside Discount rv"+ discountdataModels.get(0).getDiscountDetail());
        pdt_three_discountRv.setAdapter(new PrdThreeDiscountAdapter(discountdataModels,getContext()));

    }

    private void initWidigits() {
        pdt_three_Name = view.findViewById(R.id.pdt_three_profilename);
        pdt_three_Std = view.findViewById(R.id.pdt_three_std);
        pdt_three_discountRv = view.findViewById(R.id.pdt_three_discount_RV);
        pdt_three_additionRv = view.findViewById(R.id.pdt_three_additionDetail_RV);
        pdt_three_Section = view.findViewById(R.id.pdt_three_section);
        pdtPending = view.findViewById(R.id.pdt_three_pending);
        pdt_three_Amount = view.findViewById(R.id.pdt_three_amount);
        pdt_three_Issuedate = view.findViewById(R.id.pdt_three_issuedate);
        pdt_three_Duedate = view.findViewById(R.id.pdt_three_duedate);
        pdt_three_Description = view.findViewById(R.id.pdt_three_description);
        pdt_three_Feeamount = view.findViewById(R.id.pdt_three_feeamount);
        pdt_three_Totalamt = view.findViewById(R.id.pdt_three_total_amount);
        cancelbtn = view.findViewById(R.id.pdt_three_cancel);
        pdtpaid = view.findViewById(R.id.pdt_three_paid);
        pdtrefunded = view.findViewById(R.id.pdt_three_refund);
        pdtoverdue = view.findViewById(R.id.pdt_three_overdue);
        pdtcancelled = view.findViewById(R.id.pdt_three_cancelled);
        processbyTv = view.findViewById(R.id.pdt_processby);
        actionTv = view.findViewById(R.id.action_tv);
        canceltextTv = view.findViewById(R.id.Canceltext_TVpdt);
        cancelTv = view.findViewById(R.id.pdt_Three_cancel_tv);
        pdt_three_iamge = view.findViewById(R.id.pdt_three_image);
    }

}