package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fee_management_new.Modalclass.AllStudentsIntegratedModal;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class AllStudentsIntegratedAdapter extends RecyclerView.Adapter<AllStudentsIntegratedAdapter.AllStudentsIntegratedViewHolder> {
    ArrayList<AllStudentsIntegratedModal> allStudentsModal;
    Context context;
    FragmentManager fragmentManager;
    ArrayList<String> name=new ArrayList<>();
    AdapterView.OnItemClickListener onItemClickListener;
    private static final String baseUrlForImages = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";

    public AllStudentsIntegratedAdapter(ArrayList<AllStudentsIntegratedModal> allStudentsModal, Context context, FragmentManager fragmentManager) {
        this.allStudentsModal = allStudentsModal;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public AllStudentsIntegratedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_students_integrated_card, parent, false);
        AllStudentsIntegratedViewHolder allStudentsIntegratedViewHolder = new AllStudentsIntegratedViewHolder(view,onItemClickListener);
        return allStudentsIntegratedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentsIntegratedViewHolder holder, int position) {
        AllStudentsIntegratedModal currentData = allStudentsModal.get(position);
        holder.transactionTV.setText(currentData.getTransaction());
        holder.rollNoTV.setText(currentData.getRollno());
        Glide.with(context).load(baseUrlForImages+currentData.getImage()).into(holder.Image);
        holder.namesItegrate.setText(currentData.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                name.add(currentData.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return allStudentsModal.size();
    }

    public class AllStudentsIntegratedViewHolder extends RecyclerView.ViewHolder {
        TextView namesItegrate, rollNoTV, transactionTV;
        CheckBox checkBox;
        ImageView Image;

        public AllStudentsIntegratedViewHolder(@NonNull View itemView, AdapterView.OnItemClickListener onItemClickListener) {
            super(itemView);
            namesItegrate = itemView.findViewById(R.id.namesintegrate);
            rollNoTV = itemView.findViewById(R.id.rollNo_tv);
            transactionTV = itemView.findViewById(R.id.transactioncount_tv);
            checkBox = itemView.findViewById(R.id.cardCheckbox);
            Image = itemView.findViewById(R.id.inte_all_students_img);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        namesItegrate.getText().toString();

                    }
                }
            });
        }
    }
}
