package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class AllActivitiesTwoAdapter extends RecyclerView.Adapter<AllActivitiesTwoAdapter.AllActivitiesTwoViewHolder> {
    Context context;
    ArrayList<AllActivitesTwoModal> allActivitesTwoModals;


    public AllActivitiesTwoAdapter(Context context, ArrayList<AllActivitesTwoModal> allActivitesTwoModals) {
        this.context = context;
        this.allActivitesTwoModals = allActivitesTwoModals;
    }

    @NonNull
    @Override
    public AllActivitiesTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_activity_two_recyclerview_card,parent,false);
        AllActivitiesTwoViewHolder activitiesTwoViewHolder = new AllActivitiesTwoViewHolder(view);
        return activitiesTwoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllActivitiesTwoViewHolder holder, int position) {
        AllActivitesTwoModal currentData = allActivitesTwoModals.get(position);
        holder.allactivitiesTwo_name.setText(currentData.getName());
        holder.allactivitiesTwo_Rollno.setText(currentData.getRollno());
        holder.allactivitiesTwo_Amount.setText(currentData.getAmount());
        holder.allactivitiesTwo_Note.setText(currentData.getNote());
        holder.allactivitiesTwo_Date.setText(currentData.getDate());
        switch (currentData.getStatus()){
            case "Paid":
                holder.allactivitiesTwo_Paid.setVisibility(View.VISIBLE);
                break;

            case "Pending":
                holder.allactivitiesTwo_pending.setVisibility(View.VISIBLE);
                break;
            case "Cancelled":
                holder.allactivitiesTwo_cancelle.setVisibility(View.VISIBLE);
                break;
            case "Refund":
                holder.allactivitiesTwo_refunded.setVisibility(View.VISIBLE);
                break;
            case "Overdue":
                holder.allactivitiesTwo_Overdue.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return allActivitesTwoModals.size();
    }

    public class AllActivitiesTwoViewHolder extends RecyclerView.ViewHolder{
        TextView allactivitiesTwo_name,allactivitiesTwo_Rollno,allactivitiesTwo_Amount,allactivitiesTwo_Note,allactivitiesTwo_Date,allactivitiesTwo_pending,allactivitiesTwo_refunded,allactivitiesTwo_cancelle,allactivitiesTwo_Paid,allactivitiesTwo_Overdue;

        public AllActivitiesTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            allactivitiesTwo_name = itemView.findViewById(R.id.allactivitytwo_RVname);
            allactivitiesTwo_Rollno = itemView.findViewById(R.id.allactivitytwo_RVrollno);
            allactivitiesTwo_Amount = itemView.findViewById(R.id.allactivitytwo_RVamount);
            allactivitiesTwo_Note = itemView.findViewById(R.id.allactivitytwo_RVdiscriptionTV);
            allactivitiesTwo_Date = itemView.findViewById(R.id.allactivitytwo_RVdate);
            allactivitiesTwo_pending = itemView.findViewById(R.id.allactivitytwo_pending);
            allactivitiesTwo_refunded = itemView.findViewById(R.id.allactivitytwo_refunded);
            allactivitiesTwo_cancelle = itemView.findViewById(R.id.allactivitytwo_cancelled);
            allactivitiesTwo_Paid = itemView.findViewById(R.id.allactivitytwo_paid);
            allactivitiesTwo_Overdue = itemView.findViewById(R.id.allactivitytwo_overdue);

        }

    }
}
