package com.example.fee_management_new;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fee_management_new.Adapters.ParentRecyclerViewAdapter;
import com.example.fee_management_new.Modalclass.ParentModal;

import java.util.ArrayList;


public class AllStudentFragment extends Fragment {
    RecyclerView ParentRecyclerview;
    private RecyclerView.LayoutManager parentLayoutManager;
    RecyclerView.Adapter ParentAdapter;
    ArrayList<ParentModal> parentModelArrayList;
    View view;


    public AllStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_student, container, false);
      buildRecyclerViewAllStudent();
        return view;
    }

    private void buildRecyclerViewAllStudent() {
        parentModelArrayList = new ArrayList<>();
        parentModelArrayList.add(new ParentModal("kishan"));

        ParentRecyclerview = view.findViewById(R.id.rv_parent);
        ParentRecyclerview.setHasFixedSize(true);
        parentLayoutManager = new LinearLayoutManager(getContext());
        ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, getContext());
        ParentRecyclerview.setLayoutManager(parentLayoutManager);
        ParentRecyclerview.setAdapter(ParentAdapter);
        ParentAdapter.notifyDataSetChanged();

    }
}