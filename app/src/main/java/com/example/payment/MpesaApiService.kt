package com.example.payment

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MpesaApiService {
    @GET("oauth/v1/generate?grant_type=client_credentials")
    suspend fun getAccessToken(
        @Header("Authorization") authHeader: String
    ): Response<TokenResponse>

    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun initiateStkPush(
        @Header("Authorization") bearerToken: String,
        @Body request: StkPushRequest
    ): Response<StkPushResponse>
}
