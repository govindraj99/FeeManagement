package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class GprtDiscountAdapter extends RecyclerView.Adapter<GprtDiscountAdapter.GprtDiscountViewholder>{
    ArrayList<DiscountdataModel> discountdataModels;
    OnItemClickListener onItemClickListener;
    private Context context;


    public GprtDiscountAdapter(ArrayList<DiscountdataModel> discountdataModels, Context context) {
        this.discountdataModels = discountdataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public GprtDiscountViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gprt_discountdetailrow,parent,false);
        GprtDiscountViewholder gprtDiscountViewholder = new GprtDiscountViewholder(view,onItemClickListener);
        return gprtDiscountViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull GprtDiscountViewholder holder, int position) {
        DiscountdataModel currentData = discountdataModels.get(position);
        holder.gprtDiscountAmount.setText(currentData.getDiscountAmount());
        holder.gprtDiscountTitle.setText(currentData.getDiscountDetail());

    }
    public interface OnItemClickListener {
        void onDelete(int position,double amount);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return discountdataModels.size();
    }

    public class GprtDiscountViewholder extends RecyclerView.ViewHolder{
        TextView gprtDiscountTitle,gprtDiscountAmount,discountRemove;
        public GprtDiscountViewholder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            gprtDiscountTitle = itemView.findViewById(R.id.gprt_discounttitle);
            gprtDiscountAmount = itemView.findViewById(R.id.gprt_discountamount);
            discountRemove = itemView.findViewById(R.id.discountremove_tv);
            discountRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDelete(position, Double.parseDouble(discountdataModels.get(position).getDiscountAmount()));

                        }

                    }
                }
            });
        }
    }
}
