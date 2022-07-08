package com.example.fee_management_new.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.GeneratePaymentsNamesAdapter;
import com.example.fee_management_new.Adapters.GprtAdditionAdapter;
import com.example.fee_management_new.Adapters.GprtDiscountAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.RequestGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Api.ResponseGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Bottomsheets.GprtCalenderBottomSheet;
import com.example.fee_management_new.DiscountRoomDatabase.AddtionDetailAdapter;
import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeneratePaymentRequestTwoFragment extends Fragment implements GprtCalenderBottomSheet.CalendarBottomsheetListener {
    View view;
    ArrayList<String> nameslist;
    ArrayList<Integer> userID;
    AlertDialog.Builder builder;
    NavController navController;
    ArrayList<DiscountdataModel> discountdataModels = new ArrayList<>();
    ArrayList<AdditionDataModalClass> additionDataModals = new ArrayList<>();
    AlertDialog alertDialog;
    private RecyclerView generateNameRecView, discountGprt_RV, additionGprt_RV;
    private RecyclerView.LayoutManager layoutManager;
    private GeneratePaymentsNamesAdapter adapter;
    private GprtDiscountAdapter discountGprtAdapter;
    String discountAmount;
    String discountTitle;
    ApiService apiService;
    String description;
    String additionTitle;
    String additionAmount;
    String addition_description;
    private double gst;
    String duedateToSend, discountToSend, additionToSend, paymentTypeToSend = "online", duedatetosend;
    double amtToSend;
    String noteToSend;
    int stdIdToSend;
    GprtAdditionAdapter gprtAdditionAdapter;
    TextInputEditText descriptionET, additionDescription, amountET, duedate, note;
    TextView gprt_addtv, gprtAddtionAddtv, feeAmountTV, totalPayable;
    double discountTotalAmt = 0.0;
    double additionTotalAmt = 0.0;
    private Button sendRequestBtn;

    LinearLayoutManager linearLayoutManager;


    private static final String TAG = "GeneratePaymentRequestT";

    public GeneratePaymentRequestTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInt();
    }

    private void apiInt() {
        apiService = ApiClient.getLoginService();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = amountET.getText().toString().trim();
            String due_date = duedate.getText().toString().trim();
            String noteET = note.getText().toString().trim();
            sendRequestBtn.setEnabled(!amount.isEmpty() && !due_date.isEmpty() && !noteET.isEmpty());

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = amountET.getText().toString().trim();
            String due_date = duedate.getText().toString().trim();
            String noteET = note.getText().toString().trim();
            feeAmountTV.setText(amount);
            totalPayable.setText(amount);
            sendRequestBtn.setEnabled(!amount.isEmpty() && !due_date.isEmpty() && !noteET.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String amount = amountET.getText().toString().trim();
            String due_date = duedate.getText().toString().trim();
            String noteET = note.getText().toString().trim();
            sendRequestBtn.setEnabled(!amount.isEmpty() && !due_date.isEmpty() && !noteET.isEmpty());

        }
    };
    private TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = amountET.getText().toString().trim();

            sendRequestBtn.setEnabled(!amount.isEmpty());

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = amountET.getText().toString().trim();
            gst = (Double.parseDouble(amount) * 18 / 100);
            feeAmountTV.setText(amount);
            totalPayable.setText(String.valueOf(Double.parseDouble(amount) + gst));
            sendRequestBtn.setEnabled(!amount.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String amount = amountET.getText().toString().trim();

            sendRequestBtn.setEnabled(!amount.isEmpty());

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_generate_payment_request_two, container, false);
        initWidgets();

        Bundle bundle = this.getArguments();
        nameslist = bundle != null ? bundle.getStringArrayList("namesArray") : null;
        userID = bundle!= null ? bundle.getIntegerArrayList("userid") : null;
        stdIdToSend =bundle!= null ? bundle.getInt("standardId") : null;
        gprtAddtionAddtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additiondialogSheet();
            }
        });
        gprt_addtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountdialogSheet();

            }
        });
        builRecyclerView();
        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GprtCalenderBottomSheet gprtCalenderBottomSheet = new GprtCalenderBottomSheet();
                gprtCalenderBottomSheet.show(getParentFragmentManager(), "GprtCalenderBottomSheet");
                gprtCalenderBottomSheet.setTargetFragment(GeneratePaymentRequestTwoFragment.this, 2);
            }
        });
        amountET.addTextChangedListener(watcher2);
        note.addTextChangedListener(watcher);
        duedate.addTextChangedListener(watcher);


        return view;
    }

    private void additiondialogSheet() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.addition_detail_dialog, null);
        discountpopup.setClipToOutline(true);
        Button addition_add = discountpopup.findViewById(R.id.addition_aadbtn);
        EditText addition_title = discountpopup.findViewById(R.id.addition_detailET);
        EditText addition_Amount = discountpopup.findViewById(R.id.additionAmount_ET);
        Button add_cancelbtn = discountpopup.findViewById(R.id.addcancel_btn);
        additionDescription = discountpopup.findViewById(R.id.addition_description_ET);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                additionTitle = addition_title.getText().toString();
                additionAmount = addition_Amount.getText().toString();
                addition_add.setEnabled(!additionTitle.isEmpty() && !additionAmount.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                additionTitle = addition_title.getText().toString();
                additionAmount = addition_Amount.getText().toString();
                addition_add.setEnabled(!additionTitle.isEmpty() && !additionAmount.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                additionTitle = addition_title.getText().toString();
                additionAmount = addition_Amount.getText().toString();
                if (additionTitle.isEmpty()) {
                    addition_title.setError("Addition title is required");
//                    addition_Amount.setError("Amount is required");
                }
                if (addition_Amount.isFocused() && additionAmount.isEmpty()) {
                    addition_Amount.setError("Amount is required");
                }
                addition_add.setEnabled(!additionTitle.isEmpty() && !additionAmount.isEmpty());
            }
        };
        addition_Amount.addTextChangedListener(textWatcher);
        addition_title.addTextChangedListener(textWatcher);
        add_cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        addition_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additionTitle = addition_title.getText().toString();
                additionAmount = addition_Amount.getText().toString();
                addition_description = additionDescription.getText().toString();
//                feeAmountTV.setText(String.valueOf(feeAmount));
                additionDataModals.add(new AdditionDataModalClass(additionTitle, additionAmount, addition_description));
                buildAdditionDatarv();
                additionTotalAmt = additionTotalAmt + Double.parseDouble(additionAmount);
                double amttoPay = Double.parseDouble(amountET.getText().toString());
//                double feeAmount = amttoPay + discountTotalAmt - additionTotalAmt;
                double totalAmount = amttoPay + additionTotalAmt - discountTotalAmt + gst;
                totalPayable.setText(String.valueOf(totalAmount));
                closekeyboard();
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();

    }

    private void buildAdditionDatarv() {
        additionGprt_RV.setHasFixedSize(true);
        additionGprt_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        gprtAdditionAdapter = new GprtAdditionAdapter(additionDataModals, getContext());
        additionGprt_RV.setAdapter(gprtAdditionAdapter);
        gprtAdditionAdapter.setOnItemClickListener(new GprtAdditionAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double amount) {
                additionDataModals.remove(position);
                gprtAdditionAdapter.notifyItemRemoved(position);
                buildAdditionDatarv();
//                gpro_additionAmount = addition_AmountET.getText().toString();
                additionTotalAmt = additionTotalAmt - amount;

                String totalAmt = totalPayable.getText().toString();
                double total_amt = Double.parseDouble(totalAmt) - amount;
                totalPayable.setText(String.valueOf(total_amt));
            }
        });
    }

    private void discountdialogSheet() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.fragment_discountdialoug, null);
        discountpopup.setClipToOutline(true);
        EditText discount_title = discountpopup.findViewById(R.id.discount_detailET);
        EditText discount_Amount = discountpopup.findViewById(R.id.discountAmount_ET);
        Button dicount_cancelbtn = discountpopup.findViewById(R.id.cancel_btn);
        descriptionET = discountpopup.findViewById(R.id.description_ET);
        Button dialog_add = discountpopup.findViewById(R.id.aadbtn);
        TextWatcher textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountTitle = discount_title.getText().toString();
                discountAmount = discount_Amount.getText().toString();
                dialog_add.setEnabled(!discountTitle.isEmpty() && !discountAmount.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountTitle = discount_title.getText().toString();
                discountAmount = discount_Amount.getText().toString();
                dialog_add.setEnabled(!discountTitle.isEmpty() && !discountAmount.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                discountTitle = discount_title.getText().toString();
                discountAmount = discount_Amount.getText().toString();
                dialog_add.setEnabled(!discountTitle.isEmpty() && !discountAmount.isEmpty());
                if (discountTitle.isEmpty()) {
                    discount_title.setError("Discount detail is required");
                }
                if (discount_Amount.isFocused() && !discountAmount.isEmpty()) {
                    discount_Amount.setError("Discount amount is required");
                }
            }
        };
        discount_title.addTextChangedListener(textWatcher2);
        discount_Amount.addTextChangedListener(textWatcher2);
        dicount_cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                discountTitle = discount_title.getText().toString();
                discountAmount = discount_Amount.getText().toString();
                description = descriptionET.getText().toString();
                discountTotalAmt = discountTotalAmt + Double.parseDouble(discountAmount);


                discountdataModels.add(new DiscountdataModel(discountTitle, discountAmount, description));
                buildGprtdiscountdata_RV();
                double amttoPay = Double.parseDouble(amountET.getText().toString());
//                double feeAmount = amttoPay + discountTotalAmt - additionTotalAmt;
                double totalAmount = amttoPay + additionTotalAmt - discountTotalAmt + gst;
//                feeAmountTV.setText(String.valueOf(feeAmount));
                totalPayable.setText(String.valueOf(totalAmount));
                closekeyboard();
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

            }

        });

        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void closekeyboard() {
        view = this.alertDialog.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void buildGprtdiscountdata_RV() {
        discountGprt_RV.setHasFixedSize(true);
        discountGprt_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        discountGprtAdapter = new GprtDiscountAdapter(discountdataModels, getContext());
        discountGprt_RV.setAdapter(discountGprtAdapter);
        discountGprtAdapter.setOnItemClickListener(new GprtDiscountAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double amount) {
                discountdataModels.remove(position);
                discountGprtAdapter.notifyItemRemoved(position);
                buildGprtdiscountdata_RV();
//                gpro_additionAmount = addition_AmountET.getText().toString();
                discountTotalAmt = discountTotalAmt - amount;

                String totalAmt = totalPayable.getText().toString();
                double total_amt = Double.parseDouble(totalAmt) + amount;
                totalPayable.setText(String.valueOf(total_amt));

            }
        });

    }

    private void builRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        adapter = new GeneratePaymentsNamesAdapter(nameslist);
        generateNameRecView.setHasFixedSize(true);
        generateNameRecView.setLayoutManager(layoutManager);
        generateNameRecView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GeneratePaymentsNamesAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                nameslist.remove(position);
                userID.remove(position);
                adapter.notifyItemRemoved(position);
                Log.i(TAG, "builRecyclerView: names" + nameslist);
                Log.i(TAG, "builRecyclerView:UserId " + userID);
            }
        });
    }

    private void initWidgets() {
        generateNameRecView = view.findViewById(R.id.generatenameRecyclerview);
        additionGprt_RV = view.findViewById(R.id.gprt_additionRV);
        discountGprt_RV = view.findViewById(R.id.gprt_discountRV);
        gprt_addtv = view.findViewById(R.id.gprt_add_tv);
        gprtAddtionAddtv = view.findViewById(R.id.gprt_additionRS_addtv);
        amountET = view.findViewById(R.id.gt_Amount_ET);
        duedate = view.findViewById(R.id.gt_datepick);
        note = view.findViewById(R.id.gt_description_ET);
        sendRequestBtn = view.findViewById(R.id.sendrequest_btn);
        feeAmountTV = view.findViewById(R.id.gprt_fee_amountRS);
        totalPayable = view.findViewById(R.id.gprto_totalpayment);
    }

    @Override
    public void onDueDateSelected(String selectedDate) {
        duedate.setText(selectedDate);
        duedateToSend = duedate.getText().toString();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amtToSend = Double.parseDouble(amountET.getText().toString());
                noteToSend = note.getText().toString();
                JSONArray discountArray, additionArray;
                JSONObject discount_Object, addition_Object;
                discountArray = new JSONArray();
                additionArray = new JSONArray();
                for (DiscountdataModel dm :
                        discountdataModels) {
                    discount_Object = new JSONObject();
                    try {
                        discount_Object.put("name", dm.getDiscountDetail());
                        discount_Object.put("amount", dm.getDiscountAmount());
                        discount_Object.put("details", dm.getDiscountDescription());
                        discountArray.put(discount_Object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (AdditionDataModalClass am :
                        additionDataModals) {
                    addition_Object = new JSONObject();
                    try {
                        addition_Object.put("name", am.getAdditionTitle());
                        addition_Object.put("amount", am.getAdditionAmount());
                        addition_Object.put("details", am.getAdditionDescription());
                        additionArray.put(addition_Object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                discountToSend = discountArray.toString();
                additionToSend = additionArray.toString();
                duedatetosend = duedate.getText().toString();

                SendGeneratePaymentRequestTwo();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void SendGeneratePaymentRequestTwo() {
        RequestGeneratePaymentRequestIndividual paymentRequestIndividual = new RequestGeneratePaymentRequestIndividual(amtToSend, noteToSend, paymentTypeToSend, null, discountToSend, additionToSend, duedatetosend, userID, stdIdToSend);
        Call<ResponseGeneratePaymentRequestIndividual> paymentRequestIndividualCall = apiService.GENERATE_PAYMENT_REQUEST_INDIVIDUAL_CALL(paymentRequestIndividual);
        paymentRequestIndividualCall.enqueue(new Callback<ResponseGeneratePaymentRequestIndividual>() {
            @Override
            public void onResponse(Call<ResponseGeneratePaymentRequestIndividual> call, Response<ResponseGeneratePaymentRequestIndividual> response) {
                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + response.code());
                }
                ResponseGeneratePaymentRequestIndividual responseGeneratePaymentRequestIndividual = response.body();
                try {
                    Toast.makeText(getContext(), "" + responseGeneratePaymentRequestIndividual.getShow().getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (responseGeneratePaymentRequestIndividual.getShow().getType().equals("success")) {
                        NavDirections action = GeneratePaymentRequestDirections.actionGeneratePaymentRequestToActivateFrag();
                        navController.navigate(action);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneratePaymentRequestIndividual> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}