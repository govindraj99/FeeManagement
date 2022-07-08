package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.fee_management_new.AllActivitiesTwoFragmentArgs;
//import com.example.fee_management_new.AllActivitiesTwoFragmentDirections;
import com.bumptech.glide.Glide;
import com.example.fee_management_new.Fragment.AllActivitiesTwoFragmentDirections;
import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AllActivitiesTwoAdapter extends RecyclerView.Adapter<AllActivitiesTwoAdapter.AllActivitiesTwoViewHolder> {
    Context context;
    ArrayList<AllActivitesTwoModal> allActivitesTwoModals;
    int id;
    static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";


    public AllActivitiesTwoAdapter(Context context, ArrayList<AllActivitesTwoModal> allActivitesTwoModals) {
        this.context = context;
        this.allActivitesTwoModals = allActivitesTwoModals;
    }

    @NonNull
    @Override
    public AllActivitiesTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_activity_two_recyclerview_card,parent,false);
        AllActivitiesTwoViewHolder activitiesTwoViewHolder = new AllActivitiesTwoViewHolder(view);
        return activitiesTwoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllActivitiesTwoViewHolder holder, int position) {
        AllActivitesTwoModal currentData = allActivitesTwoModals.get(position);
        holder.allactivitiesTwo_name.setText(currentData.getName());
        holder.allactivitiesTwo_Rollno.setText(currentData.getRollno());
        holder.allactivitiesTwo_Amount.setText(currentData.getAmount());
        holder.allactivitiesTwo_Note.setText(currentData.getNote());
        holder.allactivitiesTwo_Date.setText(currentData.getDate());
        Glide.with(context).load(baseUrlForImages+currentData.getImage()).into(holder.allActivity_TwoIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = currentData.getId();
                NavDirections action = com.example.fee_management_new.Fragment.AllActivitiesTwoFragmentDirections.actionAllActivitiesFragmentToPaymentRequesrtDetailsTwoFragment(id);
                Navigation.findNavController(view).navigate(action);
            }
        });
        switch (currentData.getStatus()){
            case "Paid":
                holder.allactivitiesTwo_Paid.setVisibility(View.VISIBLE);
                holder.allactivitiesTwo_pending.setVisibility(View.GONE);
                holder.allactivitiesTwo_cancelle.setVisibility(View.GONE);
                holder.allactivitiesTwo_refunded.setVisibility(View.GONE);
                holder.allactivitiesTwo_Overdue.setVisibility(View.GONE);
                break;

            case "Pending":
                holder.allactivitiesTwo_Paid.setVisibility(View.GONE);
                holder.allactivitiesTwo_pending.setVisibility(View.VISIBLE);
                holder.allactivitiesTwo_cancelle.setVisibility(View.GONE);
                holder.allactivitiesTwo_refunded.setVisibility(View.GONE);
                holder.allactivitiesTwo_Overdue.setVisibility(View.GONE);
                break;
            case "Cancelled":
                holder.allactivitiesTwo_Paid.setVisibility(View.GONE);
                holder.allactivitiesTwo_pending.setVisibility(View.GONE);
                holder.allactivitiesTwo_cancelle.setVisibility(View.VISIBLE);
                holder.allactivitiesTwo_refunded.setVisibility(View.GONE);
                holder.allactivitiesTwo_Overdue.setVisibility(View.GONE);
                break;
            case "Refund":
                holder.allactivitiesTwo_Paid.setVisibility(View.GONE);
                holder.allactivitiesTwo_pending.setVisibility(View.GONE);
                holder.allactivitiesTwo_cancelle.setVisibility(View.GONE);
                holder.allactivitiesTwo_refunded.setVisibility(View.VISIBLE);
                holder.allactivitiesTwo_Overdue.setVisibility(View.GONE);
                break;
            case "Overdue":
                holder.allactivitiesTwo_Paid.setVisibility(View.GONE);
                holder.allactivitiesTwo_pending.setVisibility(View.GONE);
                holder.allactivitiesTwo_cancelle.setVisibility(View.GONE);
                holder.allactivitiesTwo_refunded.setVisibility(View.GONE);
                holder.allactivitiesTwo_Overdue.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return allActivitesTwoModals.size();
    }

    public class AllActivitiesTwoViewHolder extends RecyclerView.ViewHolder{
        TextView allactivitiesTwo_name,allactivitiesTwo_Rollno,allactivitiesTwo_Amount,allactivitiesTwo_Note,allactivitiesTwo_Date,allactivitiesTwo_pending,allactivitiesTwo_refunded,allactivitiesTwo_cancelle,allactivitiesTwo_Paid,allactivitiesTwo_Overdue;
        ShapeableImageView allActivity_TwoIV;

        public AllActivitiesTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            allactivitiesTwo_name = itemView.findViewById(R.id.allactivitytwo_RVname);
            allactivitiesTwo_Rollno = itemView.findViewById(R.id.allactivitytwo_RVrollno);
            allactivitiesTwo_Amount = itemView.findViewById(R.id.allactivitytwo_RVamount);
            allactivitiesTwo_Note = itemView.findViewById(R.id.allactivitytwo_RVdiscriptionTV);
            allactivitiesTwo_Date = itemView.findViewById(R.id.allactivitytwo_RVdate);
            allactivitiesTwo_pending = itemView.findViewById(R.id.allactivitytwo_pending);
            allactivitiesTwo_refunded = itemView.findViewById(R.id.allactivitytwo_refunded);
            allactivitiesTwo_cancelle = itemView.findViewById(R.id.allactivitytwo_cancelled);
            allactivitiesTwo_Paid = itemView.findViewById(R.id.allactivitytwo_paid);
            allactivitiesTwo_Overdue = itemView.findViewById(R.id.allactivitytwo_overdue);
            allActivity_TwoIV = itemView.findViewById(R.id.all_activity_TwoRVImage);

        }

    }
}
