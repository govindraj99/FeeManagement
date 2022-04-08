package com.example.fee_management_new.DiscountRoomDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.R;

import java.util.List;

public class DiscountDetailAdapter extends RecyclerView.Adapter<DiscountDetailAdapter.DiscountDetailViewHolder> {
    List<DiscountData> discountDatalist;
    private Context context;
    private RoomDB database;

    public DiscountDetailAdapter(List<DiscountData> discountDatalist, Context context) {
        this.discountDatalist = discountDatalist;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discountdetailrow,parent,false);
        DiscountDetailViewHolder discountDetailViewHolder = new DiscountDetailViewHolder(view);
        return discountDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountDetailViewHolder holder, int position) {
        DiscountData discountData = discountDatalist.get(position);
        database = RoomDB.getInstance(context);
        holder.rv_DiscountTitle.setText(discountData.getDiscountdetail());
        holder.rv_DiscountAmount.setText(discountData.getDiscountAmount());

    }

    @Override
    public int getItemCount() {
        return discountDatalist.size();
    }

    public class DiscountDetailViewHolder extends RecyclerView.ViewHolder{
        TextView rv_DiscountTitle,rv_DiscountAmount;

        public DiscountDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_DiscountTitle = itemView.findViewById(R.id.pdt_discounttitle);
            rv_DiscountAmount = itemView.findViewById(R.id.pdt_discountamount);
        }
    }

}
