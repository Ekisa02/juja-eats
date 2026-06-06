package com.example.payment

import android.util.Base64
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MpesaRepository {

    // IMPORTANT: Storing API secrets directly in the app (even via BuildConfig accessed from secrets panel)
    // is strongly discouraged for production apps. Malicious users can decompile APKs to extract them.
    // For production, you should migrate the token generation and STK push logic to a secure backend proxy
    // (e.g. Firebase Cloud Functions).
    // The keys below will be read securely from AI Studio Secrets during development.
    private val consumerKey = com.example.BuildConfig.MPESA_CONSUMER_KEY
    private val consumerSecret = com.example.BuildConfig.MPESA_CONSUMER_SECRET
    private val passkey = com.example.BuildConfig.MPESA_PASSKEY
    
    // Safaricom Sandbox Base URL. Switch to live URL for production.
    private val baseUrl = "https://sandbox.safaricom.co.ke/"

    private val apiService: MpesaApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MpesaApiService::class.java)
    }

    suspend fun initiateStkPush(
        businessShortCode: String,
        amount: String,
        phoneNumber: String,
        callbackUrl: String,
        accountReference: String,
        transactionDesc: String
    ): Result<StkPushResponse> {
        return try {
            // 1. Generate Auth Header
            val authBytes = "$consumerKey:$consumerSecret".toByteArray(Charsets.UTF_8)
            val authHeader = "Basic ${Base64.encodeToString(authBytes, Base64.NO_WRAP)}"

            // 2. Fetch Access Token
            val tokenResponse = apiService.getAccessToken(authHeader)
            val accessToken = tokenResponse.body()?.accessToken
                ?: return Result.failure(Exception("Failed to fetch access token"))

            // 3. Generate Timestamp & Password
            val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
            val rawPassword = "$businessShortCode$passkey$timestamp"
            val password = Base64.encodeToString(rawPassword.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

            // 4. Create Request
            val request = StkPushRequest(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                amount = amount,
                partyA = phoneNumber,
                partyB = businessShortCode,
                phoneNumber = phoneNumber, // User's phone number as 254...
                callBackURL = callbackUrl,
                accountReference = accountReference,
                transactionDesc = transactionDesc
            )

            // 5. Send STK Push
            val bearerToken = "Bearer $accessToken"
            val mpesaResponse = apiService.initiateStkPush(bearerToken, request)

            if (mpesaResponse.isSuccessful && mpesaResponse.body() != null) {
                Result.success(mpesaResponse.body()!!)
            } else {
                Result.failure(Exception(mpesaResponse.errorBody()?.string() ?: "STK Push failed"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
