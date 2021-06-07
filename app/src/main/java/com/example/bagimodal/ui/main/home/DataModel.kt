package com.example.bagimodal.ui.main.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataModel(
    val email: String = "",
    val judul: String = "",
    val description: String = "",
    val category:String = "",
    var totaldonation: Int = 0,
    var targetdonation:Int = 0
) : Parcelable