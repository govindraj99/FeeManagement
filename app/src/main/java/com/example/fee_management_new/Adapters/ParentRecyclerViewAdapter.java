package com.example.fee_management_new.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Modalclass.ChildModelclass;
import com.example.fee_management_new.Modalclass.ParentModal;
import com.example.fee_management_new.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "Yashwanth";
    private ArrayList<ParentModal> parentModelArrayList;
    private ArrayList<String> stdName;
    private Context cxt;
    private HashMap<String, ArrayList<ClassName>> listHashMap;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public RecyclerView childRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.stdTV);
            childRecyclerView = itemView.findViewById(R.id.chil_rv);
        }
    }

    public ParentRecyclerViewAdapter(ArrayList<ParentModal> exampleList, Context context, ArrayList<String> stdName, HashMap<String, ArrayList<ClassName>> listHashMap) {
        this.parentModelArrayList = exampleList;
        this.cxt = context;
        this.stdName = stdName;
        this.listHashMap = listHashMap;
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
        Log.i(TAG, "onBindViewHolder: " + position);
        for (int i = 0; i < stdName.size(); i++) {
            for (int j = 0; j < listHashMap.get(stdName.get(i)).size(); j++) {
                if (parentModelArrayList.get(position).getStdClass().equals(listHashMap.get(stdName.get(i)).get(j).getStd())) {
                    arrayList.add(new ChildModelclass(listHashMap.get(stdName.get(i)).get(j).getSection(), listHashMap.get(stdName.get(i)).get(j).getSection(), String.valueOf(listHashMap.get(stdName.get(i)).get(j).getStudentsCount()),listHashMap.get(stdName.get(i)).get(j).getId(),listHashMap.get(stdName.get(i)).get(j).getStd()));
                }
            }
        }
        // added in second child row
        ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(arrayList, holder.childRecyclerView.getContext());
        holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
    }
}
