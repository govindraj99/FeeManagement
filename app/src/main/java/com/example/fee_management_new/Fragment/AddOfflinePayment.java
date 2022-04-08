package com.example.fee_management_new.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.example.fee_management_new.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOfflinePayment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddOfflinePayment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int transactionIdToSend,userId;

    private DatePickerDialog datePickerDialog;

    private View offlinePaymentView;
    private ApiService apiService;

    private AutoCompleteTextView selectClass, searchName, amount, description, dateET;
    private Button cashBtn, debitCreditBtn, upiBtn, bankTransferBtn, chequeBtn, addBtn;
    private EditText transactionId;
    private CheckBox checkBox;

    private ArrayAdapter<String> selectClassAdapter, nameSearchAdapter, descriptionAdapter;

    private ArrayList<Integer> stdIds, userIds, transactionIDs;
    private ArrayList<Float> amounts, feeAmounts, additionAmounts, discountAmounts, totalPayableAmounts;

    private TextView feeAmtTV, discountAmountTV, additionAmountTV, payableAmtTV;
    private LinearLayout transactionLayout;

    private int stdId;
    private String paymentMethod;

    private boolean isNewPayment;

    public AddOfflinePayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOfflinePayment.
     */
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
        selectClass.showDropDown();
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
        searchName.setDropDownHeight(600);
        searchName.showDropDown();
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
                                discountAmounts.add(Float.valueOf(t.getTotal_discount()));
                                additionAmounts.add(Float.valueOf(t.getTotal_addition()));
                                transactionIDs.add(t.getId());
                            }
                            Log.i(TAG, "onResponse:tryDescriptionsSize " + descriptions.size());
                            Log.i(TAG, "onResponse:tryAmountsSize " + amounts.size());
                            Toast.makeText(getContext(), "" + amounts.size(), Toast.LENGTH_LONG).show();
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
        description.showDropDown();
        description.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                amount.setText(amounts.get(i).toString());
                feeAmtTV.setText(feeAmounts.get(i).toString());
                discountAmountTV.setText(discountAmounts.get(i).toString());
                additionAmountTV.setText(additionAmounts.get(i).toString());
                payableAmtTV.setText(totalPayableAmounts.get(i).toString());
                transactionIdToSend = transactionIDs.get(i);
            }
        });
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
                    discountAmountTV.setText("ADD");
                    additionAmountTV.setText("ADD");

                    discountAmountTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //code to add discount details
                            Toast.makeText(getContext(), "Discount Clicked", Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onClick:Discount Clicked ");
                        }
                    });
                    additionAmountTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //code to add addition details
                            Toast.makeText(getContext(), "Addition Clicked", Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onClick:Addition Clicked ");
                        }
                    });
                }else {
                  discountAmountTV.setOnClickListener(null);
                  additionAmountTV.setOnClickListener(null);
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
                userIds.add(userId);

                double amtPayable = Double.parseDouble(amount.getText().toString());
                double discountAmt = 0.0d;
                double additionAmt = 0.0d;
                double feeAmt = amtPayable+discountAmt-additionAmt;
                double totalAmt = amtPayable+additionAmt-discountAmt;
                payableAmtTV.setText(String.valueOf(totalAmt));
                feeAmtTV.setText(String.valueOf(feeAmt));
                if (isNewPayment){
                    //Toast.makeText(getContext(), "Is New Payment", Toast.LENGTH_SHORT).show();
                    GenerateNewOfflinePaymentRequest generateNewOfflinePaymentRequest = new GenerateNewOfflinePaymentRequest((int) totalAmt,note,"offline",paymentMethod,null,null,null,userIds,stdId);
                    Call<GenerateNewOfflinePaymentResponse> generateNewOfflinePaymentResponseCall = apiService.GENERATE_NEW_OFFLINE_PAYMENT_RESPONSE_CALL(generateNewOfflinePaymentRequest);
                    generateNewOfflinePaymentResponseCall.enqueue(new Callback<GenerateNewOfflinePaymentResponse>() {
                        @Override
                        public void onResponse(Call<GenerateNewOfflinePaymentResponse> call, Response<GenerateNewOfflinePaymentResponse> response) {
                            if (!response.isSuccessful()){
                                Log.i(TAG, "onResponse:add new offline payment "+response.code());
                                Toast.makeText(getContext(), "Add new offline payment "+response.code(), Toast.LENGTH_LONG).show();
                            }
                            GenerateNewOfflinePaymentResponse generateNewOfflinePaymentResponse = response.body();
                            Log.i(TAG, "onResponse:add new offline payment "+generateNewOfflinePaymentResponse.getShow().getType());
                            Toast.makeText(getContext(), "Add new offline payment "+generateNewOfflinePaymentResponse.getShow().getType(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<GenerateNewOfflinePaymentResponse> call, Throwable t) {
                            Log.i(TAG, "onFailure:Add new offline Payment "+t.getMessage());
                            Toast.makeText(getContext(), "Add new offline payment "+t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }else {
                    //Toast.makeText(getContext(), "Not New Payment", Toast.LENGTH_SHORT).show();
                    UpdateOfflineTransactionRequest updateOfflineTransactionRequest = new UpdateOfflineTransactionRequest(transactionIdToSend, paymentMethod, chequeNo, paymentDate);
                    Call<UpdateOfflineTransactionResponse> updateOfflineTransactionResponseCall = apiService.UPDATE_OFFLINE_TRANSACTION_RESPONSE_CALL(updateOfflineTransactionRequest);
                    updateOfflineTransactionResponseCall.enqueue(new Callback<UpdateOfflineTransactionResponse>() {
                        @Override
                        public void onResponse(Call<UpdateOfflineTransactionResponse> call, Response<UpdateOfflineTransactionResponse> response) {
                            if (!response.isSuccessful()) {
                                Log.i(TAG, "onResponse:notSuccessful " + response.code());
                                Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                            }
                            UpdateOfflineTransactionResponse updateOfflineTransactionResponse = response.body();
                            Log.i(TAG, "onResponse:successful " + updateOfflineTransactionResponse.getShow().getType());
                            Toast.makeText(getContext(), "" + updateOfflineTransactionResponse.getShow().getType(), Toast.LENGTH_LONG).show();
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
        return offlinePaymentView;
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
        discountAmountTV = offlinePaymentView.findViewById(R.id.totalDiscountTV);
        additionAmountTV = offlinePaymentView.findViewById(R.id.totalAdditionTV);
        payableAmtTV = offlinePaymentView.findViewById(R.id.totalPayableTV);
        transactionLayout = offlinePaymentView.findViewById(R.id.transactionLayout);
        dateET = offlinePaymentView.findViewById(R.id.dateET);
        addBtn = offlinePaymentView.findViewById(R.id.send_request_btn);
        checkBox = offlinePaymentView.findViewById(R.id.createNewPaymentCB);
    }
}