package com.example.bagimodal.ui.credential

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityLoginBinding
import com.example.bagimodal.ui.SharePreference
import com.example.bagimodal.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharePreference: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Login"

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        auth = FirebaseAuth.getInstance()
        sharePreference = SharePreference(this)

        activityLoginBinding.btnLogin.setOnClickListener(this)
        activityLoginBinding.btnToRegister.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> doLogin()

            R.id.btn_to_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun doLogin() {
        activityLoginBinding.tvWrong.visibility = View.INVISIBLE
        if (activityLoginBinding.edEmail.text.toString().isEmpty()) {
            activityLoginBinding.edEmail.error = "Please enter email"
            activityLoginBinding.edEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(activityLoginBinding.edEmail.text.toString())
                .matches()
        ) {
            activityLoginBinding.edEmail.error = "Please enter valid email"
            activityLoginBinding.edEmail.requestFocus()
            return
        }

        if (activityLoginBinding.edPassword.text.toString().isEmpty()) {
            activityLoginBinding.edPassword.error = "Please enter password"
            activityLoginBinding.edPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(
            activityLoginBinding.edEmail.text.toString(),
            activityLoginBinding.edPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    sharePreference.save("email", activityLoginBinding.edEmail.text.toString())
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    activityLoginBinding.tvWrong.visibility = View.VISIBLE
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        activityLoginBinding.progressBar.visibility = View.VISIBLE
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        activityLoginBinding.progressBar.visibility = View.INVISIBLE
    }
}