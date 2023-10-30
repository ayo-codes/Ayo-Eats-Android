package org.wit.ayoeats.models

import timber.log.Timber.i


// this class here implements the MealLocationStore interface class, so every function in that class needs to be represented and worked on here
// to determine how they will work
class MealLocationMemStore : MealLocationStore {

    val mealLocations = ArrayList<MealLocationModel>() // mealLocations is an arraylist of the data class
    override fun findAll(): List<MealLocationModel> {
        return mealLocations
    }

    override fun create(mealLocation: MealLocationModel) {
        mealLocations.add(mealLocation)
        logAll()

    }

    fun logAll() {
        mealLocations.forEach { i ("$it") }
    }
}