package com.example.gachicar.retrofit

data class socialSignUpandLogin(
    val code: Long,
    val message: String,
    val data: Data,
)

data class Data(
    val type: String,
    val accessToken: String,
    val refreshToken: String,
)
