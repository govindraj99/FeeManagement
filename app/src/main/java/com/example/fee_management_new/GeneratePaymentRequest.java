package com.example.fee_management_new;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Api.GetUserInAClassResponse;
import com.example.fee_management_new.Bottomsheets.CalendarBottomsheet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneratePaymentRequest extends Fragment implements CalendarBottomsheet.CalendarBottomsheetListener {
    View view;
    ArrayList<String> stdName, stdSecName;
    NavController navController;
    Button Individualbtn, Send_Request_btn;
    TextView AddTV, discounttv;
    TextInputEditText datepicker, Amount_et, Name_searchET, Description_ET;
    LinearLayout Discount2_Layout;
    TextInputLayout datepicker2;
    TextView TotalAmount_payable;
    EditText title, amount, DiscountAmount, Discountdetail;
    AlertDialog alertDialog;
    String duedate;
    ApiService apiService;
    AlertDialog.Builder builder;
    private static final String TAG = "GeneratePaymentRequest";
    String discountTitle, discountAmount, discountDescription;
    private final static String Tag = "GeneratePaymentFrag";
    String[] items = {"X", "XI", "XII"};
    AutoCompleteTextView selectClass;
    ArrayAdapter<String> adapterItems;
    AppCompatButton Addbtn;
    HashMap<String, ArrayList<ClassName>> listHashMap;


    // TODO: Rename and change types and number of parameters


    public GeneratePaymentRequest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDateSelected(String selectedDate) {
        datepicker.setText(selectedDate);
        duedate = datepicker.getText().toString();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_generate_payment_request, container, false);
        apiService = ApiClient.getLoginService();
        getInfo();
        //getnames();
        Individualbtn = view.findViewById(R.id.individualBtn);
        AddTV = view.findViewById(R.id.add_tv);
        datepicker = view.findViewById(R.id.datepick);
        Amount_et = view.findViewById(R.id.Amount_ET);
        Discount2_Layout = view.findViewById(R.id.discount2_layout);
        TotalAmount_payable = view.findViewById(R.id.total_amt_payable);
        Name_searchET = view.findViewById(R.id.name_searchET);
        Description_ET = view.findViewById(R.id.description_ET);
        Send_Request_btn = view.findViewById(R.id.send_request_btn);
        discounttv = view.findViewById(R.id.DiscountTV);
        TextView listtxt = view.findViewById(R.id.listTXT);
        Button Classbtn = view.findViewById(R.id.Classbtn);
        selectClass = view.findViewById(R.id.auto_complete_txt);
        //SendRequest

        datepicker.addTextChangedListener(textWatcher);
        Amount_et.addTextChangedListener(textWatcher);
        /*Name_searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Call<GetUserInAClassResponse> getUserInAClassResponseCall = apiService.GET_USER_IN_A_CLASS_RESPONSE_CALL();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        Description_ET.addTextChangedListener(textWatcher);


        Individualbtn.setOnClickListener(new View.OnClickListener() {
            Drawable background = Individualbtn.getBackground();

            @Override

            public void onClick(View view) {
                if (Individualbtn.getText().equals("Individual")) {
                    Individualbtn.setBackgroundResource(R.drawable.buttonchange);
                    Classbtn.setBackgroundResource(R.drawable.border4);
                }


            }
        });
        Classbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Classbtn.getText().equals("Class")) {
                    Classbtn.setBackgroundResource(R.drawable.buttonchange);
                    Individualbtn.setBackgroundResource(R.drawable.border4);
                }
            }
        });
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarBottomsheet calendarBottomsheet = new CalendarBottomsheet();
                calendarBottomsheet.show(getParentFragmentManager(), "Calendar Bottomsheet");
                calendarBottomsheet.setTargetFragment(GeneratePaymentRequest.this, 1);
            }
        });
        AddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                discountdialougFragment dialog = new discountdialougFragment();
//                dialog.setTargetFragment(GeneratePaymentRequest.this,1);
//                dialog.show(getChildFragmentManager(),"MyCustomDialog");
                discountIalogSheet();

            }
        });

        return view;

    }

    private void getInfo() {
        Call<HashMap<String, ArrayList<ClassName>>> mapCall = apiService.GetClassResponse();
        mapCall.enqueue(new Callback<HashMap<String, ArrayList<ClassName>>>() {
            @Override
            public void onResponse(Call<HashMap<String, ArrayList<ClassName>>> call, Response<HashMap<String, ArrayList<ClassName>>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.code());
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG);
                }
                listHashMap = response.body();
                Set<String> stdNames = listHashMap.keySet();
                stdName = new ArrayList<>();

                for (String s :
                        stdNames) {
                    stdName.add(s);
                }
                Collections.sort(stdName);
                stdSecName = new ArrayList<>();
                for (String s :
                        stdName) {
                    for (ClassName c :
                            listHashMap.get(s)) {
                        stdSecName.add(s + "-" + c.getSection());
                    }
                }
                Log.i(TAG, "onResponse: " + stdSecName.size());
                setAdapter();
                selectClass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String stsAndSec = selectClass.getText().toString();
                        String[] stdAndSecs = stsAndSec.split("-");
                        String std = stdAndSecs[0];
                        String sec = stdAndSecs[1];
                        int stdId = 0;
                        Log.i(TAG, "onTextChanged: " + std);
                        Log.i(TAG, "onTextChanged: " + sec);
                        for (String s :
                                stdName) {
                            if (s.equals(std)) {
                                for (ClassName c :
                                        listHashMap.get(s)) {
                                    if (c.getSection().equals(sec)) stdId = c.getId();
                                }
                            }
                        }
                        Log.i(TAG, "onTextChanged: " + stdId);
                        Call<List<GetUserInAClassResponse>> listCall = apiService.GET_USER_IN_A_CLASS_RESPONSE_CALL(stdId, "");
                        listCall.enqueue(new Callback<List<GetUserInAClassResponse>>() {
                            @Override
                            public void onResponse(Call<List<GetUserInAClassResponse>> call, Response<List<GetUserInAClassResponse>> response) {
                                if (!response.isSuccessful()) {
                                    Log.i(TAG, "onResponse: " + response.code());
                                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                                }
                                List<GetUserInAClassResponse> getUserInAClassResponses = response.body();
                                Log.i(TAG, "onResponse: " + getUserInAClassResponses.get(0).getId());
                                Toast.makeText(getContext(), String.valueOf(getUserInAClassResponses.get(0).getId()), Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<List<GetUserInAClassResponse>> call, Throwable t) {
                                Log.i(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }

            @Override
            public void onFailure(Call<HashMap<String, ArrayList<ClassName>>> call, Throwable t) {

            }
        });
    }

    private void setAdapter() {
        adapterItems = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stdSecName);
        selectClass.setAdapter(adapterItems);
        selectClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectClass.showDropDown();
                selectClass.requestFocus();
            }
        });
    }

    private void SendGeneratePaymentRequest(String discountTitle, String discountAmount, String discountDescription) {
        String amount = TotalAmount_payable.getText().toString();
        String note = Description_ET.getText().toString();
        String paymentType = "online";
        String name = this.discountTitle;
        String damount = this.discountAmount;
        String Ddetails = this.discountDescription;
        String UserIds;
        String standardid;
        Log.i(TAG, "SendGeneratePaymentRequest:duedate " + duedate);

//         String discountdetail =


//        RequestGeneratePaymentRequestIndividual = new RequestGeneratePaymentRequestIndividual()
//        Call<ResponseGeneratePaymentRequestIndividual> PaymentRequestResponseCall = apiService.GENERATE_PAYMENT_REQUEST_INDIVIDUAL_CALL();
    }

    private void discountIalogSheet() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.fragment_discountdialoug, null);
        discountpopup.setClipToOutline(true);
        DiscountAmount = discountpopup.findViewById(R.id.discountAmount_ET);
        title = discountpopup.findViewById(R.id.discount_detailET);
        Discountdetail = discountpopup.findViewById(R.id.description_ET);
        Button addbtns = discountpopup.findViewById(R.id.aadbtn);
        addbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("TAG", title.getText().toString());

                String discountDetailET = title.getText().toString();
                String discountAmountET = DiscountAmount.getText().toString();
                String discountDescription = Discountdetail.getText().toString();
                discounttv.setText(discountDetailET);
                AddTV.setText(discountAmountET);
                getdata(discountDetailET, discountAmountET, discountDescription);
                if (alertDialog.isShowing()) {
                    Discount2_Layout.setVisibility(View.VISIBLE);
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void getdata(String discountDetailET, String discountAmountET, String discountDescriptionET) {
        discountTitle = discountDetailET;
        discountAmount = discountAmountET;
        discountDescription = discountDescriptionET;
        Log.i(TAG, "getdata: " + discountTitle);
        Log.i(TAG, "getdata: " + discountAmount);
        Log.i(TAG, "getdata: " + discountDescription);
        SendGeneratePaymentRequest(discountTitle, discountAmount, discountDescription);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
            String nameSearch = Name_searchET.getText().toString().trim();
            String descriptionET = Description_ET.getText().toString().trim();
            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !nameSearch.isEmpty() && !descriptionET.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

//    public String CalenderbottomSheet() {
//
//        view = getLayoutInflater().inflate(R.layout.bottom_calendar, null);
//        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
////        bt=new BottomSheetDialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat);
//        CalendarView calendarView = view.findViewById(R.id.calander_view);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // display the selected date by using a toast
//                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
////                duedate = datepicker.getText().toString();
////
//                //duedate=(new StringBuilder().append(dayOfMonth).append("/").append(month).append("/").append(year).toString());
//                //Log.i(TAG, "onSelectedDayChange: d"+duedate);
//                datepicker.setText(new StringBuilder().append(dayOfMonth).append("/").append(month).append("/").append(year).toString());
//                bt.dismiss();
//
//            }
//        });
//        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////        view= LayoutInflater.from(context).inflate(R.layout.edit,null);
//        bt.setContentView(view);
//        bt.setCanceledOnTouchOutside(true);
////        bt.getWindow().setGravity(Gravity.BOTTOM);
//        bt.show();
//        return duedate;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);

        Send_Request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = GeneratePaymentRequestDirections.actionGeneratePaymentRequestToPaymentRequestDetails();
                navController.navigate(action);
                SendGeneratePaymentRequest(discountTitle, discountAmount, discountDescription);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}