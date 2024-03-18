package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ReserveData_Service {
    @GET("/api/report/reserve")
    fun getReserveData(): Call<ReserveData>
}