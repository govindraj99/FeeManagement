package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.Modalclass.DiscountdataModel;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class PrdThreeAdditionAdapter extends RecyclerView.Adapter<PrdThreeAdditionAdapter.PrdThreeAdditionViewHolder> {
    ArrayList<AdditionDataModalClass> additionDataModalClasses;
    Context context;

    public PrdThreeAdditionAdapter(ArrayList<AdditionDataModalClass> additionDataModalClasses, Context context) {
        this.additionDataModalClasses = additionDataModalClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public PrdThreeAdditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prdt_additiondetailrow,parent,false);
        PrdThreeAdditionViewHolder prdThreeAdditionViewHolder = new PrdThreeAdditionViewHolder(view);
        return prdThreeAdditionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrdThreeAdditionViewHolder holder, int position) {
        AdditionDataModalClass currentData = additionDataModalClasses.get(position);
        holder.prd_threeAdditionAmount.setText(currentData.getAdditionAmount());
        holder.prd_threeAdditionTitle.setText(currentData.getAdditionTitle());
    }

    @Override
    public int getItemCount() {
        return additionDataModalClasses.size();
    }

    public class PrdThreeAdditionViewHolder extends RecyclerView.ViewHolder{
        TextView prd_threeAdditionTitle, prd_threeAdditionAmount;

        public PrdThreeAdditionViewHolder(@NonNull View itemView) {
            super(itemView);
            prd_threeAdditionTitle = itemView.findViewById(R.id.prd_three_additiontitle);
            prd_threeAdditionAmount = itemView.findViewById(R.id.prd_three_additionamount);

        }
    }
}
