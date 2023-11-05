package org.wit.ayoeats.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// annotate the class and also set the class to the extend from Parcelable
@Parcelize
data class MealLocationModel(
    var id: Long = 0,
    var mealName: String = "",
    var mealDescription: String = "",
    var mealPrice: Double = 0.00,
    var mealRating: Double = 0.00,
    var userId: Long = 0,
    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable // title property and a value is given here

@Parcelize
data class Location(var lat: Double = 0.0, var lng: Double = 0.0, var zoom: Float = 0f) : Parcelable

@Parcelize
data class User( var firstname: String = "", var surname:String="" , var email: String ="", var password: String ="", var id: Long =0L):Parcelable