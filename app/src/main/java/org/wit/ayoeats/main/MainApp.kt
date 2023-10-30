package org.wit.ayoeats.main

import android.app.Application
import org.wit.ayoeats.models.MealLocationModel
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {

    val mealLocations =
        ArrayList<MealLocationModel>() // Creates an array list of the individual eat locations

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("MainApp Activity started")
    }


}