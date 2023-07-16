package org.project.nursecall.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final RetrofitAPI retrofitAPI = RetrofitClient.getRetrofit().create(RetrofitAPI.class);

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://167.99.69.223/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
