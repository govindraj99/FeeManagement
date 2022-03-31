package com.example.fee_management_new.Adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.GetAllTransactionResponse;
import com.example.fee_management_new.Modalclass.FilterClassBottomModel;
import com.example.fee_management_new.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterClassBottomAdapter extends RecyclerView.Adapter<FilterClassBottomAdapter.FilterClassBottomViewHolder> {
    ArrayList<FilterClassBottomModel> filterClassBottomModels;
    Context context;
    ArrayList<Integer> section = new ArrayList<>();
    ApiService apiService;

    private static final String TAG = "FilterClassBottomAdapte";
    ArrayList<Integer> stdId;

    public FilterClassBottomAdapter(ArrayList<FilterClassBottomModel> filterClassBottomModels, Context context, ArrayList<Integer> stdId) {
        this.filterClassBottomModels = filterClassBottomModels;
        this.context = context;
        this.stdId = stdId;
    }

    @NonNull
    @Override
    public FilterClassBottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filterclass_recycler_card,parent,false);
       FilterClassBottomViewHolder filterClassBottomViewHolder = new FilterClassBottomViewHolder(view);
        return filterClassBottomViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterClassBottomViewHolder holder, int position) {
        FilterClassBottomModel currentModal = filterClassBottomModels.get(position);
        holder.FCRVStd.setText(currentModal.getStd());
        holder.FCRVsection.setText(currentModal.getSection());



    }

    @Override
    public int getItemCount() {
        return filterClassBottomModels.size();
    }

    public class FilterClassBottomViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        TextView FCRVStd,FCRVsection;

        public FilterClassBottomViewHolder(@NonNull View itemView) {
            super(itemView);
            FCRVStd = itemView.findViewById(R.id.fcrv_std);
            FCRVsection = itemView.findViewById(R.id.fcrv_section);
            checkBox = itemView.findViewById(R.id.fcrv_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        section.add(stdId.get(getAdapterPosition()));
                        Log.i(TAG, "Stdid  "+section);
                        Integer[] sections = section.toArray(new Integer[0]);
                        Log.i(TAG, "onCheckedChanged:checked at "+getAdapterPosition());
                        Call<GetAllTransactionResponse> allTransactionResponseCall = apiService.CheckBoxResponse(sections);
                        allTransactionResponseCall.enqueue(new Callback<GetAllTransactionResponse>() {
                            @Override
                            public void onResponse(Call<GetAllTransactionResponse> call, Response<GetAllTransactionResponse> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(context, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                }
                                Log.i(TAG, "onResponse Successful ");
//                                for (:
//                                     ) {
//
//                                }
                            }

                            @Override
                            public void onFailure(Call<GetAllTransactionResponse> call, Throwable t) {

                            }
                        });


                    }

                }
            });
        }
    }


}
