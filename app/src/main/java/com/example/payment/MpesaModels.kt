package com.example.payment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val expiresIn: String
)

@JsonClass(generateAdapter = true)
data class StkPushRequest(
    @Json(name = "BusinessShortCode") val businessShortCode: String,
    @Json(name = "Password") val password: String,
    @Json(name = "Timestamp") val timestamp: String,
    @Json(name = "TransactionType") val transactionType: String = "CustomerPayBillOnline",
    @Json(name = "Amount") val amount: String,
    @Json(name = "PartyA") val partyA: String,
    @Json(name = "PartyB") val partyB: String,
    @Json(name = "PhoneNumber") val phoneNumber: String,
    @Json(name = "CallBackURL") val callBackURL: String,
    @Json(name = "AccountReference") val accountReference: String,
    @Json(name = "TransactionDesc") val transactionDesc: String
)

@JsonClass(generateAdapter = true)
data class StkPushResponse(
    @Json(name = "MerchantRequestID") val merchantRequestID: String?,
    @Json(name = "CheckoutRequestID") val checkoutRequestID: String?,
    @Json(name = "ResponseCode") val responseCode: String?,
    @Json(name = "ResponseDescription") val responseDescription: String?,
    @Json(name = "CustomerMessage") val customerMessage: String?,
    @Json(name = "errorMessage") val errorMessage: String?,
    @Json(name = "errorCode") val errorCode: String?
)
