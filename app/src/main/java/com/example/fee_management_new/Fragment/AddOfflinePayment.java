package com.example.fee_management_new.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.AOPAdditionAdapter;
import com.example.fee_management_new.Adapters.AOPDiscountAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GenerateNewOfflinePaymentRequest;
import com.example.fee_management_new.Api.GenerateNewOfflinePaymentResponse;
import com.example.fee_management_new.Api.GetUserInAClassResponse;
import com.example.fee_management_new.Api.OverViewResponse;
import com.example.fee_management_new.Api.Standard;
import com.example.fee_management_new.Api.TransactionByAUserResponse;
import com.example.fee_management_new.Api.UpdateOfflineTransactionRequest;
import com.example.fee_management_new.Api.UpdateOfflineTransactionResponse;
import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOfflinePayment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddOfflinePayment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String discountdetail, discountamount, discountdescription, additiondetail, additionamount, additiondescription, message;
    private double total_amt_two;
    private int transactionIdToSend, userId;
    private RelativeLayout addDiscountlayout, addAdditionlayout, gstlayout;
    private DatePickerDialog datePickerDialog;
    private RecyclerView aopDiscountRV, aopAdditionRV;
    private View offlinePaymentView;
    private ApiService apiService;
    private TextInputLayout amount_afp, description_afp, date_afp;
    private AutoCompleteTextView selectClass, searchName, amount, description, dateET;
    private Button cashBtn, debitCreditBtn, upiBtn, bankTransferBtn, chequeBtn, addBtn;
    private EditText transactionId;
    private CheckBox checkBox;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private ArrayAdapter<String> selectClassAdapter, nameSearchAdapter, descriptionAdapter;

    private ArrayList<Integer> stdIds, userIds, transactionIDs;
    private ArrayList<Float> amounts, feeAmounts, totalPayableAmounts;
    private ArrayList<String> additionAmounts, discountAmounts;
    private ArrayList<DiscountdataModel> discountdataModelsthree;
    private ArrayList<AdditionDataModalClass> additionDataModalClassesthree;

    private TextView feeAmtTV, discountAmountTV_add, additionAmountTV_add, payableAmtTV, totalAmt_Tv, gsttotalamt_tv;
    private LinearLayout transactionLayout;

    private int stdId;
    private String paymentMethod;
    private AOPAdditionAdapter aopAdditionAdapter;
    private AOPDiscountAdapter aopDiscountAdapter;
    private boolean isNewPayment;
    private double discountAmtTotal = 0.0d;
    private double additionAmtTotal = 0.0d;
    private String paymentMethodttwo;
    int c = 0;

    public AddOfflinePayment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddOfflinePayment newInstance(String param1, String param2) {
        AddOfflinePayment fragment = new AddOfflinePayment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        apiInit();
        initDatePicker();
        getClassNames();
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String description_afp = description.getText().toString().trim();
            String amount_afp = amount.getText().toString().trim();
            String date_afp = dateET.getText().toString().trim();
            addBtn.setEnabled(!description_afp.isEmpty() && !amount_afp.isEmpty() && !date_afp.isEmpty());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String description_afp = description.getText().toString().trim();
            String amount_afp = amount.getText().toString().trim();
            String date_afp = dateET.getText().toString().trim();
            addBtn.setEnabled(!description_afp.isEmpty() && !amount_afp.isEmpty() && !date_afp.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String description_afp = description.getText().toString().trim();
            String amount_afp = amount.getText().toString().trim();
            String date_afp = dateET.getText().toString().trim();
            addBtn.setEnabled(!description_afp.isEmpty() && !amount_afp.isEmpty() && !date_afp.isEmpty());
        }
    };

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateET.setText(date);
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return year + "-" + month + "-" + day;
    }

    private void getClassNames() {
        Map<String, String> param = new HashMap<>();
        param.put("type", "week");
        Call<OverViewResponse> overViewResponseCall = apiService.OVER_VIEW_RESPONSE_CALL(param);
        overViewResponseCall.enqueue(new Callback<OverViewResponse>() {
            @Override
            public void onResponse(Call<OverViewResponse> call, Response<OverViewResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:notSuccessful " + response.code());
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                try {
                    OverViewResponse overViewResponse = response.body();
                    ArrayList<Standard> standardArrayList = overViewResponse.getRecentTransactions().getStandards();
                    ArrayList<String> stdSec = new ArrayList<>();
                    stdIds = new ArrayList<>();
                    for (Standard s :
                            standardArrayList) {
                        stdSec.add(s.getStd() + "-" + s.getSection());
                        stdIds.add(s.getId());
                    }
                    setClassAdapter(stdSec);

                } catch (Exception e) {
                    Log.i(TAG, "onResponse:catch " + e.getMessage());
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OverViewResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClassAdapter(ArrayList<String> stdSec) {
        selectClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stdSec);
        selectClass.setAdapter(selectClassAdapter);
        selectClass.setDropDownHeight(600);
        //selectClass.showDropDown();
        selectClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stdId = stdIds.get(i);
                Call<List<GetUserInAClassResponse>> responseCall = apiService.GET_USER_IN_A_CLASS_RESPONSE_CALL(stdId, null);
                responseCall.enqueue(new Callback<List<GetUserInAClassResponse>>() {
                    @Override
                    public void onResponse(Call<List<GetUserInAClassResponse>> call, Response<List<GetUserInAClassResponse>> response) {
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "onResponse: " + response.code());
                            Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<String> nameSearch;
                        List<GetUserInAClassResponse> getUserInAClassResponses = response.body();
                        try {
                            if (getUserInAClassResponses.size() > 0) {
                                nameSearch = new ArrayList<>();
                                userIds = new ArrayList<>();
                                for (GetUserInAClassResponse g :
                                        getUserInAClassResponses) {
                                    nameSearch.add(g.getName());
                                    userIds.add(g.getId());
                                }

                                setNameSearchAdapter(nameSearch);
                            }
                        } catch (Exception e) {
                            nameSearch = new ArrayList<>();
                            setNameSearchAdapter(nameSearch);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GetUserInAClassResponse>> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void setNameSearchAdapter(ArrayList<String> nameSearch) {
        nameSearchAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nameSearch);
        searchName.setAdapter(nameSearchAdapter);
        searchName.setThreshold(1);
        searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchName.requestFocus();
                c++;
                if (c % 2 == 0) {
                    searchName.showDropDown();
                } else {
                    searchName.dismissDropDown();
                }
            }
        });
        searchName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    searchName.showDropDown();
                }
            }
        });
        searchName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userId = userIds.get(i);
                Call<List<TransactionByAUserResponse>> call = apiService.TRANSACTION_BY_A_USER_RESPONSE_CALL(stdId, userId);
                call.enqueue(new Callback<List<TransactionByAUserResponse>>() {
                    @Override
                    public void onResponse(Call<List<TransactionByAUserResponse>> call, Response<List<TransactionByAUserResponse>> response) {
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "onResponse: " + response.code());
                            Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<String> descriptions;
                        List<TransactionByAUserResponse> transactionByAUser = response.body();
                        try {
                            descriptions = new ArrayList<>();
                            amounts = new ArrayList<>();
                            feeAmounts = new ArrayList<>();
                            additionAmounts = new ArrayList<>();
                            discountAmounts = new ArrayList<>();
                            totalPayableAmounts = new ArrayList<>();
                            transactionIDs = new ArrayList<>();
                            for (TransactionByAUserResponse t :
                                    transactionByAUser) {
                                descriptions.add(t.getNote());
                                amounts.add(Float.parseFloat(t.getAmount()));
                                totalPayableAmounts.add(Float.valueOf(String.valueOf(t.getAmountPayable())));
                                String feeAmt = String.valueOf(t.getAmountPayable() + t.getTotal_discount() - t.getTotal_addition());
                                feeAmounts.add(Float.parseFloat(feeAmt));
                                discountAmounts.add(t.discount_details);
                                additionAmounts.add(t.addition_details);
                                transactionIDs.add(t.getId());
                            }
                            Log.i(TAG, "onResponse:tryDescriptionsSize " + descriptions.size());
                            Log.i(TAG, "onResponse:tryAmountsSize " + amounts.size());
//                            Toast.makeText(getContext(), "" + amounts.size(), Toast.LENGTH_LONG).show();
                            setDescriptionAdapter(descriptions);
                        } catch (Exception e) {
                            Log.i(TAG, "onResponse:catch " + e.getMessage());
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransactionByAUserResponse>> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void setDescriptionAdapter(ArrayList<String> descriptions) {
        descriptionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, descriptions);
        description.setAdapter(descriptionAdapter);
        description.setThreshold(1);
        description.setDropDownHeight(400);
//        description.showDropDown();
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.requestFocus();
                c++;
                if (c % 2 == 0) {
                    description.showDropDown();
                } else {
                    description.dismissDropDown();
                }
            }
        });
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                   description.showDropDown();
                }
            }
        });
        description.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                amount.setText(amounts.get(i).toString());
                feeAmtTV.setText(feeAmounts.get(i).toString());
                String discountString = String.valueOf(discountAmounts.get(i));
                discountdataModelsthree = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(discountString);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject object2 = jsonArray.getJSONObject(j);
                        discountdataModelsthree.add(new DiscountdataModel(object2.getString("name"), object2.getString("amount"), object2.getString("details")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String additionString = String.valueOf(additionAmounts.get(i));
                additionDataModalClassesthree = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(additionString);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject object2 = jsonArray.getJSONObject(j);
                        additionDataModalClassesthree.add(new AdditionDataModalClass(object2.getString("name"), (object2.getString("amount")), object2.getString("details")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                buildRecyclerviewforaddition();
                buildRecyclerviewfordiscount();
//                discountAmountTV.setText(discountAmounts.get(i).toString());
//                additionAmountTV.setText(additionAmounts.get(i).toString());
                payableAmtTV.setText(totalPayableAmounts.get(i).toString());
                transactionIdToSend = transactionIDs.get(i);
            }
        });
    }

    private void buildRecyclerviewforaddition() {
        aopAdditionRV.setHasFixedSize(true);
        aopAdditionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        aopAdditionRV.setAdapter(new AOPAdditionAdapter(additionDataModalClassesthree, getContext()));
    }

    private void buildRecyclerviewfordiscount() {
        aopDiscountRV.setHasFixedSize(true);
        aopDiscountRV.setLayoutManager(new LinearLayoutManager(getContext()));
        aopDiscountRV.setAdapter(new AOPDiscountAdapter(discountdataModelsthree, getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        offlinePaymentView = inflater.inflate(R.layout.fragment_add_offline_payment, container, false);
        initWidgets();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isNewPayment = b;
                if (b) {
                    addDiscountlayout.setVisibility(View.VISIBLE);
                    addAdditionlayout.setVisibility(View.VISIBLE);
                    gstlayout.setVisibility(View.VISIBLE);
                    additionDataModalClassesthree = new ArrayList<>();
                    discountdataModelsthree = new ArrayList<>();
                    buildRecyclerviewfordiscount();
                    buildRecyclerviewforaddition();

                    amount.setText("");
                    feeAmtTV.setText("0.0");
                    payableAmtTV.setText("0.0");
                    description.setText("");
                    dateET.setText("");
                    amount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            double gst, totalamt_to_show;
                            ;

                            try {
                                String amounttoshow = amount.getText().toString().trim();
                                feeAmtTV.setText(amounttoshow);
                                total_amt_two = Double.parseDouble(amounttoshow);
                                gst = total_amt_two * 18 / 100;
                                String gstapp = String.format("%.2f", gst);
                                gsttotalamt_tv.setText(gstapp);
                                totalamt_to_show = total_amt_two + gst;
                                payableAmtTV.setText(String.valueOf(totalamt_to_show));
                            } catch (Exception e) {
                                amount.setError("Amount should not be empty100");
                                additionDataModalClassesthree = new ArrayList<>();
                                discountdataModelsthree = new ArrayList<>();
                                buildRecyclerviewfordiscount();
                                buildRecyclerviewforaddition();
                                gst = 0.0;
                                totalamt_to_show = 0.0;
                                feeAmtTV.setText("0.0");
                                gsttotalamt_tv.setText("0.0");
                                payableAmtTV.setText("0.0");
                            }


                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    discountAmountTV_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            discountdialog();
                        }
                    });
                    additionAmountTV_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            additiondialoug();
                        }
                    });

                } else {
                    addDiscountlayout.setVisibility(View.GONE);
                    addAdditionlayout.setVisibility(View.GONE);
                }

            }
        });
        dateET.setText(getTodaysDate());
        if (cashBtn.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.buttonchange).getConstantState())) {
            transactionLayout.setVisibility(View.GONE);
        }
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        selectModeofPayment();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chequeNo = transactionId.getText().toString();
                String paymentDate = dateET.getText().toString();
                //String amountToSend = payableAmtTV.getText().toString();
                String note = description.getText().toString();
                ArrayList<Integer> userIds = new ArrayList<>();
                JSONArray discountArray, additionArray;
                JSONObject discountObject, additionObject;
                discountArray = new JSONArray();
                additionArray = new JSONArray();

                for (DiscountdataModel d :
                        discountdataModelsthree) {
                    discountObject = new JSONObject();
                    try {
                        discountObject.put("name", d.getDiscountDetail());
                        discountObject.put("amount", d.getDiscountAmount());
                        discountObject.put("details", d.getDiscountDescription());
                        discountArray.put(discountObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                for (AdditionDataModalClass a :
                        additionDataModalClassesthree) {
                    additionObject = new JSONObject();
                    try {
                        additionObject.put("name", a.getAdditionTitle());
                        additionObject.put("amount", a.getAdditionAmount());
                        additionObject.put("details", a.getAdditionDescription());
                        additionArray.put(additionObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Log.i(TAG, "onClick:discount array to send " + discountArray);
                Log.i(TAG, "onClick:addition array to send " + additionArray);
                userIds.add(userId);
//                Log.i(TAG, "onClick:discount amount "+discountAmt);
//                Log.i(TAG, "onClick:addition amount "+additionAmt);
//                Log.i(TAG, "onClick:fee amount "+feeAmt);
//                Log.i(TAG, "onClick:total amount "+totalAmt);
                String discountDetails = discountArray.toString();
                String additionDetails = additionArray.toString();
//                double totalAmt = Double.parseDouble(payableAmtTV.getText().toString());
                if (isNewPayment) {
                    String paiddate = dateET.getText().toString();
                    Log.i(TAG, "onClick:date " + paiddate);

                    Toast.makeText(getContext(), "Is New Payment", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: total amt to  send ----->" + total_amt_two);
                    GenerateNewOfflinePaymentRequest generateNewOfflinePaymentRequest = new GenerateNewOfflinePaymentRequest(total_amt_two, note, "offline", paymentMethod, discountDetails, additionDetails, paiddate, userIds, stdId);
                    Call<GenerateNewOfflinePaymentResponse> generateNewOfflinePaymentResponseCall = apiService.GENERATE_NEW_OFFLINE_PAYMENT_RESPONSE_CALL(generateNewOfflinePaymentRequest);
                    generateNewOfflinePaymentResponseCall.enqueue(new Callback<GenerateNewOfflinePaymentResponse>() {
                        @Override
                        public void onResponse(Call<GenerateNewOfflinePaymentResponse> call, Response<GenerateNewOfflinePaymentResponse> response) {
                            if (!response.isSuccessful()) {
                                Log.i(TAG, "onResponse:add new offline payment " + response.code());
                                Toast.makeText(getContext(), "Add new offline payment " + response.code(), Toast.LENGTH_LONG).show();
                            }
                            GenerateNewOfflinePaymentResponse generateNewOfflinePaymentResponse = response.body();
                            Log.i(TAG, "onResponse:add new offline payment " + generateNewOfflinePaymentResponse.getShow().getType());
                            Toast.makeText(getContext(), "Add new offline payment " + generateNewOfflinePaymentResponse.getShow().getType(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<GenerateNewOfflinePaymentResponse> call, Throwable t) {
                            Log.i(TAG, "onFailure:Add new offline Payment " + t.getMessage());
                            Toast.makeText(getContext(), "Add new offline payment " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    //Toast.makeText(getContext(), "Not New Payment", Toast.LENGTH_SHORT).show();
                    if (paymentMethod != null) {
                        paymentMethodttwo = paymentMethod;


                    } else {
                        paymentMethodttwo = "Cash";
                    }
                    UpdateOfflineTransactionRequest updateOfflineTransactionRequest = new UpdateOfflineTransactionRequest(transactionIdToSend, paymentMethodttwo, chequeNo, paymentDate);
                    Call<UpdateOfflineTransactionResponse> updateOfflineTransactionResponseCall = apiService.UPDATE_OFFLINE_TRANSACTION_RESPONSE_CALL(updateOfflineTransactionRequest);
                    updateOfflineTransactionResponseCall.enqueue(new Callback<UpdateOfflineTransactionResponse>() {
                        @Override
                        public void onResponse(Call<UpdateOfflineTransactionResponse> call, Response<UpdateOfflineTransactionResponse> response) {
                            if (!response.isSuccessful()) {
                                Log.i(TAG, "onResponse:notSuccessful " + response.code());
                                Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                            }
                            UpdateOfflineTransactionResponse updateOfflineTransactionResponse = response.body();
                            try {
                                Log.i(TAG, "onResponse:successful " + updateOfflineTransactionResponse.getShow().getType());
                                Toast.makeText(getContext(), "" + updateOfflineTransactionResponse.getShow().getType(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "paymentMethod should not be empty", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UpdateOfflineTransactionResponse> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        dateET.addTextChangedListener(tw);
        description.addTextChangedListener(tw);
        amount.addTextChangedListener(tw);
        return offlinePaymentView;
    }

    private void additiondialoug() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.addition_detail_dialog, null);
        discountpopup.setClipToOutline(true);
        //additionDataModalClassesthree = new ArrayList<>();
        Button addition_add = discountpopup.findViewById(R.id.addition_aadbtn);
        EditText addition_title = discountpopup.findViewById(R.id.addition_detailET);
        EditText addition_Amount = discountpopup.findViewById(R.id.additionAmount_ET);
        TextInputEditText additionDescription = discountpopup.findViewById(R.id.addition_description_ET);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                additiondetail = addition_title.getText().toString();
                additionamount = addition_Amount.getText().toString();
                addition_add.setEnabled(!additiondetail.isEmpty() && !additionamount.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                additiondetail = addition_title.getText().toString();
                additionamount = addition_Amount.getText().toString();
                addition_add.setEnabled(!additiondetail.isEmpty() && !additionamount.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                additiondetail = addition_title.getText().toString();
                additionamount = addition_Amount.getText().toString();
                if (additiondetail.isEmpty()) {
                    addition_title.setError("Addition title is required");
//                    addition_Amount.setError("Amount is required");
                }
                if (addition_Amount.isFocused() && additionamount.isEmpty()) {
                    addition_Amount.setError("Amount is required");
                }
                addition_add.setEnabled(!additiondetail.isEmpty() && !additionamount.isEmpty());
            }
        };
        addition_title.addTextChangedListener(textWatcher);
        addition_Amount.addTextChangedListener(textWatcher);
        addition_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amtPayable = 0;
                additiondetail = addition_title.getText().toString();
                additionamount = addition_Amount.getText().toString();
                additiondescription = additionDescription.getText().toString();
                additionDataModalClassesthree.add(new AdditionDataModalClass(additiondetail, additionamount, additiondescription));
                buildCheckedadditionRv();
                additionAmtTotal = additionAmtTotal + Double.parseDouble(additionamount);
                try {
                    amtPayable = Double.parseDouble(amount.getText().toString());
                } catch (Exception e) {
                    amount.setError("Amount should not be empty");
                }

                double totalAmt = amtPayable + additionAmtTotal - discountAmtTotal;
                double gst = totalAmt * 18 / 100;
                String gstapp = String.format("%.2f", gst);
                gsttotalamt_tv.setText(gstapp);
                double total_pay = amtPayable + additionAmtTotal - discountAmtTotal + gst;
                String total_amt = String.format("%.2f", total_pay);
                total_amt_two = amtPayable + additionAmtTotal - discountAmtTotal;
                payableAmtTV.setText(total_amt);

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void buildCheckedadditionRv() {
        aopAdditionRV.setHasFixedSize(true);
        aopAdditionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        aopAdditionAdapter = new AOPAdditionAdapter(additionDataModalClassesthree, getContext());
        aopAdditionRV.setAdapter(aopAdditionAdapter);
        aopAdditionAdapter.setOnItemClickListener(new AOPAdditionAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double amount) {
                additionDataModalClassesthree.remove(position);
                aopAdditionAdapter.notifyItemRemoved(position);
                buildCheckedadditionRv();
                additionAmtTotal = additionAmtTotal - amount;
//                String amtPayable = payableAmtTV.getText().toString();
//                double totalAmt = Double.parseDouble(amtPayable) + additionAmtTotal;
                total_amt_two = total_amt_two - amount;
                double gst = total_amt_two * 18 / 100;
                double total_pay = total_amt_two + gst;
                payableAmtTV.setText(String.valueOf(total_pay));

            }
        });
    }

    private void discountdialog() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.fragment_discountdialoug, null);
        discountpopup.setClipToOutline(true);
//        discountdataModelsthree = new ArrayList<>();
        EditText discount_title = discountpopup.findViewById(R.id.discount_detailET);
        EditText discount_amount = discountpopup.findViewById(R.id.discountAmount_ET);
        TextInputEditText descriptionET = discountpopup.findViewById(R.id.description_ET);
        Button discount_add = discountpopup.findViewById(R.id.aadbtn);
        TextWatcher textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountdetail = discount_title.getText().toString();
                discountamount = discount_amount.getText().toString();
                discount_add.setEnabled(!discountdetail.isEmpty() && !discountamount.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountdetail = discount_title.getText().toString();
                discountamount = discount_amount.getText().toString();
                discount_add.setEnabled(!discountdetail.isEmpty() && !discountamount.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                discountdetail = discount_title.getText().toString();
                discountamount = discount_amount.getText().toString();
                discount_add.setEnabled(!discountdetail.isEmpty() && !discountamount.isEmpty());
                if (discountdetail.isEmpty()) {
                    discount_title.setError("Discount detail is required");
                }
                if (discount_amount.isFocused() && discountamount.isEmpty()) {
                    discount_amount.setError("Discount amount is required");
                }

            }
        };
        discount_title.addTextChangedListener(textWatcher2);
        discount_amount.addTextChangedListener(textWatcher2);
        discount_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountdetail = discount_title.getText().toString();
                discountamount = discount_amount.getText().toString();
                discountdescription = descriptionET.getText().toString();
                discountdataModelsthree.add(new DiscountdataModel(discountdetail, discountamount, discountdescription));
                buildoncheckedDiscountRV();
                discountAmtTotal = discountAmtTotal + Double.parseDouble(discountamount);
                double amtPayable = Double.parseDouble(amount.getText().toString());
                double totalAmt = amtPayable + additionAmtTotal - discountAmtTotal;
                double gst = totalAmt * 18 / 100;
                String gstapx = String.format("%.2f", gst);
                gsttotalamt_tv.setText(gstapx);
                double total_amt_topay = amtPayable + additionAmtTotal - discountAmtTotal + gst;
                String totalamt_toay = String.format("%.2f", total_amt_topay);
                total_amt_two = amtPayable + additionAmtTotal - discountAmtTotal;
                payableAmtTV.setText(totalamt_toay);
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }


    private void buildoncheckedDiscountRV() {
        aopDiscountRV.setHasFixedSize(true);
        aopDiscountRV.setLayoutManager(new LinearLayoutManager(getContext()));
        aopDiscountAdapter = new AOPDiscountAdapter(discountdataModelsthree, getContext());
        aopDiscountRV.setAdapter(aopDiscountAdapter);
        aopDiscountAdapter.setOnItemClickListener(new AOPDiscountAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double amount) {
                discountdataModelsthree.remove(position);
                aopDiscountAdapter.notifyItemRemoved(position);
                buildoncheckedDiscountRV();
                discountAmtTotal = discountAmtTotal - amount;
                total_amt_two = total_amt_two + amount;
//                String totalAmt = payableAmtTV.getText().toString();
                double gst = total_amt_two * 18 / 100;
                gsttotalamt_tv.setText(String.valueOf(gst));
                double total_amt = total_amt_two + gst;
                payableAmtTV.setText(String.valueOf(total_amt));
            }
        });
    }

    private void selectModeofPayment() {
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashBtn.setBackgroundResource(R.drawable.buttonchange);
                debitCreditBtn.setBackgroundResource(R.drawable.border4);
                upiBtn.setBackgroundResource(R.drawable.border4);
                bankTransferBtn.setBackgroundResource(R.drawable.border4);
                chequeBtn.setBackgroundResource(R.drawable.border4);
                cashBtn.setTextColor(Color.parseColor("#FFFFFF"));
                debitCreditBtn.setTextColor(Color.parseColor("#AEAEAE"));
                upiBtn.setTextColor(Color.parseColor("#AEAEAE"));
                bankTransferBtn.setTextColor(Color.parseColor("#AEAEAE"));
                chequeBtn.setTextColor(Color.parseColor("#AEAEAE"));
                if (transactionLayout.getVisibility() == View.VISIBLE) {
                    transactionLayout.setVisibility(View.GONE);
                }
                paymentMethod = "Cash";
            }
        });
        debitCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashBtn.setBackgroundResource(R.drawable.border4);
                debitCreditBtn.setBackgroundResource(R.drawable.buttonchange);
                upiBtn.setBackgroundResource(R.drawable.border4);
                bankTransferBtn.setBackgroundResource(R.drawable.border4);
                chequeBtn.setBackgroundResource(R.drawable.border4);
                cashBtn.setTextColor(Color.parseColor("#AEAEAE"));
                debitCreditBtn.setTextColor(Color.parseColor("#FFFFFF"));
                upiBtn.setTextColor(Color.parseColor("#AEAEAE"));
                bankTransferBtn.setTextColor(Color.parseColor("#AEAEAE"));
                chequeBtn.setTextColor(Color.parseColor("#AEAEAE"));
                if (transactionLayout.getVisibility() == View.GONE) {
                    transactionLayout.setVisibility(View.VISIBLE);
                }
                paymentMethod = "Debit/Credit";
            }
        });
        upiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashBtn.setBackgroundResource(R.drawable.border4);
                debitCreditBtn.setBackgroundResource(R.drawable.border4);
                upiBtn.setBackgroundResource(R.drawable.buttonchange);
                bankTransferBtn.setBackgroundResource(R.drawable.border4);
                chequeBtn.setBackgroundResource(R.drawable.border4);
                cashBtn.setTextColor(Color.parseColor("#AEAEAE"));
                debitCreditBtn.setTextColor(Color.parseColor("#AEAEAE"));
                upiBtn.setTextColor(Color.parseColor("#FFFFFF"));
                bankTransferBtn.setTextColor(Color.parseColor("#AEAEAE"));
                chequeBtn.setTextColor(Color.parseColor("#AEAEAE"));
                if (transactionLayout.getVisibility() == View.GONE) {
                    transactionLayout.setVisibility(View.VISIBLE);
                }
                paymentMethod = "UPI";
            }
        });
        bankTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashBtn.setBackgroundResource(R.drawable.border4);
                debitCreditBtn.setBackgroundResource(R.drawable.border4);
                upiBtn.setBackgroundResource(R.drawable.border4);
                bankTransferBtn.setBackgroundResource(R.drawable.buttonchange);
                chequeBtn.setBackgroundResource(R.drawable.border4);
                cashBtn.setTextColor(Color.parseColor("#AEAEAE"));
                debitCreditBtn.setTextColor(Color.parseColor("#AEAEAE"));
                upiBtn.setTextColor(Color.parseColor("#AEAEAE"));
                bankTransferBtn.setTextColor(Color.parseColor("#FFFFFF"));
                chequeBtn.setTextColor(Color.parseColor("#AEAEAE"));
                if (transactionLayout.getVisibility() == View.GONE) {
                    transactionLayout.setVisibility(View.VISIBLE);
                }
                paymentMethod = "BankTransfer";
            }
        });
        chequeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashBtn.setBackgroundResource(R.drawable.border4);
                debitCreditBtn.setBackgroundResource(R.drawable.border4);
                upiBtn.setBackgroundResource(R.drawable.border4);
                bankTransferBtn.setBackgroundResource(R.drawable.border4);
                chequeBtn.setBackgroundResource(R.drawable.buttonchange);
                cashBtn.setTextColor(Color.parseColor("#AEAEAE"));
                debitCreditBtn.setTextColor(Color.parseColor("#AEAEAE"));
                upiBtn.setTextColor(Color.parseColor("#AEAEAE"));
                bankTransferBtn.setTextColor(Color.parseColor("#AEAEAE"));
                chequeBtn.setTextColor(Color.parseColor("#FFFFFF"));
                if (transactionLayout.getVisibility() == View.GONE) {
                    transactionLayout.setVisibility(View.VISIBLE);
                }
                paymentMethod = "Cheque";
            }
        });
    }

    private void apiInit() {
        apiService = ApiClient.getLoginService();
    }

    private void initWidgets() {
        selectClass = offlinePaymentView.findViewById(R.id.selectClassET);
        searchName = offlinePaymentView.findViewById(R.id.searchName);
        amount = offlinePaymentView.findViewById(R.id.amount);
        description = offlinePaymentView.findViewById(R.id.descriptionET);
        cashBtn = offlinePaymentView.findViewById(R.id.btnCash);
        debitCreditBtn = offlinePaymentView.findViewById(R.id.btnDebitCredit);
        upiBtn = offlinePaymentView.findViewById(R.id.btnUpi);
        bankTransferBtn = offlinePaymentView.findViewById(R.id.btnBankTransfer);
        chequeBtn = offlinePaymentView.findViewById(R.id.btnCheque);
        transactionId = offlinePaymentView.findViewById(R.id.transactionIDET);
        feeAmtTV = offlinePaymentView.findViewById(R.id.feeAmtTV);
        discountAmountTV_add = offlinePaymentView.findViewById(R.id.aop_discount_add);
        additionAmountTV_add = offlinePaymentView.findViewById(R.id.aop_addition_add);
        payableAmtTV = offlinePaymentView.findViewById(R.id.aoptotalPayable_amountTV);
        transactionLayout = offlinePaymentView.findViewById(R.id.transactionLayout);
        dateET = offlinePaymentView.findViewById(R.id.dateET);
        addBtn = offlinePaymentView.findViewById(R.id.send_request_btn);
        checkBox = offlinePaymentView.findViewById(R.id.createNewPaymentCB);
        aopDiscountRV = offlinePaymentView.findViewById(R.id.aop_discount_rv);
        aopAdditionRV = offlinePaymentView.findViewById(R.id.aop_addition_rv);
        addAdditionlayout = offlinePaymentView.findViewById(R.id.addaddition_layout);
        addDiscountlayout = offlinePaymentView.findViewById(R.id.adddiscount_layout);
        amount_afp = offlinePaymentView.findViewById(R.id.Amount_Tl);
        description_afp = offlinePaymentView.findViewById(R.id.description_Tl);
        gstlayout = offlinePaymentView.findViewById(R.id.gst_layout);
        gsttotalamt_tv = offlinePaymentView.findViewById(R.id.gst_TotalAmt);
//        date_afp = offlinePaymentView.findViewById(R.id.dateET);
    }
}