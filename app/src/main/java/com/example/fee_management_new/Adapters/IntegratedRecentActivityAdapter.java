package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.IntegratedRecentActivityModalClass;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class IntegratedRecentActivityAdapter extends RecyclerView.Adapter<IntegratedRecentActivityAdapter.IntegratedRecentActivityViewHolder>{
    ArrayList<IntegratedRecentActivityModalClass> integratedModelClass;
    Context context;
    FragmentManager fragmentManager;
    int uid;

    public IntegratedRecentActivityAdapter(ArrayList<IntegratedRecentActivityModalClass> integratedModelClass,Context context) {
        this.integratedModelClass = integratedModelClass;
        this.context = context;

    }

    @NonNull
    @Override
    public IntegratedRecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.integrated_acard,parent,false);
        IntegratedRecentActivityViewHolder integratedRecentActivityViewHolder = new IntegratedRecentActivityViewHolder(view);

        return integratedRecentActivityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntegratedRecentActivityViewHolder holder, int position) {
        IntegratedRecentActivityModalClass currentData = integratedModelClass.get(position);
        holder.date.setText(currentData.getDate());
        holder.Amount.setText(currentData.getAmount());
//        holder.month.setText(currentData.getMonth());
        holder.name.setText(currentData.getName());


    }

    @Override
    public int getItemCount() {
        return integratedModelClass.size();
    }

    public class IntegratedRecentActivityViewHolder extends RecyclerView.ViewHolder{
        TextView name,Amount,month,date;


        public IntegratedRecentActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.names);
            Amount = itemView.findViewById(R.id.amount_tv);
            month = itemView.findViewById(R.id.month_tv);
            date = itemView.findViewById(R.id.datetv);
        }
    }
}
