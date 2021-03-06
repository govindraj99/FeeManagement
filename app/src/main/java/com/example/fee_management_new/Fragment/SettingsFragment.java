package com.example.fee_management_new.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.SettingsRequest;
import com.example.fee_management_new.Api.SettingsResponse;
import com.example.fee_management_new.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private View view;
    private ApiService apiService;
    private RadioGroup radioGroup;
    private String paidBy;
    private Button updateSettingBtn;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInit();

    }

    private void apiInit() {
        apiService = ApiClient.getLoginService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        initWidgets();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb1) {
                    paidBy = "student";
                    //Toast.makeText(getContext(), "Fee by student", Toast.LENGTH_SHORT).show();
                } else {
                    paidBy = "institute";
                    //Toast.makeText(getContext(), "Fee by Institute", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsRequest settingsRequest = new SettingsRequest(paidBy);
                Call<SettingsResponse> settingsResponseCall = apiService.SETTINGS_RESPONSE_CALL(settingsRequest);
                settingsResponseCall.enqueue(new Callback<SettingsResponse>() {
                    @Override
                    public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "onResponse:not successful " + response.code());
                            Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                        }
                        SettingsResponse settingsResponse = response.body();
                        Toast.makeText(getContext(), "" + settingsResponse.getShow().getType(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<SettingsResponse> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void initWidgets() {
        radioGroup = view.findViewById(R.id.radioGroup);
        updateSettingBtn = view.findViewById(R.id.updateSettingBtn);
    }
}