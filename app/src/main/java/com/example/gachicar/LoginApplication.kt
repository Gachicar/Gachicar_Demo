package com.example.gachicar

import android.app.Application
import com.kakao.sdk.common.KakaoSdk


class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "28a522b262a00e932f756d5de1b698a7")
    }
}