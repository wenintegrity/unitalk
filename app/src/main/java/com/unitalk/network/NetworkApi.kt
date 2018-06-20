package com.unitalk.network

import com.unitalk.network.model.CardsClickedData
import com.unitalk.network.model.FirstSamplingResultsData
import com.unitalk.network.model.NextSamplingResultsData
import com.unitalk.network.model.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApi {
    @POST("/auth")
    fun sendAuthUserData(@Body userData: UserData): Call<Void>

    @POST("/calculations")
    fun sendFirstSampleUserData(@Body userData: UserData): Call<FirstSamplingResultsData>

    @POST("/calculations/{sessionId}")
    fun sendNextSampleUserData(@Path("sessionId") sessionId: String, @Body userData: UserData): Call<NextSamplingResultsData>

    @PATCH("/calculations/{calculationId}/pictures")
    fun sendUserCards(@Path("calculationId") calculationId: String, @Body cardsClickedData: CardsClickedData): Call<Void>
}