package com.example.fee_management_new.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fee_management_new.Api.ApiClient;
import com.example.fee_management_new.Api.ApiService;
import com.example.fee_management_new.Api.SettingsRequest;
import com.example.fee_management_new.Api.SettingsResponse;
import com.example.fee_management_new.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private View view;
    private ApiService apiService;
    private RadioGroup radioGroup;
    private String paidBy, Text;
    private Button updateSettingBtn;
    private TextView settingsType;
    private Dialog dialog;

//    public interface SendText {
//    void onGetText(String text);
//    }
//    private SendText sendText;

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
                    Text = "Processing fee payable by student";
                    Log.i(TAG, "onCheckedChanged:RB1 " + paidBy);
//                    Toast.makeText(getContext(), "Fee by student", Toast.LENGTH_SHORT).show();
                }
                if (i == R.id.rb2) {
                    paidBy = "institute";
                    Text = "Processing fee payable by institute";
                    Log.i(TAG, "onCheckedChanged:RB2 " + paidBy);
//                    Toast.makeText(getContext(), "Fee by Institute", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GeneratePaymentRequest generatePaymentRequest = new GeneratePaymentRequest();
//                Bundle bundle = new Bundle();
//                bundle.putString("payby", Text);
//                Log.i(TAG, "onCreateView: payby" + Text);
//                generatePaymentRequest.setArguments(bundle);
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
                        customToast();
                        ActivateFrag activateFrag = new ActivateFrag();
                        NavHostFragment.findNavController(getParentFragment().getChildFragmentManager().findFragmentById(R.id.activity_main_nav_host_fragment)).navigate(R.id.action_settings2_to_activateFrag);
                    }


                    @Override
                    public void onFailure(Call<SettingsResponse> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

//        NavHostFragment.findNavController(Objects.requireNonNull())
        return view;
    }

    private void customToast() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.setting_dialouge, (ViewGroup) view.findViewById (R.id.rootToast));
        TextView toastText = layout.findViewById(R.id.settigs_typetv);
        toastText.setText(Text);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.TOP,2,20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    private void initWidgets() {
        radioGroup = view.findViewById(R.id.radioGroup);
        updateSettingBtn = view.findViewById(R.id.updateSettingBtn);

    }
}