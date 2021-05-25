package com.example.bagimodal.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var flaglogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        loginViewModel.isLoading.observe(this, {
            activityLoginBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        activityLoginBinding.btnLogin.setOnClickListener {
            loginViewModel.listReview.observe(this, { consumerReviews ->
                consumerReviews.map {
                    if (it.name == activityLoginBinding.edName.text.toString() && it.review == activityLoginBinding.edPassword.text.toString()) {
                        flaglogin = true

                    }
                }
            })

            if (flaglogin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                activityLoginBinding.tvWrong.visibility = View.VISIBLE
            }
        }

        activityLoginBinding.btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}