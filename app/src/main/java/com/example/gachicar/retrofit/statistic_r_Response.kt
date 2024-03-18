package com.example.gachicar.retrofit

data class statistic_r_Response(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val fuelType: String,
        val oilStatus: Int
    )
}