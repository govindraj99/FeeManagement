package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.AdditionDataModalClass;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class GproAdditionAdapter extends RecyclerView.Adapter<GproAdditionAdapter.GproAdditionViewHolder> {
    private OnItemClickListener onItemClickListener;
    ArrayList<AdditionDataModalClass> additionDataModalClasses;
    Context context;


    public GproAdditionAdapter(ArrayList<AdditionDataModalClass> additionDataModalClasses, Context context) {
        this.additionDataModalClasses = additionDataModalClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public GproAdditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gprt_additiondetailrow, parent, false);
        GproAdditionViewHolder gproAdditionViewHolder = new GproAdditionViewHolder(view, onItemClickListener);
        return gproAdditionViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull GproAdditionViewHolder holder, int position) {
        AdditionDataModalClass currentData = additionDataModalClasses.get(position);
        holder.gproAdditionTitle.setText(currentData.getAdditionTitle());
        holder.gproAdditionAmount.setText(String.valueOf(currentData.getAdditionAmount()));

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

    public class GproAdditionViewHolder extends RecyclerView.ViewHolder {

        TextView gproAdditionTitle, gproAdditionAmount, removeTv;

        public GproAdditionViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            gproAdditionTitle = itemView.findViewById(R.id.gprt_additiontitle);
            gproAdditionAmount = itemView.findViewById(R.id.gprt_additionamount);
            removeTv = itemView.findViewById(R.id.remove_tv);
           removeTv.setOnClickListener(new View.OnClickListener() {
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
