package com.example.bagimodal.ui.main.profile.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bagimodal.R

class CreateProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)
        title = "Create New Project"
    }
}