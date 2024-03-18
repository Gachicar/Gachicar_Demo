package com.example.gachicar.retrofit

data class MostUser(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val userId: Int,
        val userName: String
    )
}