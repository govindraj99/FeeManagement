package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class AllActivitesRecentAdapter extends RecyclerView.Adapter<AllActivitesRecentAdapter.AllActivityRecyclerViewHolder> {
    Context context;
    ArrayList<RecentActivitiesone> Allactivitylist;


    public AllActivitesRecentAdapter(Context context, ArrayList<RecentActivitiesone> allactivitylist) {
        this.context = context;
        Allactivitylist = allactivitylist;
    }

    @NonNull
    @Override
    public AllActivityRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_activity_recyclerview_card,parent,false);
        AllActivityRecyclerViewHolder allActivityRecyclerViewHolder = new AllActivityRecyclerViewHolder(view);
        return allActivityRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllActivityRecyclerViewHolder holder, int position) {
        RecentActivitiesone currentModel = Allactivitylist.get(position);
        holder.AllactivityRVName.setText(currentModel.getName());
        holder.AllactivityRVStd.setText(currentModel.getStd());
        holder.AllactivityRVDate.setText(currentModel.getDate());
        holder.AllactivityRVDescription.setText(currentModel.getNote());
        holder.AllactivityRVSection.setText(currentModel.getSection());
        holder.AllactivityRVAmount.setText(new StringBuilder().append("\u20B9 ").append(currentModel.getAmount()).toString());
        switch (currentModel.getStatus()){
            case "Paid":
                holder.AllactivityPaid.setVisibility(View.VISIBLE);
                break;
            case "Pending":
                holder.AllactivityPending.setVisibility(View.VISIBLE);
                break;
            case "Cancelled":
                holder.AllactivityCancelled.setVisibility(View.VISIBLE);
                break;
            case "Refund":
                holder.Allactivityrefund.setVisibility(View.VISIBLE);
                break;
            case "Overdue":
                holder.AllactivityOverdue.setVisibility(View.VISIBLE);
                break;
        }



    }

    @Override
    public int getItemCount() {
        return Allactivitylist.size();
    }


    public class AllActivityRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView AllactivityRVName,AllactivityRVStd,AllactivityRVSection,AllactivityRVAmount,AllactivityRVDescription,AllactivityRVDate,AllactivityPending,Allactivityrefund,AllactivityCancelled,AllactivityPaid,AllactivityOverdue;


        public AllActivityRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            AllactivityRVName = itemView.findViewById(R.id.all_activityRVname);
            AllactivityRVStd = itemView.findViewById(R.id.all_activityRVstd);
            AllactivityRVSection = itemView.findViewById(R.id.all_activityRVsection);
            AllactivityRVAmount = itemView.findViewById(R.id.all_activityRVamount);
            AllactivityRVDescription = itemView.findViewById(R.id.all_activityRVdiscriptionTV);
            AllactivityPending = itemView.findViewById(R.id.all_activity_pending);
            Allactivityrefund = itemView.findViewById(R.id.all_activity_refunded);
            AllactivityCancelled = itemView.findViewById(R.id.all_activity_cancelled);
            AllactivityPaid = itemView.findViewById(R.id.all_activity_paid);
            AllactivityOverdue = itemView.findViewById(R.id.all_activity_overdue);
            AllactivityRVDate = itemView.findViewById(R.id.all_activityRVdate);



        }
    }
}
