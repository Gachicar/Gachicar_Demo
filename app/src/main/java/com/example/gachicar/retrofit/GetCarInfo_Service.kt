package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.GET


interface GetCarInfo_Service {
    @GET("/api/car")
    fun getCarHomeInfo(): Call<getCarInfo>

}
