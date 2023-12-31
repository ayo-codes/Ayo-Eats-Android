package org.wit.ayoeats.main

import android.app.Application
import org.wit.ayoeats.models.MealLocationJSONStore
import org.wit.ayoeats.models.MealLocationStore
import org.wit.ayoeats.models.UserJSONStore
import org.wit.ayoeats.models.UserStore
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {


    // val mealLocations = ArrayList<MealLocationModel>() // Creates an array list of the individual eat locations(directly accessing the list)

  //  val mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations


    // refactor how the mealLocations stores objects
    lateinit var mealLocations : MealLocationStore
    lateinit var users : UserStore






    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("MainApp Activity started")


        //        mealLocations = MealLocationMemStore() // instantiates the MealLocationMemStore Class and saves it in the variable mealLocations
        mealLocations = MealLocationJSONStore(applicationContext) // instantiates the JSONStore passing the application context as the context
//        mealLocations.create(MealLocationModel(0L, "TestJohn", "TestJohn" , 24.99 , 4.0 ,1L ))
//        mealLocations.create(MealLocationModel(1L, "TestMary", "TestMary" , 24.99 , 4.0 ,2L ))
//        users = UserMemStore()
        users = UserJSONStore(applicationContext)
//        users.create(User("john", "doe","test@test.com" , "test" ))
//        users.create(User("mary","jane","test2@test.com" , "test" ))

    }




//    fun updateCurrentUser(user:User): User{
//        currentUser = user
//        i("$currentUser from main updateCurrentUserFunction")
//        return currentUser as User
//    }




}