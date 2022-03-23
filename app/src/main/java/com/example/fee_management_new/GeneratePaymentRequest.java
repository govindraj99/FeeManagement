package com.example.fee_management_new;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class GeneratePaymentRequest extends Fragment  {
    View view;
    NavController navController;
    Button Individualbtn,Send_Request_btn;
    TextView AddTV,discounttv;
    TextInputEditText datepicker,Amount_et,Name_searchET,Description_ET;
    TextInputLayout datepicker2;
    EditText title,amount;
    private final static String Tag = "GeneratePaymentFrag";

    String[] items = {"X", "XI", "XII"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    AppCompatButton Addbtn;

    EditText DiscountDetailET,DiscountAmount;



    // TODO: Rename and change types and number of parameters


    public GeneratePaymentRequest() {
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
        view = inflater.inflate(R.layout.fragment_generate_payment_request, container, false);
        Individualbtn = view.findViewById(R.id.individualBtn);
        AddTV = view.findViewById(R.id.add_tv);
        datepicker= view.findViewById(R.id.datepick);
        Amount_et = view.findViewById(R.id.Amount_ET);
        Name_searchET = view.findViewById(R.id.name_searchET);
        Description_ET = view.findViewById(R.id.description_ET);
        Send_Request_btn = view.findViewById(R.id.send_request_btn);
        discounttv = view.findViewById(R.id.DiscountTV);



        TextView listtxt = view.findViewById(R.id.listTXT);
        Button Classbtn = view.findViewById(R.id.Classbtn);
        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);
        autoCompleteTxt.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_menu, items);
        autoCompleteTxt.setAdapter(adapterItems);
       autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String item = adapterView.getItemAtPosition(i).toString();
               Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();

           }
       });
       datepicker.addTextChangedListener(textWatcher);
       Amount_et.addTextChangedListener(textWatcher);
        Name_searchET.addTextChangedListener(textWatcher);
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
                bottomSheet();
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

    private void discountIalogSheet() {
        Dialog mdialog = new Dialog(getContext());
        mdialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mdialog.setContentView(R.layout.fragment_discountdialoug);
        DiscountAmount = mdialog.findViewById(R.id.discountAmount_ET);
        EditText title=mdialog.findViewById(R.id.discount_detailET);
        Button addbtns = mdialog.findViewById(R.id.aadbtn);
        addbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", title.getText().toString());

                String discountdetail_ET = title.getText().toString();
                discounttv.setText(discountdetail_ET);
            }
        });
        mdialog.setCanceledOnTouchOutside(true);
        mdialog.show();
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
              Send_Request_btn.setEnabled(!datepick.isEmpty() && !AmountET.isEmpty() && !nameSearch.isEmpty() && !descriptionET.isEmpty() );



          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      };

    private void bottomSheet() {
        view = getLayoutInflater().inflate(R.layout.bottom_calendar,null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(),R.style.AppBottomSheetDialogTheme);
//        bt=new BottomSheetDialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat);
        CalendarView calendarView= view.findViewById(R.id.calander_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                bt.dismiss();

                datepicker.setText(new StringBuilder().append(dayOfMonth).append("/").append(month).append("/").append(year).toString());
            }
        });
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
//        view= LayoutInflater.from(context).inflate(R.layout.edit,null);
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
//        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);

        Send_Request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = GeneratePaymentRequestDirections.actionGeneratePaymentRequestToPaymentRequestDetails();
                navController.navigate(action);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

//    @Override
//    public void sendInput(String input) {
//        Log.i("discountData", "incoming Input: "+ input);
//        Discounttv.setText(input);
//
//    }
}