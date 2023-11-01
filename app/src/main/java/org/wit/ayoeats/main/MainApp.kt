package org.wit.ayoeats.main

import android.app.Application
import org.wit.ayoeats.models.MealLocationMemStore
import org.wit.ayoeats.models.MealLocationModel
import org.wit.ayoeats.models.MealLocationStore
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {

    // val mealLocations = ArrayList<MealLocationModel>() // Creates an array list of the individual eat locations(directly accessing the list)

  //  val mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations

    // refactor how the mealLocations stores objects
    lateinit var mealLocations : MealLocationStore


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("MainApp Activity started")
        mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations
        mealLocations.create(MealLocationModel(0L, "Test", "Test" , 24.99 , 4.0 ))
    }


}