package com.example.gachicar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: KaKaoAuthViewModel
    private lateinit var loginStatusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(KaKaoAuthViewModel::class.java)
        loginStatusText = findViewById(R.id.login_status_text)

        viewModel.loginStatusMessage.observe(this, Observer { message ->
            loginStatusText.text = message
        })

        viewModel.isLoggedInLiveData.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                navigateToMainActivity()
            } else {
                loginStatusText.text = "로그인 상태: 로그아웃됨"
            }
        })

        findViewById<Button>(R.id.login_button).setOnClickListener {
            viewModel.kakaoLogin()
        }

        findViewById<Button>(R.id.logout_button).setOnClickListener {
            viewModel.kakaoLogout()
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.toString().startsWith("https://localhost:3000")) {
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    viewModel.sendAccessTokenToServer(code)
                }
            }
        }
    }
}
