package com.example.fee_management_new.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.ParentRecyclerViewAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Api.GroupStandradResponse;
import com.example.fee_management_new.Modalclass.ParentModal;
import com.example.fee_management_new.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllStudentFragment extends Fragment {
    RecyclerView ParentRecyclerview;
    private RecyclerView.LayoutManager parentLayoutManager;
    RecyclerView.Adapter ParentAdapter;
    ArrayList<ParentModal>parentModelArrayList;
    ApiService apiService;
    View view;
    ArrayList<String> stdName;
    ArrayList<ClassName> classNames;
    HashMap<String, ArrayList<ClassName>> listHashMap;


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
        apiService = ApiClient.getLoginService();
        //  buildRecyclerViewAllStudent();
        getStdResponse();
        return view;
    }

    private void getStdResponse() {
        Call<HashMap<String, ArrayList<ClassName>>> hashMapCall = apiService.GROUP_STANDRAD_RESPONSE_CALL();
        hashMapCall.enqueue(new Callback<HashMap<String, ArrayList<ClassName>>>() {
            @Override
            public void onResponse(Call<HashMap<String, ArrayList<ClassName>>> call, Response<HashMap<String, ArrayList<ClassName>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
                listHashMap = response.body();
                Set<String> stdNames = listHashMap.keySet();
                //ArrayList<String> stdNameString = (ArrayList<String>) stdNames;
                Toast.makeText(getContext(), stdNames.toString(), Toast.LENGTH_LONG).show();
                Log.i("std", listHashMap.keySet().toString());
                parentModelArrayList = new ArrayList<>();
                stdName = new ArrayList<>();

                for (String s :
                        stdNames) {
                    stdName.add(s);
                }
                Log.i("Std", stdName.toString());
                Collections.sort(stdName);
                for (String s :
                        stdName) {
                    parentModelArrayList.add(new ParentModal(s));
                }
                Log.i("Std sorted", stdName.toString());
                buildRecyclerViewAllStudent(stdName);
            }

            @Override
            public void onFailure(Call<HashMap<String, ArrayList<ClassName>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildRecyclerViewAllStudent(ArrayList<String> sorted) {

        ParentRecyclerview = view.findViewById(R.id.rv_parent);
        ParentRecyclerview.setHasFixedSize(true);
        parentLayoutManager = new LinearLayoutManager(getContext());
        ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, getContext(), sorted, listHashMap);
        ParentRecyclerview.setLayoutManager(parentLayoutManager);
        ParentRecyclerview.setAdapter(ParentAdapter);
        ParentAdapter.notifyDataSetChanged();

    }
}