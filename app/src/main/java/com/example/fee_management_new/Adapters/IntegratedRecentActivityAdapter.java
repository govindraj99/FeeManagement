package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fee_management_new.Modalclass.IntegratedRecentActivityModalClass;
import com.example.fee_management_new.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class IntegratedRecentActivityAdapter extends RecyclerView.Adapter<IntegratedRecentActivityAdapter.IntegratedRecentActivityViewHolder>{
    ArrayList<IntegratedRecentActivityModalClass> integratedModelClass;
    Context context;
    static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";
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
        Glide.with(context).load(baseUrlForImages+currentData.getImage()).into(holder.inteRV_Iv);
        switch (currentData.getStatus()){
            case "Paid":
                holder.paidtv.setVisibility(View.VISIBLE);
                holder.pendingtv.setVisibility(View.GONE);
                holder.paymentcanceltv.setVisibility(View.GONE);
                holder.paymentrefundtv.setVisibility(View.GONE);
                holder.overduetv.setVisibility(View.GONE);
                break;

            case "Pending":
                holder.paidtv.setVisibility(View.GONE);
                holder.pendingtv.setVisibility(View.VISIBLE);
                holder.paymentcanceltv.setVisibility(View.GONE);
                holder.paymentrefundtv.setVisibility(View.GONE);
                holder.overduetv.setVisibility(View.GONE);
                break;
            case "Cancelled":
                holder.paidtv.setVisibility(View.GONE);
                holder.pendingtv.setVisibility(View.GONE);
                holder.paymentcanceltv.setVisibility(View.VISIBLE);
                holder.paymentrefundtv.setVisibility(View.GONE);
                holder.overduetv.setVisibility(View.GONE);
                break;
            case "Refund":
                holder.paidtv.setVisibility(View.GONE);
                holder.pendingtv.setVisibility(View.GONE);
                holder.paymentcanceltv.setVisibility(View.GONE);
                holder.paymentrefundtv.setVisibility(View.VISIBLE);
                holder.overduetv.setVisibility(View.GONE);
                break;
            case "Overdue":
                holder.paidtv.setVisibility(View.GONE);
                holder.pendingtv.setVisibility(View.GONE);
                holder.paymentcanceltv.setVisibility(View.GONE);
                holder.paymentrefundtv.setVisibility(View.GONE);
                holder.overduetv.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return integratedModelClass.size();
    }

    public class IntegratedRecentActivityViewHolder extends RecyclerView.ViewHolder{
        TextView name,Amount,month,date,pendingtv,paidtv,overduetv,paymentcanceltv,paymentrefundtv;
        ShapeableImageView inteRV_Iv;


        public IntegratedRecentActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.names);
            Amount = itemView.findViewById(R.id.amount_tv);
            month = itemView.findViewById(R.id.month_tv);
            date = itemView.findViewById(R.id.datetv);
            inteRV_Iv = itemView.findViewById(R.id.inte_ElizaImg);
            pendingtv = itemView.findViewById(R.id.Pending_TV);
            paidtv = itemView.findViewById(R.id.Paid_TV);
            overduetv = itemView.findViewById(R.id.Overdue_TV);
            paymentcanceltv = itemView.findViewById(R.id.paymentcancel_tv);
            paymentrefundtv = itemView.findViewById(R.id.paymentrefund_tv);
        }
    }
}
