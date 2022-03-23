package com.example.fee_management_new;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fee_management_new.Adapters.RecyclerviewRecentActivityAdapter;
import com.example.fee_management_new.Adapters.SettlementsAdapter;
import com.example.fee_management_new.Adapters.TransactionAdapter;
import com.example.fee_management_new.Modalclass.SettelmentModel;
import com.example.fee_management_new.Modalclass.TransactionData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class Transaction extends Fragment {
    View view;
    RecyclerView rvTransaction;
    ArrayList<TransactionData> transactionData;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    CardView TransacCardview;
    NavController navController;
    LinearLayout Paymentdate_Bottomsheet,TypeofPayment_layout;


    public Transaction() {
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
        view = inflater.inflate(R.layout.fragment_transaction, container, false);
        CreateTransactionCard();
        buildTransactionCard();
        Paymentdate_Bottomsheet = view.findViewById(R.id.payment_datelayout);
        TypeofPayment_layout = view.findViewById(R.id.typeofPayment_layout);
        TransacCardview = view.findViewById(R.id.cardViewtrasc);

        TypeofPayment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetTypeofPayment();
            }
        });
        Paymentdate_Bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetPaymentdate();
            }
        });

        return view;
    }

    private void bottomsheetTypeofPayment() {
        view = getLayoutInflater().inflate(R.layout.type_of_paymentbottomsheet,null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(),R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();

    }

    private void bottomsheetPaymentdate() {
        view = getLayoutInflater().inflate(R.layout.paymentdate_bottomsheet,null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(),R.style.AppBottomSheetDialogTheme);
                bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                bt.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                bt.setContentView(view);
                bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
                bt.show();

            }



    private void buildTransactionCard() {
        rvTransaction = view.findViewById(R.id.TransactionRv);
        rvTransaction.setHasFixedSize(true);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransaction.setAdapter(new TransactionAdapter(transactionData,getContext(),getParentFragmentManager()));

    }
    private void CreateTransactionCard(){
        transactionData = new ArrayList<>();
        transactionData.add(new TransactionData("Eliza O’Conner","Integrated batch","A","1200","12/05/2021, 06:00 PM"));
        transactionData.add(new TransactionData("Trix West","Integrated batch","A","1200","12/05/2021, 06:00 PM"));
        transactionData.add(new TransactionData("gggg","Integrated batch","A","1200","12/05/2021, 06:00 PM"));
        transactionData.add(new TransactionData("yyyyy","Integrated batch","A","1200","12/05/2021, 06:00 PM"));
    }



}