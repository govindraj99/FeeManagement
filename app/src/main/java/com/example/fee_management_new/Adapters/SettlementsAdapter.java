package com.example.fee_management_new.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.R;
import com.example.fee_management_new.Modalclass.SettelmentModel;

import java.util.ArrayList;

public class SettlementsAdapter extends RecyclerView.Adapter<SettlementsAdapter.SettelmentViewHolder>{

    ArrayList<SettelmentModel> settelmentModels;

    public SettlementsAdapter(ArrayList<SettelmentModel> settelmentModels) {
        this.settelmentModels = settelmentModels;
    }

    @NonNull
    @Override
    public SettelmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settlement_card,parent,false);
        SettelmentViewHolder settelmentViewHolder = new SettelmentViewHolder(view);
        return settelmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettelmentViewHolder holder, int position) {

        SettelmentModel currentModel = settelmentModels.get(position);
        holder.amount.setText(currentModel.getAmount());
        holder.date.setText(currentModel.getDate());

        holder.refId.setText(currentModel.getRefId());

    }

    @Override
    public int getItemCount() {
        return settelmentModels.size();
    }

    public class SettelmentViewHolder extends RecyclerView.ViewHolder{
        TextView amount,date,time,refId;
        public SettelmentViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.Amount_tv);
            date = itemView.findViewById(R.id.date1_tv);

            refId = itemView.findViewById(R.id.refId_tv);
        }
    }
}
