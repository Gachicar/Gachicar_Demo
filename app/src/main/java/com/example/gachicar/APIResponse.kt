package com.example.gachicar

// ApIResponse.kt
data class APIResponse(
    val code: Int,
    val message: String,
    val data: UserData
)

data class UserData(
    val type: String,
    val accessToken: String,
    val refreshToken: String
)
