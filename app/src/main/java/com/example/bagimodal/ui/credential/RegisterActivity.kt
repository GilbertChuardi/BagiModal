package com.example.bagimodal.ui.credential

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var activityRegisterBinding:ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        auth = FirebaseAuth.getInstance()

        activityRegisterBinding.btnRegister.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_register -> doRegister()
        }
    }

    private fun doRegister() {
        if (activityRegisterBinding.edEmail.text.toString().isEmpty()) {
            activityRegisterBinding.edEmail.error = "Please enter email"
            activityRegisterBinding.edEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(activityRegisterBinding.edEmail.text.toString()).matches()) {
            activityRegisterBinding.edEmail.error = "Please enter valid email"
            activityRegisterBinding.edEmail.requestFocus()
            return
        }

        if (activityRegisterBinding.edPassword.text.toString().isEmpty()) {
            activityRegisterBinding.edPassword.error = "Please enter password"
            activityRegisterBinding.edPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(activityRegisterBinding.edEmail.text.toString(), activityRegisterBinding.edPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}