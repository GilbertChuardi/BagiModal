package com.example.bagimodal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        activityMainBinding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewPager)

        supportActionBar?.elevation = 0f

    }
}