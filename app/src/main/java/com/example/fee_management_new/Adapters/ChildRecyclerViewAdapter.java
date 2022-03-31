package com.example.fee_management_new.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.ActivateFragDirections;
import com.example.fee_management_new.AllStudentFragmentDirections;
import com.example.fee_management_new.IntegeratedBatchAFragment;
import com.example.fee_management_new.Modalclass.ChildModelclass;
import com.example.fee_management_new.R;

import java.util.ArrayList;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewholder> {
    Context context;
    public ArrayList<ChildModelclass> childModelclassArrayList;
    NavController navController;

    public ChildRecyclerViewAdapter(ArrayList<ChildModelclass> arrayList, Context context) {

        this.childModelclassArrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recyclerview_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, @SuppressLint("RecyclerView") int position) {
        ChildModelclass currentItem = childModelclassArrayList.get(position);
        holder.Section.setText(currentItem.getSection());
        holder.studentCount.setText(currentItem.getStudentCount());
        holder.courseName.setText(currentItem.getCoursename());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", String.valueOf(position));
                int id= currentItem.getId();
                String std = currentItem.getStd();
                String section = currentItem.getSection();
//                Fragment fragment = new IntegeratedBatchAFragment(id);
//                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
//                        R.anim.slide_in,  // enter
//                        R.anim.fade_out,  // exit
//                       R.anim.fade_in,   // popEnter
//                        R.anim.slide_out  // popExit
//                );
//                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, fragment);
////                fragmentTransaction.addToBackStack(String.valueOf(position));
//                fragmentTransaction.commit();
                NavDirections action = com.example.fee_management_new.AllStudentFragmentDirections.actionAllStudentFragmentToIntegeratedBatchAFragment(id,std,section);
                Navigation.findNavController(view).navigate(action);
//                Bundle bundle = new Bundle();
//                bundle.putString("s", String.valueOf(currentItem.getId()));
//                fragment.setArguments(bundle);
//                Log.i("child", "onClick: "+bundle.get("s"));

//                Navigation.findNavController(view).navigate(R.id.integeratedBatchAFragment);

            }
        });

    }

    @Override
    public int getItemCount() {
        return childModelclassArrayList.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder{
           TextView Section,courseName,studentCount;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
                Section = itemView.findViewById(R.id.section);
                courseName = itemView.findViewById(R.id.coursename);
                studentCount = itemView.findViewById(R.id.studentcount);
        }
    }





}
