package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class AOPAdditionAdapter extends RecyclerView.Adapter<AOPAdditionAdapter.AOPAdditionViewHOlder> {
    private OnItemClickListener onItemClickListener;
    ArrayList<AdditionDataModalClass> additionDataModalClasses;
    Context context;

    public AOPAdditionAdapter(ArrayList<AdditionDataModalClass> additionDataModalClasses, Context context) {
        this.additionDataModalClasses = additionDataModalClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public AOPAdditionViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gprt_additiondetailrow, parent, false);
        AOPAdditionViewHOlder aopAdditionViewHOlder = new AOPAdditionViewHOlder(view,onItemClickListener);
        return aopAdditionViewHOlder;
    }

    @Override
    public void onBindViewHolder(@NonNull AOPAdditionViewHOlder holder, int position) {
        AdditionDataModalClass currentData = additionDataModalClasses.get(position);
        holder.aopAdditionTitle.setText(currentData.getAdditionTitle());
        holder.aopAdditionAmount.setText(String.valueOf(currentData.getAdditionAmount()));
    }
    public interface OnItemClickListener {
        void onDelete(int position,double amount);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return additionDataModalClasses.size();
    }

    public class AOPAdditionViewHOlder extends RecyclerView.ViewHolder {
        TextView aopAdditionTitle, aopAdditionAmount,aopRemovetv;

        public AOPAdditionViewHOlder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            aopAdditionTitle = itemView.findViewById(R.id.gprt_additiontitle);
            aopAdditionAmount = itemView.findViewById(R.id.gprt_additionamount);
            aopRemovetv = itemView.findViewById(R.id.remove_tv);
            aopRemovetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDelete(position, Double.parseDouble(additionDataModalClasses.get(position).getAdditionAmount()));

                        }

                    }
                }
            });
        }
    }
}
