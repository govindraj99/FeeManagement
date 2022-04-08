package com.example.fee_management_new.Fragment;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Api.GetUserInAClassResponse;
import com.example.fee_management_new.Api.RequestGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Api.ResponseGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Bottomsheets.CalendarBottomsheet;
import com.example.fee_management_new.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneratePaymentRequest extends Fragment implements CalendarBottomsheet.CalendarBottomsheetListener {
    private static final String TAG = "GeneratePaymentRequest";
    View view;
    AutoCompleteTextView Name_searchET, selectClass;
    ArrayList<String> stdName, stdSecName, namesForSearchEditText;
    NavController navController;
    Button Individualbtn, Send_Request_btn, classbtn;
    TextView AddTV, discounttv;
    TextInputEditText datepicker, Amount_et, Description_ET;
    LinearLayout Discount2_Layout;
    TextInputLayout datepicker2;
    TextView TotalAmount_payable, listtxt;
    EditText title, amount, DiscountAmount, Discountdetail;
    AlertDialog alertDialog;
    String duedate;
    ApiService apiService;
    AlertDialog.Builder builder;
    String discountTitle, discountAmount, discountDescription;
    ArrayAdapter adapterItems, nameSearchAdapter;
    AppCompatButton Addbtn;
    HashMap<String, ArrayList<ClassName>> listHashMap;
    private int stdIdToSend, amtToSend;
    private ArrayList<Integer> userIdsToSend;
    private String dateToSend, discountDetailToSend, additionDetailToSend, paymentTypeToSend = "online", noteToSend;


    // TODO: Rename and change types and number of parameters
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
            String nameSearch = Name_searchET.getText().toString().trim();
            String descriptionET = Description_ET.getText().toString().trim();
            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !nameSearch.isEmpty() && !descriptionET.isEmpty());

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
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
            String nameSearch = Name_searchET.getText().toString().trim();
            String descriptionET = Description_ET.getText().toString().trim();
            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !nameSearch.isEmpty() && !descriptionET.isEmpty());

        }
    };

    public GeneratePaymentRequest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInit();
        getInfo();
    }

    private void apiInit() {
        apiService = ApiClient.getLoginService();
    }

    private void initWidgets() {
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
        listtxt = view.findViewById(R.id.listTXT);
        classbtn = view.findViewById(R.id.Classbtn);
        selectClass = view.findViewById(R.id.auto_complete_txt);
    }

    @Override
    public void onDateSelected(String selectedDate) {
        datepicker.setText(selectedDate);
        dateToSend = datepicker.getText().toString();
        Log.i(TAG, "onDateSelected:dateToSend " + dateToSend);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_generate_payment_request, container, false);
        initWidgets();
        Individualbtn.setOnClickListener(new View.OnClickListener() {
            Drawable background = Individualbtn.getBackground();

            @Override

            public void onClick(View view) {
                if (Individualbtn.getText().equals("Individual")) {
                    Individualbtn.setBackgroundResource(R.drawable.buttonchange);
                    classbtn.setBackgroundResource(R.drawable.border4);
                }


            }
        });
        classbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (classbtn.getText().equals("Class")) {
                    classbtn.setBackgroundResource(R.drawable.buttonchange);
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
                discountIalogSheet();
            }
        });
        datepicker.addTextChangedListener(textWatcher);
        Amount_et.addTextChangedListener(textWatcher);
        Description_ET.addTextChangedListener(textWatcher);
        Name_searchET.addTextChangedListener(textWatcher);
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
                selectClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Name_searchET.setText("");
                        String stdAndSec = adapterView.getItemAtPosition(i).toString();
                        try {
                            String[] stdAndSecs = stdAndSec.split("-");
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
                            stdIdToSend = stdId;
                            getNamesForStd();
                        } catch (Exception e) {
                            Log.i(TAG, "onResponse: " + e.getMessage());
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }

            @Override
            public void onFailure(Call<HashMap<String, ArrayList<ClassName>>> call, Throwable t) {

            }
        });
    }

    private void getNamesForStd() {
        Call<List<GetUserInAClassResponse>> listCall = apiService.GET_USER_IN_A_CLASS_RESPONSE_CALL(stdIdToSend, "");
        listCall.enqueue(new Callback<List<GetUserInAClassResponse>>() {
            @Override
            public void onResponse(Call<List<GetUserInAClassResponse>> call, Response<List<GetUserInAClassResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.code());
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
                List<GetUserInAClassResponse> getUserInAClassResponses = response.body();
                try {
                    Log.i(TAG, "onResponse: " + getUserInAClassResponses.get(0).getName());
                    Toast.makeText(getContext(), String.valueOf(getUserInAClassResponses.get(0).getName()), Toast.LENGTH_LONG).show();
                    namesForSearchEditText = new ArrayList<>();
                    ArrayList<Integer> userIds = new ArrayList<>();
                    for (GetUserInAClassResponse g :
                            getUserInAClassResponses) {
                        namesForSearchEditText.add(g.getName());
                        userIds.add(g.getId());
                    }
                    setAdapterForNameSearch(namesForSearchEditText);
                    Name_searchET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.i(TAG, "onItemClick:stdIdTosend " + userIds.get(i));
                        }
                    });
                    Name_searchET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String search = Name_searchET.getText().toString();
                            Call<List<GetUserInAClassResponse>> responseCall = apiService.GET_USER_IN_A_CLASS_RESPONSE_CALL(stdIdToSend, search);
                            responseCall.enqueue(new Callback<List<GetUserInAClassResponse>>() {
                                @Override
                                public void onResponse(Call<List<GetUserInAClassResponse>> call, Response<List<GetUserInAClassResponse>> response) {
                                    if (!response.isSuccessful()) {
                                        Log.i(TAG, "onResponse: " + response.code());
                                        Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                                    }
                                    List<GetUserInAClassResponse> userInAClassResponses = response.body();
                                    namesForSearchEditText = new ArrayList<>();
                                    for (GetUserInAClassResponse g :
                                            userInAClassResponses) {
                                        namesForSearchEditText.add(g.getName());
                                        userIds.add(g.getId());
                                    }
                                    setAdapterForNameSearch(namesForSearchEditText);
                                    Name_searchET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            userIdsToSend = new ArrayList<>();
                                            userIdsToSend.add(userIds.get(i));
                                            Log.i(TAG, "onItemClick:stdIdTosend " + userIds.get(i));
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<List<GetUserInAClassResponse>> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetUserInAClassResponse>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapterForNameSearch(ArrayList<String> names) {
        nameSearchAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, names);
        Log.i(TAG, "size: " + this.namesForSearchEditText.size());
        Toast.makeText(getContext(), "" + this.namesForSearchEditText.size(), Toast.LENGTH_LONG).show();
        Name_searchET.setAdapter(nameSearchAdapter);
        Name_searchET.showDropDown();
        Name_searchET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name_searchET.requestFocus();
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

    private void SendGeneratePaymentRequest() {
        RequestGeneratePaymentRequestIndividual paymentRequestIndividual = new RequestGeneratePaymentRequestIndividual(amtToSend,noteToSend,paymentTypeToSend,null,discountDetailToSend,null,dateToSend,userIdsToSend,stdIdToSend);
        Call<ResponseGeneratePaymentRequestIndividual> paymentRequestIndividualCall = apiService.GENERATE_PAYMENT_REQUEST_INDIVIDUAL_CALL(paymentRequestIndividual);
        paymentRequestIndividualCall.enqueue(new Callback<ResponseGeneratePaymentRequestIndividual>() {
            @Override
            public void onResponse(Call<ResponseGeneratePaymentRequestIndividual> call, Response<ResponseGeneratePaymentRequestIndividual> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), ""+response.code(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: "+response.code());
                }
                ResponseGeneratePaymentRequestIndividual responseGeneratePaymentRequestIndividual = response.body();
                Toast.makeText(getContext(), ""+responseGeneratePaymentRequestIndividual.getShow().getMessage(), Toast.LENGTH_SHORT).show();
                try {
                    if (responseGeneratePaymentRequestIndividual.getShow().getType().equals("success")){
                        NavDirections action = GeneratePaymentRequestDirections.actionGeneratePaymentRequestToPaymentRequestDetails();
                        navController.navigate(action);
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneratePaymentRequestIndividual> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });

        Log.i(TAG, "SendGeneratePaymentRequest:amtToSend "+amtToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:noteToSend "+noteToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:paymentTypeToSend "+paymentTypeToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:discountDetailToSend "+discountDetailToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:dateToSend "+dateToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:userIdsToSend "+userIdsToSend.get(0));
        Log.i(TAG, "SendGeneratePaymentRequest:stdIdToSend "+stdIdToSend);

        //RequestGeneratePaymentRequestIndividual paymentRequestIndividual = new RequestGeneratePaymentRequestIndividual(amtToSend,noteToSend,paymentTypeToSend,null,discountDetailToSend,null,dateToSend,userIdsToSend,stdIdToSend);
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
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("name", discountDetailET);
            jsonObject.put("amount", discountAmountET);
            jsonObject.put("details", discountDescriptionET);
            discountDetailToSend = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getdata:discountDetailToSend " + discountDetailToSend);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);

        Send_Request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Send_Request_btn.isEnabled()){
                    noteToSend = Description_ET.getText().toString();
                    amtToSend = Integer.parseInt(Amount_et.getText().toString());
                    SendGeneratePaymentRequest();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}