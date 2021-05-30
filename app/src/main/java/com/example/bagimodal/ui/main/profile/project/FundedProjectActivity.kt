package com.example.bagimodal.ui.main.profile.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bagimodal.R

class FundedProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funded_project)

        title = "Funded Projects"
    }
}