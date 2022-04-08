package com.example.fee_management_new;

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
import com.example.fee_management_new.Api.PaymentRequestDetailsTwoResponse;
import com.example.fee_management_new.DiscountRoomDatabase.AdditionData;
import com.example.fee_management_new.DiscountRoomDatabase.AddtionDetailAdapter;
import com.example.fee_management_new.DiscountRoomDatabase.DiscountDetailAdapter;
import com.example.fee_management_new.DiscountRoomDatabase.RoomDB;
import com.example.fee_management_new.DiscountRoomDatabase.DiscountData;
import com.example.fee_management_new.DiscountRoomDatabase.RoomDBTwo;

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
    TextView pdt_Name, pdt_Std, pdt_Section, pdt_Pending, pdt_Amount, pdt_Issuedate, pdt_Duedate, pdt_Description, pdt_Feeamount, pdt_Discounttitle, pdt_Discountamount, pdt_Additiontitle, pdt_Additionamount, pdt_Totalamount;
    Button cancel;
    RecyclerView recyclerView, recyclerViewtwo;
    ArrayList<String> discountdetail;
    List<DiscountData> discountData;
    List<AdditionData> additionData;
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    RoomDBTwo roomDBTwo;
    DiscountDetailAdapter discountAdapter;
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
        uid = PaymentRequesrtDetailsTwoFragmentArgs.fromBundle(getArguments()).getUid();
        apiService = ApiClient.getLoginService();
        Log.i(TAG, "onCreateView: id--->" + uid);
        getPaymentDetailsResponse();
        database = RoomDB.getInstance(getContext());
        roomDBTwo = RoomDBTwo.getInstance(getContext());
//        additionData.clear();
//        discountData.clear();
        discountData = database.mainDao().getAlldiscountdata();
        additionData = roomDBTwo.mainDaotwo().getAlladditiondata();


        return view;
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

    }

    private void getPaymentDetailsResponse() {
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

                int amount_payable = paymentreqDetailsTwoResponse.getAmountPayable();
                int total_discount = paymentreqDetailsTwoResponse.getTotal_discount();
                int total_addition = paymentreqDetailsTwoResponse.getTotal_addition();
                String fee_amount= String.valueOf((amount_payable+total_discount)-total_addition);
                pdt_Feeamount.setText(fee_amount);
                pdt_Totalamount.setText(paymentreqDetailsTwoResponse.getAmount());



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