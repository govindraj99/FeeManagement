package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class PrdThreeDiscountAdapter extends RecyclerView.Adapter<PrdThreeDiscountAdapter.PrdThreeDiscountViewHolder> {
    ArrayList<DiscountdataModel> discountdataModels;
    Context context;
    private static final String TAG = "PrdThreeDiscountAdapter";

    public PrdThreeDiscountAdapter(ArrayList<DiscountdataModel> discountdataModels, Context context) {
        this.discountdataModels = discountdataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PrdThreeDiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prdt_discountdetailrow,parent,false);
        PrdThreeDiscountViewHolder prdThreeDiscountViewHolder = new PrdThreeDiscountViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: inside Discount Adapter oncreate"+discountdataModels.size());
        return prdThreeDiscountViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrdThreeDiscountViewHolder holder, int position) {
        DiscountdataModel currentData = discountdataModels.get(position);
        holder.prd_threeDiscountAmount.setText(currentData.getDiscountAmount());
        holder.prd_threeDiscountTitle.setText(currentData.getDiscountDetail());
    }

    @Override
    public int getItemCount() {
        return discountdataModels.size();
    }

    public class PrdThreeDiscountViewHolder extends RecyclerView.ViewHolder{
        TextView prd_threeDiscountTitle, prd_threeDiscountAmount;

        public PrdThreeDiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            prd_threeDiscountTitle = itemView.findViewById(R.id.prd_three_discounttitle);
            prd_threeDiscountAmount = itemView.findViewById(R.id.prd_three_discountamount);
        }
    }
}
