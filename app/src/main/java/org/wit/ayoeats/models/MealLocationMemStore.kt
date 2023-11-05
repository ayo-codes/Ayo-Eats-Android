package org.wit.ayoeats.models

import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber.i


var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

// this class here implements the MealLocationStore interface class, so every function in that class needs to be represented and worked on here
// to determine how they will work
class MealLocationMemStore() : AppCompatActivity(), MealLocationStore {



    val mealLocations = ArrayList<MealLocationModel>() // mealLocations is an arraylist of the data class


    override fun findAll(): List<MealLocationModel> {


       return mealLocations
    }

    override fun create(mealLocation: MealLocationModel) {
        mealLocation.id = getId()
        mealLocations.add(mealLocation)
        logAll()

    }

    override fun update(mealLocation: MealLocationModel) {
        var foundMealLocation: MealLocationModel? = mealLocations.find {m -> m.id == mealLocation.id}
        if(foundMealLocation != null ){
            foundMealLocation.mealName = mealLocation.mealName
            foundMealLocation.mealDescription = mealLocation.mealDescription
            foundMealLocation.mealPrice = mealLocation.mealPrice
            foundMealLocation.mealRating = mealLocation.mealRating
            foundMealLocation.image = mealLocation.image // updates the image
            foundMealLocation.lat = mealLocation.lat
            foundMealLocation.lng = mealLocation.lng
            foundMealLocation.zoom = mealLocation.zoom
            logAll()
        }
    }

    override fun delete(mealLocation: MealLocationModel) {
        mealLocations.remove(mealLocation)
    }

    override fun findById(id: Long): MealLocationModel? {
        val foundMealLocation: MealLocationModel? = mealLocations.find { it.id == id }
        return foundMealLocation
    }

    fun logAll() {
        mealLocations.forEach { i ("$it") }
    }

//    fun findCurrentUsersMealLocations(user: User) : List<MealLocationModel> {
//        val currentUserMealLocations = mealLocations.filter { it.userId == user.id
//        }
//        i("${user} current user")
//        i("$currentUserMealLocations arraylist")
//        return currentUserMealLocations
//    }
}