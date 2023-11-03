package org.wit.ayoeats.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel

class MealLocationListPresenter (val view: MealLocationListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerRefreshCallback()
        registerMapCallback()
    }

    // retrieve all meal locations
    fun getMealLocations() = app.mealLocations.findAll()

    // function for when the add menu button pressed
    fun doAddMealLocation() {
        val launcherIntent = Intent(view, MealLocationView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    // function for when the show map menu button pressed
    fun doShowMealLocationsMap() {
        val launcherIntent = Intent(view , MealLocationMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    // button for when a mealLocation is clicked to edit it
    fun doEditMealLocation(mealLocation: MealLocationModel, pos: Int){
        val launcherIntent = Intent(view, MealLocationView::class.java)
        launcherIntent.putExtra("mealLocation_edit" , mealLocation) // using parcelable to create a key("mealLocation_edit") and passes a value of (mealLocation object)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)

    }

    // instantiates the registerForActivityResult, and the .launch method is used in the onMealLocationClickClass ..used for the callback from the edit/add activity
    // this were two different functions in the previous iteration
    private fun registerRefreshCallback() {
        refreshIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
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
}