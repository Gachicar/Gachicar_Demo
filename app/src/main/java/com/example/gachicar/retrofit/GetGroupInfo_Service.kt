package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface GetGroupInfo_Service {
    @GET("/api/group")
    fun getGroupInfo(): Call<GetGroupInfo>
}
