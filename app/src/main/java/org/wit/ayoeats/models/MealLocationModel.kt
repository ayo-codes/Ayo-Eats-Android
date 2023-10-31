package org.wit.ayoeats.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// annotate the class and also set the class to the extend from Parcelable
@Parcelize
data class MealLocationModel(var mealName: String = "" , var mealDescription: String = "" , var mealPrice: Double = 0.00 , var mealRating: Double = 0.00) : Parcelable // title property and a value is given here
