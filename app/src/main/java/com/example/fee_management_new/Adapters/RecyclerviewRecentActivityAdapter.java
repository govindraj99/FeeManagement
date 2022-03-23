package com.example.fee_management_new.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.R;

import java.util.ArrayList;

public class RecyclerviewRecentActivityAdapter extends RecyclerView.Adapter<RecyclerviewRecentActivityAdapter.RAViewHolder> {
    ArrayList<String> names;

    public RecyclerviewRecentActivityAdapter(ArrayList<String> names) {
        this.names = names;
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
        holder.nameTv.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class RAViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;

        public RAViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.names);
        }
    }
}