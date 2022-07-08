package com.example.fee_management_new.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ParentRecyclerViewAdapter ParentAdapter;
    ArrayList<ParentModal>parentModelArrayList;
    ApiService apiService;
    View view;
    androidx.appcompat.widget.SearchView searchView;
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
        setHasOptionsMenu(true);
        apiService = ApiClient.getLoginService();
        //searchView = view.findViewById(R.id.search);
        //  buildRecyclerViewAllStudent();
        getStdResponse();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.Seachview);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("search class");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==R.id.Seachview)
//            Toast.makeText(getContext(), "Search CLicked", Toast.LENGTH_LONG).show();
//        return super.onOptionsItemSelected(item);
//    }

    private void filter(String s) {
        ArrayList<ParentModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (ParentModal item : parentModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStdClass().toLowerCase().contains(s.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
                ParentAdapter.filterList(filteredlist);
        }
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
//                Toast.makeText(getContext(), stdNames.toString(), Toast.LENGTH_LONG).show();
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
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        filter(newText);
//                        return false;
//                    }
//                });
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