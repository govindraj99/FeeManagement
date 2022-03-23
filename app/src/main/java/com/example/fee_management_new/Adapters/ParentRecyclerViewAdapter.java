package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Modalclass.ChildModelclass;
import com.example.fee_management_new.Modalclass.ParentModal;
import com.example.fee_management_new.R;

import java.util.ArrayList;


public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder> {
        private ArrayList<ParentModal> parentModelArrayList;
        public Context cxt;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView category;
            public RecyclerView childRecyclerView;

            public MyViewHolder(View itemView) {
                super(itemView);

                category = itemView.findViewById(R.id.stdTV);
                childRecyclerView = itemView.findViewById(R.id.chil_rv);
            }
        }

        public ParentRecyclerViewAdapter(ArrayList<ParentModal> exampleList, Context context) {
            this.parentModelArrayList = exampleList;
            this.cxt = context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paraent_recyclerview_items, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return parentModelArrayList.size();
        }



    @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            ParentModal currentItem = parentModelArrayList.get(position);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false);
            holder.childRecyclerView.setLayoutManager(layoutManager);
            holder.childRecyclerView.setHasFixedSize(true);
            holder.category.setText(currentItem.getStdClass());
            ArrayList<ChildModelclass> arrayList = new ArrayList<>();

            // added the first child row
            if (parentModelArrayList.get(position).getStdClass().equals("kishan")) {
                arrayList.add(new ChildModelclass("selenium","A","7"));
                arrayList.add(new ChildModelclass("maths","B","45"));
                arrayList.add(new ChildModelclass( "puc","c","56"));
                arrayList.add(new ChildModelclass( "wed","X","df"));
                arrayList.add(new ChildModelclass( "don","v","2"));
            }

            // added in second child row
            ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(arrayList,holder.childRecyclerView.getContext());
            holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
        }
}
