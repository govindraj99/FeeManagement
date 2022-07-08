package com.example.fee_management_new.Fragment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.R;
import com.google.android.material.textfield.TextInputEditText;


public class discountdialougFragment extends DialogFragment {
    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;
    View view;
    private final static String Tag = "MyCustomDialog";
    TextInputEditText Discountdetail_ET, DiscountAmount_ET;
    AppCompatButton Addbtn, Cancelbtn;
    TextView DiscountTV;


    // TODO: Rename and change types of parameters


    public discountdialougFragment() {
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
        view = inflater.inflate(R.layout.fragment_discountdialoug, container, false);
        Discountdetail_ET = view.findViewById(R.id.discount_detailET);
        DiscountAmount_ET = view.findViewById(R.id.discountAmount_ET);

        Addbtn = view.findViewById(R.id.aadbtn);
        Cancelbtn = view.findViewById(R.id.cancel_btn);
        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Add Clicked", "Add clicked");
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                String Discountinput = Discountdetail_ET.getText().toString();
                if (!Discountinput.equals("")) {

//                    GeneratePaymentRequest generatePaymentRequest1 = (GeneratePaymentRequest) getActivity().getSupportFragmentManager().findFragmentByTag("GeneratePaymentFrag");
//                    Log.i("data",Discountinput);
//                    generatePaymentRequest1.DiscountTV.setText(Discountinput);
                    onInputSelected.sendInput(Discountinput);
                    Log.i("Data after set text", "onClick: ");
                }
                getDialog().dismiss();
            }
        });

//        DiscountAmount_ET.addTextChangedListener(textWatcher2);
//        Discountdetail_ET.addTextChangedListener(textWatcher2);
//        Addbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String Discountdetail_et = Discountdetail_ET.getText().toString().trim();
//                DiscountTV.setText(Discountdetail_et);
//                Toast.makeText(getContext(), Discountdetail_et, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onInputSelected = (OnInputSelected) getTargetFragment();

        }catch (ClassCastException e){
            Log.e("error", "onAttach:ClassCastException "+e.getMessage() );
        }
    }
    //    private TextWatcher textWatcher2 = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            String discountdetial_et = Discountdetail_ET.getText().toString().trim();
//            String discountamount_et = DiscountAmount_ET.getText().toString().trim();
//            Addbtn.setEnabled(!discountdetial_et.isEmpty() && !discountamount_et.isEmpty());
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}