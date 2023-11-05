package org.wit.ayoeats.views.meallocationlist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel
import org.wit.ayoeats.models.User
import org.wit.ayoeats.ui.login.LoginActivity
import org.wit.ayoeats.views.meallocation.MealLocationView
import org.wit.ayoeats.views.meallocationmaps.MealLocationMapsView
import timber.log.Timber.i

class MealLocationListPresenter (val view: MealLocationListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var logoutIntentLauncher : ActivityResultLauncher<Intent> // Logout intent launcher
    private var position: Int = 0

    var userLoggedIn = false
    var currentUser = User()

    init {
        app = view.application as MainApp
        registerRefreshCallback()
        registerMapCallback()
        registerLogoutCallback()

        if(view.intent.hasExtra("current_user1")){
            userLoggedIn = true
            currentUser = view.intent.extras?.getParcelable("current_user1")!!
        }
    }

    // retrieve all meal locations
    fun getMealLocations(): List<MealLocationModel> {

        var mealLocations = app.mealLocations.findAll().filter { it.userId == currentUser.id }
        i("calling the mealLocations size to see what it is ${mealLocations.size} and what is in it ${mealLocations}")
        return mealLocations
    }

    // function for when the add menu button pressed
    fun doAddMealLocation() {
        val launcherIntent = Intent(view, MealLocationView::class.java)
        launcherIntent.putExtra("current_user" , currentUser)
        refreshIntentLauncher.launch(launcherIntent)

    }

    // function for when the show map menu button pressed
    fun doShowMealLocationsMap() {
        val launcherIntent = Intent(view , MealLocationMapsView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    // button for when a mealLocation is clicked to edit it
    fun doEditMealLocation(mealLocation: MealLocationModel, pos: Int){
        val launcherIntent = Intent(view, MealLocationView::class.java)
        launcherIntent.putExtra("mealLocation_edit" , mealLocation) // using parcelable to create a key("mealLocation_edit") and passes a value of (mealLocation object)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)

    }

    fun doLogout(){
        val launcherIntent = Intent(view, LoginActivity::class.java)
        logoutIntentLauncher.launch(launcherIntent)
        view.finish()
    }

    // instantiates the registerForActivityResult, and the .launch method is used in the onMealLocationClickClass ..used for the callback from the edit/add activity
    // this were two different functions in the previous iteration
    private fun registerRefreshCallback() {
        refreshIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                i("result code${it.resultCode}")
                i("the data back ${it.data}")
                i("the data back ${it.data?.data}")
                i("Register for Refresh Testing to see who is $currentUser")
                i(" register for Refresh = ${getMealLocations()}")
                view.onRefresh()
            } else {
                if (it.resultCode == 99) {
                    view.onDelete(position)
                }
            }
        }
    }

    // instantiates the registerForActivityResult and used for the .launch method for maps
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }
    }

    private fun registerLogoutCallback(){
        logoutIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }
    }
}