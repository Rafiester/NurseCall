package org.project.nursecall.util;

import org.project.nursecall.data.LoginData;
import org.project.nursecall.data.PasienData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @GET("index.php")
    Call<ArrayList<PasienData>> getPasien();

    @POST("index.php")
    Call<LoginData> postLogin(@Body LoginData loginData);

}
