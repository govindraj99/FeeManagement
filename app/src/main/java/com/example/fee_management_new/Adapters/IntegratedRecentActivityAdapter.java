package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.IntegratedRecentActivityModalClass;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class IntegratedRecentActivityAdapter extends RecyclerView.Adapter<IntegratedRecentActivityAdapter.IntegratedRecentActivityViewHolder>{
    ArrayList<IntegratedRecentActivityModalClass> integratedModelClass;
    Context context;
    FragmentManager fragmentManager;

    public IntegratedRecentActivityAdapter(ArrayList<IntegratedRecentActivityModalClass> integratedModelClass, Context context, FragmentManager fragmentManager) {
        this.integratedModelClass = integratedModelClass;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public IntegratedRecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.Integrated_acard,parent,false);
        IntegratedRecentActivityViewHolder integratedRecentActivityViewHolder = new IntegratedRecentActivityViewHolder(view);

        return integratedRecentActivityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntegratedRecentActivityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IntegratedRecentActivityViewHolder extends RecyclerView.ViewHolder{
        TextView name,Amount,month,date;


        public IntegratedRecentActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.names);
            Amount = itemView.findViewById(R.id.amount_tv);
            month = itemView.findViewById(R.id.monthlyfee_tv);
            date = itemView.findViewById(R.id.)
        }
    }
}
