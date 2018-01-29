package com.chisw.switchmymind.network;

import com.chisw.switchmymind.network.model.InputData;
import com.chisw.switchmymind.network.model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by user on 19.01.18.
 */

public interface NetworkApi {
    @Headers("Content-Type: application/json")
    @POST("/calculations")
    Call<Result> sendInputData(@Body InputData inputData);

    @Headers("Content-Type: application/json")
    @GET("/calculations")
    Call<Object> getResult();
}
