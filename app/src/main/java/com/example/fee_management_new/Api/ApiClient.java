package com.example.fee_management_new.Api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit=null;
    public static final String URL="https://test.theclassroom.biz/api/";

    public static  Retrofit getRetrofit(){

        if (retrofit == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;

    }

    public static ApiService getLoginService(){

        ApiService apiService = getRetrofit().create(ApiService.class);
        return apiService;
    }

}

