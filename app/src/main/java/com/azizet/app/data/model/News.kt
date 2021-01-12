package com.azizet.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val link: String = "",
    val judul: String = "",
    val poster: String = "",
    val waktu: String = "",
    val tipe: String = "",
    val kutipan: String = ""

) : Parcelable
