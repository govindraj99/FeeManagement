package com.example.fee_management_new.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class RecyclerviewRecentActivityAdapter extends RecyclerView.Adapter<RecyclerviewRecentActivityAdapter.RAViewHolder> {
    ArrayList<RecentActivitiesone> recentActivitiesoneslist;

    public RecyclerviewRecentActivityAdapter(ArrayList<RecentActivitiesone> recentActivitiesoneslist) {
        this.recentActivitiesoneslist = recentActivitiesoneslist;
    }

    @NonNull
    @Override
    public RAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_activity_card, parent, false);
        RAViewHolder raViewHolder = new RAViewHolder(view);
        return raViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RAViewHolder holder, int position) {
        RecentActivitiesone currentModel = recentActivitiesoneslist.get(position);
        holder.amount_TV.setText(new StringBuilder().append("\u20B9 ").append(currentModel.getAmount()).toString());
        holder.dateTV.setText(currentModel.getDate());
        holder.nameTv.setText(currentModel.getName());
        holder.std_TV.setText(currentModel.getStd());
        holder.sectionTV.setText(currentModel.getSection());
        holder.Discription_TV.setText(currentModel.getNote());
        switch (currentModel.getStatus()){
            case "Paid":
                    holder.paidTV.setVisibility(View.VISIBLE);
                break;

            case "Pending":
                    holder.pendingTV.setVisibility(View.VISIBLE);
                break;
            case "Cancelled":
                    holder.cancelledTV.setVisibility(View.VISIBLE);
                break;
            case "Refund":
                holder.refundTV.setVisibility(View.VISIBLE);
                break;
            case "Overdue":
                holder.overdueTV.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return recentActivitiesoneslist.size();
    }

    public class RAViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,std_TV,sectionTV,amount_TV,dateTV,pendingTV,refundTV,cancelledTV,paidTV,overdueTV,Discription_TV;

        public RAViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.ra_name);
            std_TV = itemView.findViewById(R.id.raStd);
            sectionTV = itemView.findViewById(R.id.ra_section);
            amount_TV = itemView.findViewById(R.id.raAmount);
            dateTV = itemView.findViewById(R.id.radate);
            pendingTV = itemView.findViewById(R.id.ra_pending);
            refundTV = itemView.findViewById(R.id.ra_refunded);
            cancelledTV = itemView.findViewById(R.id.ra_cancelled);
            paidTV = itemView.findViewById(R.id.ra_paid);
            overdueTV = itemView.findViewById(R.id.ra_overdue);
            Discription_TV = itemView.findViewById(R.id.description_tv);
        }
    }
}