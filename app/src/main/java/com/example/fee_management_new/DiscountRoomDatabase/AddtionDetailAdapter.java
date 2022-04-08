package com.example.fee_management_new.DiscountRoomDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.R;

import java.util.List;

public class AddtionDetailAdapter extends RecyclerView.Adapter<AddtionDetailAdapter.AdditionDetailViewHolder> {
    List<AdditionData> additionDataList;
    private Context context;
    private RoomDBTwo roomDBTwo;

    public AddtionDetailAdapter(List<AdditionData> additionDataList, Context context) {
        this.additionDataList = additionDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdditionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.additiondetailrow,parent,false);
        AdditionDetailViewHolder additionDetailViewHolder = new AdditionDetailViewHolder(view);
        return additionDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionDetailViewHolder holder, int position) {
        AdditionData additionData = additionDataList.get(position);
        roomDBTwo = RoomDBTwo.getInstance(context);
        holder.rv_AdditionTitle.setText(additionData.getAdditionDetail());
        holder.rv_AdditionAmount.setText(additionData.getAdditionAmount());


    }

    @Override
    public int getItemCount() {
        return additionDataList.size();
    }

    public class AdditionDetailViewHolder extends RecyclerView.ViewHolder{
        TextView rv_AdditionTitle,rv_AdditionAmount;

        public AdditionDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_AdditionTitle = itemView.findViewById(R.id.pdt_additiontitle);
            rv_AdditionAmount = itemView.findViewById(R.id.pdt_additionamount);
        }
    }

}
