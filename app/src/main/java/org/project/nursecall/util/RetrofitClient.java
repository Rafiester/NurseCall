package org.project.nursecall.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final RetrofitAPI retrofitAPI = RetrofitClient.getRetrofit().create(RetrofitAPI.class);

    public static Retrofit getRetrofit() {
//        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
//                    Request request = chain.request().newBuilder().build();
//                    return chain.proceed(request);
//                }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .build();

        return new Retrofit.Builder()
                .baseUrl("http://192.168.18.4/nursecall/")
                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient)
                .build();
    }
}
