package com.example.bagimodal.ui.credential

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Register"
        db = FirebaseFirestore.getInstance()

        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        auth = FirebaseAuth.getInstance()

        activityRegisterBinding.btnRegister.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> doRegister()
        }
    }

    private fun doRegister() {
        activityRegisterBinding.tvEmailExists.visibility = View.INVISIBLE

        if (activityRegisterBinding.edEmail.text.toString().isEmpty()) {
            activityRegisterBinding.edEmail.error = "Please enter email"
            activityRegisterBinding.edEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(activityRegisterBinding.edEmail.text.toString())
                .matches()
        ) {
            activityRegisterBinding.edEmail.error = "Please enter valid email"
            activityRegisterBinding.edEmail.requestFocus()
            return
        }

        if (activityRegisterBinding.edPassword.text.toString().isEmpty()) {
            activityRegisterBinding.edPassword.error = "Please enter password"
            activityRegisterBinding.edPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(
            activityRegisterBinding.edEmail.text.toString(),
            activityRegisterBinding.edPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { tasks ->
                            if (tasks.isSuccessful) {
                                val data = hashMapOf(
                                    "money" to 100000
                                )

                                db.collection("moneyUser")
                                    .document(activityRegisterBinding.edEmail.text.toString())
                                    .set(data)
                                    .addOnSuccessListener {
                                        Log.d(
                                            "create money data",
                                            "DocumentSnapshot successfully written!"
                                        )
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            this,
                                            "Error adding money data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.w("Create Project", "Error adding document", e)
                                    }

                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    activityRegisterBinding.tvEmailExists.visibility = View.VISIBLE
                }
            }
    }
}