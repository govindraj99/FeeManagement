package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class RecyclerviewRecentActivityAdapter extends RecyclerView.Adapter<RecyclerviewRecentActivityAdapter.RAViewHolder> {
    Context context;
    static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";
    ArrayList<RecentActivitiesone> recentActivitiesoneslist;

    public RecyclerviewRecentActivityAdapter(ArrayList<RecentActivitiesone> recentActivitiesoneslist, Context context) {
        this.recentActivitiesoneslist = recentActivitiesoneslist;
        this.context = context;

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
        Glide.with(context).load(baseUrlForImages + currentModel.getImage()).into(holder.raImage);
        switch (currentModel.getStatus()) {
            case "Paid":
                holder.paidTV.setVisibility(View.VISIBLE);
                holder.pendingTV.setVisibility(View.GONE);
                holder.cancelledTV.setVisibility(View.GONE);
                holder.refundTV.setVisibility(View.GONE);
                holder.overdueTV.setVisibility(View.GONE);
                break;

            case "Pending":
                holder.paidTV.setVisibility(View.GONE);
                holder.pendingTV.setVisibility(View.VISIBLE);
                holder.cancelledTV.setVisibility(View.GONE);
                holder.refundTV.setVisibility(View.GONE);
                holder.overdueTV.setVisibility(View.GONE);
                break;
            case "Cancelled":
                holder.paidTV.setVisibility(View.GONE);
                holder.pendingTV.setVisibility(View.GONE);
                holder.cancelledTV.setVisibility(View.VISIBLE);
                holder.refundTV.setVisibility(View.GONE);
                holder.overdueTV.setVisibility(View.GONE);
                break;
            case "Refunded":
                holder.paidTV.setVisibility(View.GONE);
                holder.pendingTV.setVisibility(View.GONE);
                holder.cancelledTV.setVisibility(View.GONE);
                holder.refundTV.setVisibility(View.VISIBLE);
                holder.overdueTV.setVisibility(View.GONE);
                break;
            case "Overdue":
                holder.paidTV.setVisibility(View.GONE);
                holder.pendingTV.setVisibility(View.GONE);
                holder.cancelledTV.setVisibility(View.GONE);
                holder.refundTV.setVisibility(View.GONE);
                holder.overdueTV.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return recentActivitiesoneslist.size();
    }

    public class RAViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, std_TV, sectionTV, amount_TV, dateTV, pendingTV, refundTV, cancelledTV, paidTV, overdueTV, Discription_TV;
        ImageView raImage;

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
            raImage = itemView.findViewById(R.id.ra_ElizaImg);
        }
    }
}