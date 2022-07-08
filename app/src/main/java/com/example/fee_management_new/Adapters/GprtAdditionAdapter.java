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

public class GprtAdditionAdapter extends RecyclerView.Adapter<GprtAdditionAdapter.GprtAdditionViewHolder> {
    ArrayList<AdditionDataModalClass> additionDataModalClassestwo;
    OnItemClickListener onItemClickListener;
    Context context;

    public GprtAdditionAdapter(ArrayList<AdditionDataModalClass> additionDataModalClassestwo, Context context) {
        this.additionDataModalClassestwo = additionDataModalClassestwo;
        this.context = context;
    }

    @NonNull
    @Override
    public GprtAdditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gprt_additiondetailrow, parent, false);
        GprtAdditionViewHolder gprtAdditionViewHolder = new GprtAdditionViewHolder(view, onItemClickListener);
        return gprtAdditionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GprtAdditionViewHolder holder, int position) {
        AdditionDataModalClass currentData = additionDataModalClassestwo.get(position);
        holder.gprtAdditionTitle.setText(currentData.getAdditionTitle());
        holder.gprtAdditionAmount.setText(String.valueOf(currentData.getAdditionAmount()));
    }

    public interface OnItemClickListener {
        void onDelete(int position, double amount);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return additionDataModalClassestwo.size();
    }

    public class GprtAdditionViewHolder extends RecyclerView.ViewHolder {
        TextView gprtAdditionTitle, gprtAdditionAmount, additionRemovetv;

        public GprtAdditionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            gprtAdditionTitle = itemView.findViewById(R.id.gprt_additiontitle);
            gprtAdditionAmount = itemView.findViewById(R.id.gprt_additionamount);
            additionRemovetv = itemView.findViewById(R.id.remove_tv);
            additionRemovetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDelete(position, Double.parseDouble(additionDataModalClassestwo.get(position).getAdditionAmount()));

                        }

                    }
                }
            });
        }
    }
}
