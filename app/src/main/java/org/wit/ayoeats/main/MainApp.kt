package org.wit.ayoeats.main

import android.app.Application
import org.wit.ayoeats.models.MealLocationMemStore
import org.wit.ayoeats.models.MealLocationModel
import org.wit.ayoeats.models.MealLocationStore
import org.wit.ayoeats.models.User
import org.wit.ayoeats.models.UserMemStore
import org.wit.ayoeats.models.UserStore
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {

    // val mealLocations = ArrayList<MealLocationModel>() // Creates an array list of the individual eat locations(directly accessing the list)

  //  val mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations

    // refactor how the mealLocations stores objects
    lateinit var mealLocations : MealLocationStore

    lateinit var users : UserStore
     var userLoggedIn : Boolean = false

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("MainApp Activity started")
        mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations
        //mealLocations = MealLocationJSONStore(applicationContext) // instantiates the JSONStore passing the application context as the context
        mealLocations.create(MealLocationModel(0L, "Test", "Test" , 24.99 , 4.0 ))

        users = UserMemStore()

        users.create(User("john", "doe","trial@trial.com" , "testing" ))
        users.create(User("mary","jane","trial2@trial.com" , "testedtrial" ))

    }


}