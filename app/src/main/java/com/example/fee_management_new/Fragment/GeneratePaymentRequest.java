package com.example.fee_management_new.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.TooltipCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.GproAdditionAdapter;
import com.example.fee_management_new.Adapters.GproDiscountAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Api.GetUserInAClassResponse;
import com.example.fee_management_new.Api.RequestGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Api.ResponseGeneratePaymentRequestIndividual;
import com.example.fee_management_new.Bottomsheets.CalendarBottomsheet;
import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;

import com.example.fee_management_new.XYMarkerView;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAlign;
import com.skydoves.balloon.BalloonAnimation;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
    double amtpayable;
    Button Individualbtn, Send_Request_btn, classbtn;
    TextView AddTV, discounttv, gpro_addition_add, feeamount_rsTv, total_payable_rsTV, prcessing_tv;
    TextInputEditText datepicker, Amount_et, Description_ET, addition_descriptionET, nameSearchET;
    LinearLayout Discount2_Layout;
    TextInputLayout datepicker2, nameSearchlayout;
    TextView TotalAmount_payable, listtxt, studentNameTV, feePayerTV, gstAmttv;
    EditText title, amount, DiscountAmount, Discountdetail, addition_DetailET, addition_AmountET;
    AlertDialog alertDialog;
    String duedate, gpro_additionDetail, gpro_additionAmount, gpro_additionDescription, addition_details_tosend, discount_details_tosend, payableby;
    //    String uAmount;
    ApiService apiService;
    AlertDialog.Builder builder;
    String discountDetailET, discountAmountET, discountDescription, dis_amt, add_amt;
    ArrayAdapter adapterItems, nameSearchAdapter;
    ArrayList<DiscountdataModel> discountdataModelstwo = new ArrayList<>();
    ArrayList<AdditionDataModalClass> additionDataModalClassestwo = new ArrayList<>();
    RecyclerView discountdataRV, additiondataRV;
    ArrayList<Integer> wholeClassUserIdsToSend;
    String status, feepayer;
    GproAdditionAdapter gproAdditionAdapter;
    GproDiscountAdapter gproDiscountAdapter;
    AppCompatButton Addbtn;
    LinearLayout pL;
    ImageView processFee_infoImg;
    double gst;
    private double discount_AmtTotal = 0.0d;
    double total_amt_two;
    double addition_AmtTotal = 0.0d;
CoordinatorLayout c;
    HashMap<String, ArrayList<ClassName>> listHashMap;
    private int stdIdToSend;
    private double amtToSend;
    private ArrayList<Integer> userIdsIndividual;
    private ArrayList<Integer> Userarray_list_toSend;
    private String dateToSend, discountDetailToSend, additionDetailToSend, paymentTypeToSend = "online", noteToSend;
    ToolTipsManager toolTipsManager;

    // TODO: Rename and change types and number of parameters
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();
            String descriptionET = Description_ET.getText().toString().trim();
            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !descriptionET.isEmpty());

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();
            String descriptionET = Description_ET.getText().toString().trim();
            feeamount_rsTv.setText(AmountET);

            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !descriptionET.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String datepick = datepicker.getText().toString().trim();
            String AmountET = Amount_et.getText().toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();

            String descriptionET = Description_ET.getText().toString().trim();

            Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !descriptionET.isEmpty());

        }
    };
    private TextWatcher textWatcherr = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String AmountET = Amount_et.getText().toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();

            Send_Request_btn.setEnabled(!AmountET.isEmpty());

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String AmountET = Objects.requireNonNull(Amount_et.getText()).toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();

//            uAmount = Amount_et.getText().toString().trim();
            gst = (Double.parseDouble(AmountET) * 18 / 100);
            Log.i(TAG, "afterTextChanged: gst" + gst);
            gstAmttv.setText(String.valueOf(gst));
            feeamount_rsTv.setText(AmountET);
            total_payable_rsTV.setText(String.valueOf(Double.parseDouble(AmountET) + gst));
            total_amt_two = Double.parseDouble(Amount_et.getText().toString());
            Send_Request_btn.setEnabled(!AmountET.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String AmountET = Amount_et.getText().toString().trim();
//            String nameSearch = Name_searchET.getText().toString().trim();
            Send_Request_btn.setEnabled(!AmountET.isEmpty());

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
        AddTV = view.findViewById(R.id.gprto_add_tv);
        feePayerTV = view.findViewById(R.id.fee_payer_tv);
        datepicker = view.findViewById(R.id.datepick);
        Amount_et = view.findViewById(R.id.Amount_ET);
//        TotalAmount_payable = view.findViewById(R.id.total_amt_payable);
        Name_searchET = view.findViewById(R.id.name_searchET);
        Description_ET = view.findViewById(R.id.description_ET);
        Send_Request_btn = view.findViewById(R.id.send_request_btn);
//        discounttv = view.findViewById(R.id.DiscountTV);
        listtxt = view.findViewById(R.id.listTXT);
        classbtn = view.findViewById(R.id.Classbtn);
        selectClass = view.findViewById(R.id.auto_complete_txt);
        discountdataRV = view.findViewById(R.id.gprto_discountRV);
        gpro_addition_add = view.findViewById(R.id.gprto_additionRS_addtv);
        additiondataRV = view.findViewById(R.id.gprto_additionRV);
        studentNameTV = view.findViewById(R.id.studentnametv);
        nameSearchlayout = view.findViewById(R.id.name_search_layout);
        feeamount_rsTv = view.findViewById(R.id.gpro_fee_amountRS);
        total_payable_rsTV = view.findViewById(R.id.gprto_totalpayment);
        processFee_infoImg = view.findViewById(R.id.processfee_info);
        gstAmttv = view.findViewById(R.id.gst_TotalAmt);
        pL = view.findViewById(R.id.pl);
        prcessing_tv = view.findViewById(R.id.Processingfee_tv);
        c = view.findViewById(R.id.co);
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
        feepayer = GeneratePaymentRequestArgs.fromBundle(getArguments()).getFeepaidBy();
        Log.i(TAG, "onCreateView: " + feepayer);

        if (feepayer.equals("institute")) {
            feePayerTV.setText("Processing fee payable by institute");
        } else {
            feePayerTV.setText("Processing fee payable by student");
        }


        Individualbtn.setOnClickListener(new View.OnClickListener() {
            Drawable background = Individualbtn.getBackground();

            @Override

            public void onClick(View view) {
                if (Individualbtn.getText().equals("Individual")) {
                    Individualbtn.setBackgroundResource(R.drawable.buttonchange);
                    classbtn.setBackgroundResource(R.drawable.border4);
                    studentNameTV.setVisibility(View.VISIBLE);
                    nameSearchlayout.setVisibility(View.VISIBLE);
                    Name_searchET.setVisibility(View.VISIBLE);
                    status = "Individual";

                }


            }
        });
        classbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (classbtn.getText().equals("Class")) {
                    classbtn.setBackgroundResource(R.drawable.buttonchange);
                    Individualbtn.setBackgroundResource(R.drawable.border4);
                    studentNameTV.setVisibility(View.GONE);
                    nameSearchlayout.setVisibility(View.GONE);
                    Name_searchET.setVisibility(View.GONE);
                    status = "class";
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
        gpro_addition_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additionDialogsheet();
            }
        });
        datepicker.addTextChangedListener(textWatcher);
        Amount_et.addTextChangedListener(textWatcherr);
        Description_ET.addTextChangedListener(textWatcher);
        String AmountET = Amount_et.getText().toString();
        total_payable_rsTV.setText(AmountET);
        toolTipsManager = new ToolTipsManager();
//        Name_searchET.addTextChangedListener(textWatcher);

        processFee_infoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());
                int position = ToolTip.POSITION_RIGHT_TO;
                String ms =  "Upi   free"
                        + System.getProperty("line.separator")
                        + "Debit and Credit card   1.8%"
                        + System.getProperty("line.separator");

                ToolTip.Builder builder = new ToolTip.Builder(getContext(),processFee_infoImg,c,ms,position);
                builder.setAlign(ToolTip.ALIGN_CENTER);
                builder.setBackgroundColor(Color.BLACK);
                toolTipsManager.show(builder.build());

//                final View discountpopup = getLayoutInflater().inflate(R.layout.processfee_info, null);
//                alertDialog.getContext().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                XYMarkerView mv = new XYMarkerView(getContext(), new IndexAxisValueFormatter());
//                mv.setChartView(pL); // For bounds control
//                mChart.setMarker(mv);

//                discountpopup.setClipToOutline(true);
//                builder.setView(discountpopup);
//                alertDialog = builder.create();
//                alertDialog.show();

//                Balloon balloon = new Balloon.Builder(getContext())
//                        .setLayout(R.layout.processfee_info)
//                        .setArrowSize(10)
//                        .setArrowOrientation(ArrowOrientation.TOP)
//                        .setArrowPosition(0.5f)
//                        .setWidthRatio(0.55f)
//                        .setHeight(250)
//                        .setCornerRadius(4f)
//                        .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black))
//                        .setBalloonAnimation(BalloonAnimation.CIRCULAR)
//                        .setLifecycleOwner(getViewLifecycleOwner())
//                        .setAutoDismissDuration(1000L)
//                        .build();
//                processFee_infoImg.showAlignTop()


            }

        });

        return view;

    }


    private void additionDialogsheet() {
        builder = new AlertDialog.Builder(getContext());
        final View discountpopup = getLayoutInflater().inflate(R.layout.addition_detail_dialog, null);
        discountpopup.setClipToOutline(true);
        addition_DetailET = discountpopup.findViewById(R.id.addition_detailET);
        addition_AmountET = discountpopup.findViewById(R.id.additionAmount_ET);
        addition_descriptionET = discountpopup.findViewById(R.id.addition_description_ET);
        Button addition_Addbtn = discountpopup.findViewById(R.id.addition_aadbtn);
        Button add_cancel_btn = discountpopup.findViewById(R.id.addcancel_btn);
        TextWatcher textWatcher4 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gpro_additionDetail = addition_DetailET.getText().toString();
                gpro_additionAmount = addition_AmountET.getText().toString();
                addition_Addbtn.setEnabled(!gpro_additionDetail.isEmpty() && !gpro_additionAmount.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gpro_additionDetail = addition_DetailET.getText().toString();
                gpro_additionAmount = addition_AmountET.getText().toString();
                addition_Addbtn.setEnabled(!gpro_additionDetail.isEmpty() && !gpro_additionAmount.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                gpro_additionDetail = addition_DetailET.getText().toString();
                gpro_additionAmount = addition_AmountET.getText().toString();
                addition_Addbtn.setEnabled(!gpro_additionDetail.isEmpty() && !gpro_additionAmount.isEmpty());
                if (gpro_additionDetail.isEmpty()) {
                    addition_DetailET.setError("Addition detail is required");
                }
                if (addition_AmountET.isFocused() && gpro_additionAmount.isEmpty()) {
                    addition_AmountET.setError("Addition amount is required");
                }
            }
        };
        addition_DetailET.addTextChangedListener(textWatcher4);
        addition_AmountET.addTextChangedListener(textWatcher4);
        add_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        addition_Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpro_additionDetail = addition_DetailET.getText().toString();
                gpro_additionAmount = addition_AmountET.getText().toString();
                gpro_additionDescription = addition_descriptionET.getText().toString();
                additionDataModalClassestwo.add(new AdditionDataModalClass(gpro_additionDetail, gpro_additionAmount, gpro_additionDescription));
                buildAdditiondataRv();
                addition_AmtTotal = addition_AmtTotal + Double.parseDouble(gpro_additionAmount);
                Log.i(TAG, "onClick: " + addition_AmtTotal);
                amtpayable = Double.parseDouble(Amount_et.getText().toString());
//                Log.i(TAG, "onClick: amt"+String.valueOf(amtpayable));
//                Log.i(TAG, "onClick: addition_amt_total"+String.valueOf(addition_AmtTotal));
//                Log.i(TAG, "onClick: discount_total"+discount_AmtTotal);

                double total_amt = amtpayable + addition_AmtTotal - discount_AmtTotal;
                double gst = total_amt * 18 / 100;
                gstAmttv.setText(String.valueOf(gst));
                double total_pay = amtpayable + addition_AmtTotal - discount_AmtTotal + gst;
                total_amt_two = amtpayable + addition_AmtTotal - discount_AmtTotal;
                total_payable_rsTV.setText(String.valueOf(total_pay));
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void buildAdditiondataRv() {
        additiondataRV.setHasFixedSize(true);
        additiondataRV.setLayoutManager(new LinearLayoutManager(getContext()));
        gproAdditionAdapter = new GproAdditionAdapter(additionDataModalClassestwo, getContext());
        additiondataRV.setAdapter(gproAdditionAdapter);
        gproAdditionAdapter.setOnItemClickListener(new GproAdditionAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double addAmount) {
                additionDataModalClassestwo.remove(position);
                gproAdditionAdapter.notifyItemRemoved(position);
                buildAdditiondataRv();
//                gpro_additionAmount = addition_AmountET.getText().toString();
//                addition_AmtTotal = addition_AmtTotal - addAmount;

//                String totalAmt = total_payable_rsTV.getText().toString();
                Log.i(TAG, "onDelete: total amt two---->>>" + total_amt_two);
                total_amt_two = total_amt_two - addAmount;
                Log.i(TAG, "onDelete: totalAmt--------->>>" + total_amt_two);
                double gst = total_amt_two * 18 / 100;
                double total_pay = total_amt_two + gst;

                total_payable_rsTV.setText(String.valueOf(total_pay));

            }
        });
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
                    wholeClassUserIdsToSend = new ArrayList<>(userIds);
                    Log.i(TAG, "onResponse: wholeclassUserid" + wholeClassUserIdsToSend);

                    setAdapterForNameSearch(namesForSearchEditText);


                    Name_searchET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.i(TAG, "firstclick:stdIdTosend " + userIds.get(i));
                            userIdsIndividual = new ArrayList<>();
                            userIdsIndividual.add(userIds.get(i));
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
                                            userIdsIndividual = new ArrayList<>();
                                            userIdsIndividual.add(userIds.get(i));
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
//        Name_searchET.showDropDown();
//        Name_searchET.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Name_searchET.requestFocus();
//            }
//        });
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
        Userarray_list_toSend = new ArrayList<>();
        try {
            if (status.equals("Individual")) {
                Userarray_list_toSend = userIdsIndividual;
            } else {
                Userarray_list_toSend = wholeClassUserIdsToSend;
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Select Individual or Class", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "SendGeneratePaymentRequest: date-------->" + dateToSend);
        RequestGeneratePaymentRequestIndividual paymentRequestIndividual = new RequestGeneratePaymentRequestIndividual(total_amt_two, noteToSend, paymentTypeToSend, null, discount_details_tosend, addition_details_tosend, dateToSend, Userarray_list_toSend, stdIdToSend);
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

        Log.i(TAG, "SendGeneratePaymentRequest:amtToSend " + amtToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:noteToSend " + noteToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:paymentTypeToSend " + paymentTypeToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:discountDetailToSend " + discountDetailToSend);
        Log.i(TAG, "SendGeneratePaymentRequest:dateToSend " + dateToSend);
//        Log.i(TAG, "SendGeneratePaymentRequest:userIdsToSend " + userIdsToSend.get(0));
        Log.i(TAG, "SendGeneratePaymentRequest:stdIdToSend " + stdIdToSend);

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
        Button cancel_Btn = discountpopup.findViewById(R.id.cancel_btn);
        TextWatcher textWatcher3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountDetailET = title.getText().toString();
                discountAmountET = DiscountAmount.getText().toString();
                addbtns.setEnabled(!discountDetailET.isEmpty() && !discountAmountET.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                discountDetailET = title.getText().toString();
                discountAmountET = DiscountAmount.getText().toString();
                addbtns.setEnabled(!discountDetailET.isEmpty() && !discountAmountET.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                discountDetailET = title.getText().toString();
                discountAmountET = DiscountAmount.getText().toString();
                addbtns.setEnabled(!discountDetailET.isEmpty() && !discountAmountET.isEmpty());
                if (discountDetailET.isEmpty()) {
                    title.setError("Discount Title is required");
                }
                if (DiscountAmount.isFocused() && discountAmountET.isEmpty()) {
                    DiscountAmount.setError("Discount Amount is required");
                }
            }
        };
        title.addTextChangedListener(textWatcher3);
        DiscountAmount.addTextChangedListener(textWatcher3);
        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        addbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("TAG", title.getText().toString());

                discountDetailET = title.getText().toString();
                discountAmountET = DiscountAmount.getText().toString();
                discountDescription = Discountdetail.getText().toString();
//                getdata(discountDetailET, discountAmountET, discountDescription);
                discountdataModelstwo.add(new DiscountdataModel(discountDetailET, discountAmountET, discountDescription));
                discount_AmtTotal = discount_AmtTotal + Double.parseDouble(discountAmountET);
                amtpayable = Double.parseDouble(Amount_et.getText().toString());
                double total_amt = amtpayable + addition_AmtTotal - discount_AmtTotal;
                double gst = total_amt * 18 / 100;
                gstAmttv.setText(String.valueOf(gst));
                double total_amt_topay = amtpayable + addition_AmtTotal - discount_AmtTotal + gst;
                total_amt_two = amtpayable + addition_AmtTotal - discount_AmtTotal;
                total_payable_rsTV.setText(String.valueOf(total_amt_topay));
                Log.i(TAG, "onClick: totla amt------>" + total_amt);

                builddiscout_RV();
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

            }
        });
        builder.setView(discountpopup);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void builddiscout_RV() {
        discountdataRV.setHasFixedSize(true);
        discountdataRV.setLayoutManager(new LinearLayoutManager(getContext()));
        gproDiscountAdapter = new GproDiscountAdapter(discountdataModelstwo, getContext());
        discountdataRV.setAdapter(gproDiscountAdapter);
        gproDiscountAdapter.setOnItemClickListener(new GproDiscountAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position, double amount) {
                discountdataModelstwo.remove(position);
                gproDiscountAdapter.notifyItemRemoved(position);
                builddiscout_RV();
                Log.i(TAG, "onDelete: " + amount);

                discount_AmtTotal = discount_AmtTotal - amount;
//                String totalAmt = total_payable_rsTV.getText().toString();
//                double total_amt = Double.parseDouble(totalAmt) + amount;
                total_amt_two = total_amt_two + amount;
                Log.i(TAG, "onDelete: totalAmt--------->>>" + total_amt_two);
                double gst = total_amt_two * 18 / 100;
                double total_pay = total_amt_two + gst;
                total_payable_rsTV.setText(String.valueOf(total_pay));
            }
        });
    }

//    private void getdata(String discountDetailET, String discountAmountET, String discountDescriptionET) {
//        JSONObject jsonObject;
//        try {
//            jsonObject = new JSONObject();
//            jsonObject.put("name", discountDetailET);
//            jsonObject.put("amount", discountAmountET);
//            jsonObject.put("details", discountDescriptionET);
//            discountDetailToSend = jsonObject.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "getdata:discountDetailToSend " + discountDetailToSend);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);

        Send_Request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Send_Request_btn.isEnabled()) {
                    noteToSend = Description_ET.getText().toString();
                    amtToSend = Double.parseDouble(total_payable_rsTV.getText().toString());
                    Log.i(TAG, "onClick totalamt_to send:--------> " + total_amt_two);

                    ArrayList<Integer> userIds = new ArrayList<>();
                    JSONArray discount_array, addition_array;
                    JSONObject discount_object, addition_object;
                    discount_array = new JSONArray();
                    addition_array = new JSONArray();
                    for (DiscountdataModel dm :
                            discountdataModelstwo) {
                        discount_object = new JSONObject();
                        try {
                            discount_object.put("name", dm.getDiscountDetail());
                            discount_object.put("amount", dm.getDiscountAmount());
                            discount_object.put("details", dm.getDiscountDescription());
                            discount_array.put(discount_object);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                    for (AdditionDataModalClass am :
                            additionDataModalClassestwo) {
                        addition_object = new JSONObject();
                        try {
                            addition_object.put("name", am.getAdditionTitle());
                            addition_object.put("amount", am.getAdditionAmount());
                            addition_object.put("details", am.getAdditionDescription());
                            addition_array.put(addition_object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    discount_details_tosend = discount_array.toString();
                    addition_details_tosend = addition_array.toString();

                    Log.i(TAG, "onClick: " + discount_details_tosend);


                    SendGeneratePaymentRequest();

                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}