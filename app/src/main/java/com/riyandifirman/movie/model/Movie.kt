package com.riyandifirman.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    var desc:String ? = "",
    var director:String ? = "",
    var genre:String ? = "",
    var title:String ? = "",
    var poster:String ? = "",
    var rating:String ? = ""
) : Parcelable