package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.ActivateFragDirections;
import com.example.fee_management_new.InvoiceFragment;
import com.example.fee_management_new.Modalclass.TransactionData;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    ArrayList<TransactionData> transactionData;
    Context context;
    FragmentManager fragmentManager;

    public TransactionAdapter(ArrayList<TransactionData> transactionData, Context context, FragmentManager fragmentManager) {
        this.transactionData = transactionData;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_transaction_card, parent, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(view);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionData currentData = transactionData.get(position);
        holder.names.setText(currentData.getName());
        holder.date.setText(currentData.getDate());
        holder.Section.setText(currentData.getSection());
        holder.Amount.setText(new StringBuilder().append("\u20B9 ").append(currentData.getAmount()).toString());
        holder.STDtv.setText(currentData.getStd());
        holder.note.setText(currentData.getNote());
        switch (currentData.getPaymenttype()){
            case "online":
                holder.online_TV.setVisibility(View.VISIBLE);
                holder.offline_TV.setVisibility(View.GONE);
                break;
            case "offline":
                holder.offline_TV.setVisibility(View.VISIBLE);
                holder.online_TV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return transactionData.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView names, STDtv, Section, Amount, date,note,online_TV,offline_TV;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.namesT);
            STDtv = itemView.findViewById(R.id.stdtv);
            Section = itemView.findViewById(R.id.sectionT);
            Amount = itemView.findViewById(R.id.Amount_transac);
            date = itemView.findViewById(R.id.date_transac);
            note = itemView.findViewById(R.id.notetv);
            offline_TV = itemView.findViewById(R.id.offline_tv);
            online_TV = itemView.findViewById(R.id.online_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new InvoiceFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }

}
