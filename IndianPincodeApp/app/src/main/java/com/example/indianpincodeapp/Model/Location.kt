package com.example.indianpincodeapp.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val city: String? = null,
    val district: String? = null,
    var pincode: String?= null,
    val postOfficeName: String?=null,
    var state: String?=null,
    var isLike: Boolean = false
):Parcelable{


    override fun toString(): String {
     return pincode.toString()

        return city.toString()
    }



}
