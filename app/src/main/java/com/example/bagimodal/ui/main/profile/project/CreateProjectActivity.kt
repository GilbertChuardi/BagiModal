package com.example.bagimodal.ui.main.profile.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityCreateProjectBinding
import com.example.bagimodal.ui.SharePreference
import com.google.firebase.firestore.FirebaseFirestore


class CreateProjectActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sharePreference: SharePreference
    private lateinit var activityCreateProjectBinding: ActivityCreateProjectBinding
    private lateinit var db: FirebaseFirestore
    private var email = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = arrayOf("Arts","Music","Game","Tech","Film")

        activityCreateProjectBinding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(activityCreateProjectBinding.root)
        title = "Create New Project"

        sharePreference = SharePreference(this)
        db = FirebaseFirestore.getInstance()
        activityCreateProjectBinding.btnCreateProject.setOnClickListener(this)
        activityCreateProjectBinding.spinnerCategory.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        activityCreateProjectBinding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activityCreateProjectBinding.spinnerValue.text = options[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                activityCreateProjectBinding.spinnerValue.text = ""
            }
        }
        email = sharePreference.getValueString("email").toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_create_project -> createProject()
        }
    }

    private fun createProject() {
        val judul = activityCreateProjectBinding.edJudul.text.toString().trim()
        val description = activityCreateProjectBinding.edDescription.text.toString().trim()
        val category = activityCreateProjectBinding.spinnerValue.text.toString().trim()
        val targetdonation = activityCreateProjectBinding.edTargetDonation.text.toString().trim()

        if (judul.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty() && targetdonation.isNotEmpty()) {
            val data = hashMapOf(
                "email" to email,
                "judul" to judul,
                "description" to description,
                "category" to category,
                "totaldonation" to 0,
                "targetdonation" to targetdonation.toInt()
            )

            db.collection("projectUser")
                .document(judul)
                .set(data)
                .addOnSuccessListener {
                    Log.d("create project", "DocumentSnapshot successfully written!")
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
                    Log.w("Create Project", "Error adding document", e)
                }
        }
    }
}