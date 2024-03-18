//package com.example.gachicar
//
//import android.content.ContentValues.TAG
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.kakao.sdk.auth.model.OAuthToken
//import com.kakao.sdk.user.UserApiClient
//
//class RedirectUriActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // 인증 코드 추출 및 처리
//        val uri = intent.data
//        if (uri != null && uri.toString().startsWith("https://localhost:3000")) {
//            val code = uri.getQueryParameter("code")
//
//            if (code != null) {
//                // 여기서 code를 사용하여 액세스 토큰을 요청
//                requestAccessToken(code)
//            }
//        }
//    }
//
//    private fun requestAccessToken(code: String) {
//        UserApiClient.instance.issueAccessToken(authCode = code) { token, error ->
//            if (error != null) {
//                Log.e(TAG, "Access token issue failed", error)
//            } else if (token != null) {
//                Log.i(TAG, "Access token is issued: ${token.accessToken}")
//
//                // 액세스 토큰을 이용한 추가 작업 (예: 메인 액티비티로 이동)
//                navigateToMainActivityWithToken(token)
//            }
//        }
//    }
//
//    private fun navigateToMainActivityWithToken(token: OAuthToken) {
//        val intent = Intent(this, MainActivity::class.java).apply {
//            // 인텐트에 액세스 토큰 정보 추가
//            putExtra("access_token", token.accessToken)
//        }
//        startActivity(intent)
//        finish()
//    }
//}