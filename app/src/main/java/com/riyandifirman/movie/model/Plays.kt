package com.riyandifirman.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plays (
    var name:String ? = "",
    var url:String ? = ""
) : Parcelable