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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Adapters.AllActivitesRecentAdapter;
import com.example.fee_management_new.Adapters.AllActivitiesTwoAdapter;
import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Modalclass.AllActivitesTwoModal;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllActivitiesTwoFragment extends Fragment {
    LinearLayout issueDate,status;
    RecyclerView allActivitytwoRecyclerView;
    int sectionid;
    TextView issueDataeTV;
    View view;
    ArrayList<AllActivitesTwoModal> allActivitesTwoModals;
    ApiService apiService;
    private static final String TAG = "AllActivitiesTwoFragmen";


    public AllActivitiesTwoFragment() {
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
        apiService = ApiClient.getLoginService();
        sectionid = AllActivitiesTwoFragmentArgs.fromBundle(getArguments()).getId();
        Log.i("Sectionid", "onCreateView: " + sectionid);



        view = inflater.inflate(R.layout.fragment_all_activities, container, false);
        initWidgets();
        getAllTransaction();
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusBttomsheet();
            }
        });
        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                issueDateBottomsheet();
            }
        });
        return view;
    }

    private void getAllTransaction() {
        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.AllActivityTransactions(sectionid);
        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
            @Override
            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse: "+response.code());
                }
                GetAllTransactionResponse getAllTransactionResponse = response.body();
               int length = getAllTransactionResponse.getResponse().items.size();
               allActivitesTwoModals = new ArrayList<>();
               if (length == 0){

               }else {
                   for (int i = 0;i<length;i++){
                     allActivitesTwoModals.add(new AllActivitesTwoModal(getAllTransactionResponse.getResponse().items.get(i).getUser().getName(),String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getUser().getStudent().getRollNo()),String.valueOf(getAllTransactionResponse.getResponse().items.get(i).getAmountPayable()),getAllTransactionResponse.getResponse().items.get(i).getNote(),getAllTransactionResponse.getResponse().items.get(i).getDate(),getAllTransactionResponse.getResponse().items.get(i).getStatus()));
                   }
               }
               buildRecylerviewforAllActitvitytwo();

                    


            }

            @Override
            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void buildRecylerviewforAllActitvitytwo() {
        allActivitytwoRecyclerView = view.findViewById(R.id.allActivityRecyclerView);
        allActivitytwoRecyclerView.setHasFixedSize(true);
        allActivitytwoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allActivitytwoRecyclerView.setAdapter(new AllActivitiesTwoAdapter(getContext(),allActivitesTwoModals));


    }

    private void initWidgets() {
        issueDate = view.findViewById(R.id.issuedate_layout);
        issueDataeTV = view.findViewById(R.id.iussuedate_tv);
        status = view.findViewById(R.id.status_layout);
    }

    private void statusBttomsheet() {
        view = getLayoutInflater().inflate(R.layout.status_bottomsheet, null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
    }

    private void issueDateBottomsheet() {
        view = getLayoutInflater().inflate(R.layout.issuedate_bottomsheet, null);
        BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        bt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bt.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bt.setContentView(view);
        bt.setCanceledOnTouchOutside(true);
        bt.getWindow().setGravity(Gravity.BOTTOM);
        bt.setCanceledOnTouchOutside(true);
        bt.show();
    }
}