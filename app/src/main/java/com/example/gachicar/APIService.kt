package com.example.gachicar

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    @POST("/api/auth/social")
    fun sendAccessToken(@Header("Authorization") accessToken: String): Call<APIResponse>
}
