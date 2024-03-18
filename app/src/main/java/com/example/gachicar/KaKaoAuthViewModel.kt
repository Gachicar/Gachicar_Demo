package com.example.gachicar

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gachicar.retrofit.RetrofitConnection
import com.example.gachicar.retrofit.SocialSignUpAndLoginRequest
import com.example.gachicar.retrofit.SocialSignUpandLoginResponse
import com.example.gachicar.retrofit.SocialSignUpandLoginService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KaKaoAuthViewModel(application: Application) : AndroidViewModel(application) {
    val loginStatusMessage = MutableLiveData<String>()
    val isLoggedInLiveData = MutableLiveData<Boolean>()

    init {
        checkIsLoggedIn()
    }

    fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(getApplication<Application>().applicationContext)) {
            UserApiClient.instance.loginWithKakaoTalk(getApplication<Application>().applicationContext) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(getApplication<Application>().applicationContext) { token, error ->
                handleLoginResult(token, error)
            }
        }
    }

    fun kakaoLogout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("KakaoLogout", "카카오 로그아웃 실패", error)
            } else {
                Log.i("KakaoLogout", "카카오 로그아웃 성공")
                isLoggedInLiveData.postValue(false)
                loginStatusMessage.postValue("로그아웃 되었습니다.")
            }
        }
    }


    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            Log.e("KakaoLogin", "카카오 로그인 실패", error)
            loginStatusMessage.postValue("로그인 실패")
        } else if (token != null) {
            Log.i("KakaoLogin", "카카오 로그인 성공, 토큰: ${token.accessToken}")
            sendAccessTokenToServer(token.accessToken)
        }
    }

    private fun checkIsLoggedIn() {
        UserApiClient.instance.me { user, error ->
            isLoggedInLiveData.postValue(user != null)
        }
    }
    fun sendAccessTokenToServer(accessToken: String) {
        val requestData = SocialSignUpAndLoginRequest(accessToken = accessToken)
        viewModelScope.launch {
            RetrofitConnection.getInstance().create(SocialSignUpandLoginService::class.java)
                .socialSignUpAndLogin(requestData).enqueue(object:
                    Callback<SocialSignUpandLoginResponse> {
                    override fun onResponse(call: Call<SocialSignUpandLoginResponse>, response: Response<SocialSignUpandLoginResponse>) {
                        if (response.isSuccessful) {
                            loginStatusMessage.postValue("로그인 성공: ${response.body()?.message}")
                            isLoggedInLiveData.postValue(true)
                        } else {
                            loginStatusMessage.postValue("로그인 실패: ${response.errorBody()?.string()}")
                            isLoggedInLiveData.postValue(false)
                        }
                    }

                    override fun onFailure(call: Call<SocialSignUpandLoginResponse>, t: Throwable) {
                        loginStatusMessage.postValue("네트워크 오류: ${t.localizedMessage}")
                        isLoggedInLiveData.postValue(false)
                    }
                })
        }
    }
}