package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fee_management_new.Fragment.AllActivityRecentFragmentDirections;
import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.example.fee_management_new.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AllActivitesRecentAdapter extends RecyclerView.Adapter<AllActivitesRecentAdapter.AllActivityRecyclerViewHolder> {
    Context context;
    NavController navController;
    ArrayList<RecentActivitiesone> Allactivitylist;
    static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";
    private static final String TAG = "AllActivitesRecentAdapt";

    public AllActivitesRecentAdapter(Context context, ArrayList<RecentActivitiesone> allactivitylist) {
        this.context = context;
        Allactivitylist = allactivitylist;
    }

    @NonNull
    @Override
    public AllActivityRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_activity_recyclerview_card, parent, false);
        AllActivityRecyclerViewHolder allActivityRecyclerViewHolder = new AllActivityRecyclerViewHolder(view);
        return allActivityRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllActivityRecyclerViewHolder holder, int position) {
        RecentActivitiesone currentModel = Allactivitylist.get(position);
        holder.AllactivityRVName.setText(currentModel.getName().trim());
        holder.AllactivityRVStd.setText(currentModel.getStd());
        holder.AllactivityRVDate.setText(currentModel.getDate());
        holder.AllactivityRVDescription.setText(currentModel.getNote().trim());
        holder.AllactivityRVSection.setText(currentModel.getSection());
        holder.AllactivityRVAmount.setText(new StringBuilder().append("\u20B9 ").append(currentModel.getAmount()).toString());
        Glide.with(context).load(baseUrlForImages+currentModel.getImage()).into(holder.all_ActivityrvImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idtosend = currentModel.getId();
                Log.i(TAG, "onClick: IIiiiidddddd  "+ idtosend);
                NavDirections action = AllActivityRecentFragmentDirections.actionAllActivityRecentFragmentToPaymentRequestDetailsThreeFragment(idtosend);
                Navigation.findNavController(view).navigate(action);
            }
        });


        Log.i(TAG, "onBindViewHolder: "+currentModel.getStatus());
        switch (currentModel.getStatus()) {
            case "Paid":
                holder.AllactivityPaid.setVisibility(View.VISIBLE);
                holder.AllactivityPending.setVisibility(View.GONE);
                holder.AllactivityCancelled.setVisibility(View.GONE);
                holder.Allactivityrefund.setVisibility(View.GONE);
                holder.AllactivityOverdue.setVisibility(View.GONE);
                break;
            case "Pending":
                holder.AllactivityPaid.setVisibility(View.GONE);
                holder.AllactivityPending.setVisibility(View.VISIBLE);
                holder.AllactivityCancelled.setVisibility(View.GONE);
                holder.Allactivityrefund.setVisibility(View.GONE);
                holder.AllactivityOverdue.setVisibility(View.GONE);
                break;
            case "Cancelled":
                holder.AllactivityPaid.setVisibility(View.GONE);
                holder.AllactivityPending.setVisibility(View.GONE);
                holder.AllactivityCancelled.setVisibility(View.VISIBLE);
                holder.Allactivityrefund.setVisibility(View.GONE);
                holder.AllactivityOverdue.setVisibility(View.GONE);
                break;
            case "Refund":
                holder.AllactivityPaid.setVisibility(View.GONE);
                holder.AllactivityPending.setVisibility(View.GONE);
                holder.AllactivityCancelled.setVisibility(View.GONE);
                holder.Allactivityrefund.setVisibility(View.VISIBLE);
                holder.AllactivityOverdue.setVisibility(View.GONE);
                break;
            case "Overdue":
                holder.AllactivityPaid.setVisibility(View.GONE);
                holder.AllactivityPending.setVisibility(View.GONE);
                holder.AllactivityCancelled.setVisibility(View.GONE);
                holder.Allactivityrefund.setVisibility(View.GONE);
                holder.AllactivityOverdue.setVisibility(View.VISIBLE);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return Allactivitylist.size();
    }


    public class AllActivityRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView AllactivityRVName, AllactivityRVStd, AllactivityRVSection, AllactivityRVAmount, AllactivityRVDescription, AllactivityRVDate, AllactivityPending, Allactivityrefund, AllactivityCancelled, AllactivityPaid, AllactivityOverdue;
        ShapeableImageView all_ActivityrvImage;


        public AllActivityRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            AllactivityRVName = itemView.findViewById(R.id.all_activityRVname);
            AllactivityRVStd = itemView.findViewById(R.id.all_activityRVstd);
            AllactivityRVSection = itemView.findViewById(R.id.all_activityRVsection);
            AllactivityRVAmount = itemView.findViewById(R.id.all_activityRVamount);
            AllactivityRVDescription = itemView.findViewById(R.id.all_activityRVdiscriptionTV);
            AllactivityPending = itemView.findViewById(R.id.all_activity_pending);
            Allactivityrefund = itemView.findViewById(R.id.all_activity_refunded);
            AllactivityCancelled = itemView.findViewById(R.id.all_activity_cancelled);
            AllactivityPaid = itemView.findViewById(R.id.all_activity_paid);
            AllactivityOverdue = itemView.findViewById(R.id.all_activity_overdue);
            AllactivityRVDate = itemView.findViewById(R.id.all_activityRVdate);
            all_ActivityrvImage = itemView.findViewById(R.id.all_activityRVImage);


        }
    }
}
