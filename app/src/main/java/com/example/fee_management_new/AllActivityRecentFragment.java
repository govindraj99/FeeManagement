package com.example.fee_management_new;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.AllActivitesRecentAdapter;
import com.example.fee_management_new.Adapters.FilterClassBottomAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.ClassName;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Modalclass.FilterClassBottomModel;
import com.example.fee_management_new.Modalclass.RecentActivitiesone;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllActivityRecentFragment extends Fragment {

    View view;
    private static final String TAG = "AllActivityRecentFragme";
    ArrayList<FilterClassBottomModel> filterClassBottomModels;
    ArrayList<Integer> stdId;
    RecyclerView filterClassBottomRecyclerView, AllactivityrecyclerView;
    private RecyclerView.LayoutManager FilterClassBottomLayoutManager;
    RecyclerView.Adapter filterClassBottomAdapter;
    ApiService apiService;
    ArrayList<String> StdName;
    ArrayList<ClassName> classNames;
    ArrayList<RecentActivitiesone> recentActivitiesoneArrayList;
    HashMap<String, ArrayList<ClassName>> listHashMap;
    LinearLayout FilterClasslayout;


    public AllActivityRecentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_activity_alltransaction, container, false);
        apiService = ApiClient.getLoginService();
        FilterClasslayout = view.findViewById(R.id.filterlayout);
        FilterClasslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterclassBottomSheet();
            }
        });
        getStandards();
        getAllTransaction();
        return view;

    }

    private void getAllTransaction() {
        Log.d(TAG, "getAllTransaction: checking");
        Call<GetAllTransactionResponse> getAllTransactionResponse = apiService.GET_ALL_TRANSACTION_RESPONSE_CALL(1, "true");
        Log.d(TAG, "getAllTransaction: Checking call");
        getAllTransactionResponse.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                Log.d(TAG, "onResponse: Inside on response");
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                recentActivitiesoneArrayList = new ArrayList<>();
                GetAllTransactionResponse getAllTransactionResponse1 = response.body();
                int length = getAllTransactionResponse1.getResponse().items.size();
                Log.i("length", "onResponse: " + length);
                if (length == 0) {

                } else {
                    for (int i = 0; i < length; i++) {

                        int stdId = getAllTransactionResponse1.getResponse().items.get(i).getStandardId();
                        if (stdId == -1) {
                            recentActivitiesoneArrayList.add(new RecentActivitiesone("",
                                    "",
                                    "",
                                    String.valueOf(getAllTransactionResponse1.getResponse().items.get(i).getAmountPayable()),
                                    getAllTransactionResponse1.getResponse().items.get(i).getDate(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getStatus(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getNote()));
                        } else {
                            recentActivitiesoneArrayList.add(new RecentActivitiesone(getAllTransactionResponse1.getResponse().items.get(i).getUser().getName(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getUser().getStudent().getStandard().getStd(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getUser().getStudent().getStandard().getSection(),
                                    String.valueOf(getAllTransactionResponse1.getResponse().items.get(i).getAmountPayable()),
                                    getAllTransactionResponse1.getResponse().items.get(i).getDate(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getStatus(),
                                    getAllTransactionResponse1.getResponse().items.get(i).getNote()));

                        }
                    }
                    Log.i(TAG, "onResponse:Size "+recentActivitiesoneArrayList.size());
//                ArrayList<Item2> item2ArrayList = getAllTransactionResponse1.getResponse().getItems();
//                Log.i(TAG, "onResponse: "+item2ArrayList.size());
//                recentActivitiesoneArrayList = new ArrayList<>();
//                Log.i(TAG, "onResponse: Name"+item2ArrayList.get(0).getUser().getName());
//                Log.i(TAG, "onResponse: Std"+item2ArrayList.get(0).getUser().getStudent().getStandard().getStd());
//                Log.i(TAG, "onResponse: Sec"+item2ArrayList.get(0).getUser().getStudent().getStandard().getSection());
//                Log.i(TAG, "onResponse: Amt"+item2ArrayList.get(0).getAmountPayable());
//                Log.i(TAG, "onResponse: Date"+item2ArrayList.get(0).getDate());
//                Log.i(TAG, "onResponse: Status"+item2ArrayList.get(0).getStatus());
//                Log.i(TAG, "onResponse: Note"+item2ArrayList.get(0).getNote());
//                for (int i=0;i<=40;i++){
//                    Log.i(TAG, "onResponse:Name "+i);
//                    Log.i(TAG, "Name: "+item2ArrayList.get(i).getUser().getName());
//                    recentActivitiesoneArrayList.add(new RecentActivitiesone(item2ArrayList.get(i).getUser().getName(),
//                                    item2ArrayList.get(i).getUser().getStudent().getStandard().getStd(),
//                                    item2ArrayList.get(i).getUser().getStudent().getStandard().getSection(),
//                                    String.valueOf(item2ArrayList.get(i).getAmountPayable()),
//                                    item2ArrayList.get(i).getDate(),item2ArrayList.get(i).getStatus(),item2ArrayList.get(i).getNote()));
//                    Log.i(TAG, "Date: "+item2ArrayList.get(i).getDate());
//                            i.getUser().getStudent().getStandard().getStd(),
//                            i.getUser().getStudent().getStandard().getSection(),
//                            String.valueOf(i.getAmountPayable()),
//                            i.getDate(),
//                            i.getStatus(),
//                            i.getNote()));
                }
//                Log.i(TAG, "onResponse: RecentActivitySize "+recentActivitiesoneArrayList.size());
                buildAllactivtyRecyclerView();

            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

            }
        });
    }

    private void buildAllactivtyRecyclerView() {
        AllactivityrecyclerView = view.findViewById(R.id.allactivities_RV);
        AllactivityrecyclerView.setHasFixedSize(true);
        AllactivityrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG, "buildAllactivtyRecyclerView: " + recentActivitiesoneArrayList.size());
        AllactivityrecyclerView.setAdapter(new AllActivitesRecentAdapter(getContext(), recentActivitiesoneArrayList));

    }

    private void filterclassBottomSheet() {
        view = getLayoutInflater().inflate(R.layout.filterclass_bottomsheet, null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        buildFiterClassRecylerView();
        bt.show();

    }

    private void getStandards() {
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
                filterClassBottomModels = new ArrayList<>();
                stdId = new ArrayList<>();
                StdName = new ArrayList<>();

                for (String s :
                        stdNames) {
                    StdName.add(s);
                }
                Log.i("Std", StdName.toString());
                Collections.sort(StdName);
                for (int i = 0; i < StdName.size(); i++) {
                    for (int j = 0; j < listHashMap.get(StdName.get(i)).size(); j++) {
                        filterClassBottomModels.add(new FilterClassBottomModel(listHashMap.get(StdName.get(i)).get(j).getStd(), listHashMap.get(StdName.get(i)).get(j).getSection(),listHashMap.get(StdName.get(i)).get(j).getId()));
                        stdId.add(listHashMap.get(StdName.get(i)).get(j).getId());
                    }
                }
//                for (String s :
//                        StdName) {
//                    filterClassBottomModels.add(new FilterClassBottomModel().);
//                }
                Log.i("Std sorted", StdName.toString());

            }

            @Override
            public void onFailure(Call<HashMap<String, ArrayList<ClassName>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildFiterClassRecylerView() {

        filterClassBottomRecyclerView = view.findViewById(R.id.filterRV);
        filterClassBottomRecyclerView.setHasFixedSize(true);
//        filterClassBottomAdapter = new FilterClassBottomAdapter(filterClassBottomModels, getContext());
        filterClassBottomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filterClassBottomRecyclerView.setAdapter(new FilterClassBottomAdapter(filterClassBottomModels, getContext(),stdId));
    }
}
