package com.example.bagimodal.ui.main.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class DataModel(
    val email: String = "",
    val judul: String = "",
    val description: String = ""
) : Parcelable