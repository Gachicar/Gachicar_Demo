package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SocialSignUpandLoginService {
    @Headers("Content-Type: application/json")
    @POST("/api/auth/social")
    fun socialSignUpAndLogin(
        @Body requestData: SocialSignUpAndLoginRequest
    ): Call<SocialSignUpandLoginResponse>
}

data class SocialSignUpAndLoginRequest(
    val accessToken: String,
    val type: String = "kakao" // 'type' 필드는 필요에 따라 설정
)

data class SocialSignUpandLoginResponse(
    val code: Long,
    val message: String,
    val data: SocialLoginData // 'Data' 클래스의 이름을 변경했습니다.
)

data class SocialLoginData( // 'Data' 클래스의 이름을 'SocialLoginData'로 변경했습니다.
    val type: String,
    val accessToken: String,
    val refreshToken: String
)
