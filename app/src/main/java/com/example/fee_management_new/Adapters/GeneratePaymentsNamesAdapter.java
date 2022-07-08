package com.example.fee_management_new.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.R;

import java.util.ArrayList;

public class GeneratePaymentsNamesAdapter extends RecyclerView.Adapter<GeneratePaymentsNamesAdapter.GeneratePaymentsNamesViewHolder> {
    ArrayList<String> namesList;
    ImageView cancelbtn;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public GeneratePaymentsNamesAdapter(ArrayList<String> namesList) {
        this.namesList = namesList;
    }

    @NonNull
    @Override
    public GeneratePaymentsNamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generatepayment2_card, parent, false);
        GeneratePaymentsNamesViewHolder generatePaymentsNamesViewHolder = new GeneratePaymentsNamesViewHolder(view, onItemClickListener);
        return generatePaymentsNamesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratePaymentsNamesViewHolder holder, int position) {
        holder.suggestName.setText(namesList.get(position));
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class GeneratePaymentsNamesViewHolder extends RecyclerView.ViewHolder {
        TextView suggestName;

        public GeneratePaymentsNamesViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            suggestName = itemView.findViewById(R.id.suggest_name);
            cancelbtn = itemView.findViewById(R.id.cancel_suggest);
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
