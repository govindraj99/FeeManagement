package com.example.fee_management_new.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    String token = "Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEzMywicGhvbmUiOiIrOTE5ODEyMTY4NTk5IiwidXJsIjoidGVzdC50aGVjbGFzc3Jvb20uYml6Iiwib3JnSWQiOiI0Y2IyNTA5ZC03MGY1LTQzNWUtODc5Mi1kMjQ5Mzc3NDNiNTMiLCJicm93c2VyTG9naW5Db2RlIjoiKzkxOTgxMjE2ODU5OTExMzM3Y2ZiOWE5Ny1iN2FlLTRhMDctYjI2OS0yNmYyZDk3NDczYjUiLCJkZXZpY2VMb2dpbkNvZGUiOm51bGwsImlhdCI6MTY0NjgwMzIxM30.dg4fn5RhRFjohWB94Ao3vHYgXbYpOBBK5CRsZf45OEQ";
    String link = "orgurl:test.theclassroom.biz";

    @Headers({token, link})
    @GET("feev2")
    Call<OverViewResponse> OVER_VIEW_RESPONSE_CALL(@QueryMap Map<String, String> param);


    @Headers({token, link})
    @GET("feev2/byStandards")
    Call<HashMap<String, ArrayList<ClassName>>> GROUP_STANDRAD_RESPONSE_CALL();


    @Headers({token, link})
    @GET("feev2/byStandards")
    Call<HashMap<String, ArrayList<ClassName>>> GetClassResponse();

    @Headers({token, link})
    @POST("feev2")
    Call<ResponseGeneratePaymentRequestIndividual> GENERATE_PAYMENT_REQUEST_INDIVIDUAL_CALL(@Body RequestGeneratePaymentRequestIndividual requestGeneratePaymentRequestIndividual);


    @Headers({token, link})
    @GET("feev2/allTransactions")
    Call<GetAllTransactionResponse> GET_ALL_TRANSACTION_RESPONSE_CALL(@Query("page") int pageNo, @Query("allRecords") String allRecords);

    @Headers({token, link})
    @GET("feev2/allTransactions")
    Call<GetAllTransactionResponse> CheckBoxResponse(@Query("section") Integer[] section);

    @Headers({token, link})
    @GET("feev2/allTransactions")
    Call<GetAllTransactionResponse> AllActivityTransactions(@Query("section[]") int sectionID);

    @Headers({token, link})
    @GET("feev2/getUser")
    Call<List<GetUserInAClassResponse>> GET_USER_IN_A_CLASS_RESPONSE_CALL(@Query("standardId") int stdId, @Query("search") String search);

    @Headers({token, link})
    @GET("feev2/transactions-by-user/{stdId}/{userId}")
    Call<List<TransactionByAUserResponse>> TRANSACTION_BY_A_USER_RESPONSE_CALL(@Path("stdId") int stdID, @Path("userId") int userId);

    @Headers({token,link})
    @POST("feev2/update-offline-transactions")
    Call<UpdateOfflineTransactionResponse> UPDATE_OFFLINE_TRANSACTION_RESPONSE_CALL(@Body UpdateOfflineTransactionRequest updateOfflineTransactionRequest);

    @Headers({token,link})
    @POST("feev2")
    Call<GenerateNewOfflinePaymentResponse> GENERATE_NEW_OFFLINE_PAYMENT_RESPONSE_CALL(@Body GenerateNewOfflinePaymentRequest generateNewOfflinePaymentRequest);
}

