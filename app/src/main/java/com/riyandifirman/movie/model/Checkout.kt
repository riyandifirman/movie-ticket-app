package com.riyandifirman.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Checkout (
    var seat:String ? = "",
    var price:String ? = ""
) : Parcelable