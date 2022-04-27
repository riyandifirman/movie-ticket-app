package com.riyandifirman.movie.sign.signin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var balance: String ?= "",
    var email: String ?= "",
    var name: String ?= "",
    var password: String ?= "",
    var url: String ?= "",
    var username: String ?= ""
) : Parcelable