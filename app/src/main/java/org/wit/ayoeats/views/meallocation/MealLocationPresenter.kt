package org.wit.ayoeats.views.meallocation

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.ayoeats.databinding.ActivityMealLocationBinding
import org.wit.ayoeats.helpers.showImagePicker
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.Location
import org.wit.ayoeats.models.MealLocationModel
import org.wit.ayoeats.models.User
import org.wit.ayoeats.views.editlocation.EditLocationView
import timber.log.Timber
import timber.log.Timber.i

class MealLocationPresenter(private val view: MealLocationView) {
    var mealLocation = MealLocationModel() // instantiate the EatLocationModel Class here
    var app: MainApp = view.application as MainApp // // MainApp class set to view
    var currentUser = User() // instantiates a current user

    var binding: ActivityMealLocationBinding =
        ActivityMealLocationBinding.inflate(view.layoutInflater) // sets the variable binding to a type of ActivityEatLocationBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent> // image intent launcher
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent> // Map intent launcher


    // var location = Location(6.4281 ,3.4219, 15f )
    var edit = false // this acts as a flag to let us know if we are or aren't in edit mode

    init {
        if (view.intent.hasExtra("mealLocation_edit")) {
            edit =
                true // set the edit variable to true , to ensure we have a way of flagging we are in edit mode
            mealLocation = view.intent.extras?.getParcelable("mealLocation_edit")!!
            view.showMealLocation(mealLocation)
        }
        if(view.intent.hasExtra("current_user")){
            currentUser = view.intent.extras?.getParcelable("current_user")!!
            i("Received Current User : ${currentUser} from the Meal Location List")

        }
        registerImagePickerCallback() // call the image callback
        registerMapCallback() //call the map callback

    }

    fun doAddOrSave(
        mealName: String,
        mealDescription: String,
        mealPrice: Double,
        mealRating: Double,
        userId: Long
    ) {
        mealLocation.mealName = mealName
        mealLocation.mealDescription = mealDescription
        mealLocation.mealPrice = mealPrice
        mealLocation.mealRating = mealRating
        mealLocation.userId = userId
        if (edit) {
            app.mealLocations.update(mealLocation)
        } else {
            app.mealLocations.create(mealLocation)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.mealLocations.delete(mealLocation)
        view.finish()
    }



    fun doSelectImage() {
        showImagePicker(imageIntentLauncher, view)
    }

    fun doSetLocation() {
        val location = Location(6.4281, 3.4219, 15f)
        if (mealLocation.zoom != 0f) {
            location.lat = mealLocation.lat
            location.lng = mealLocation.lng
            location.zoom = mealLocation.zoom
        }
        val launcherIntent = Intent(
            view,
            EditLocationView::class.java
        ) // sets the intent, with toActivity set to the EditLocationView
            .putExtra("location", location) // this passes the location object as data
        mapIntentLauncher.launch(launcherIntent)   // calls the launch function on the mapIntentLauncher to actually open the activity
        Timber.i("Pick Location on Map Clicked")
    }

    fun cacheMealLocation(
        mealName: String,
        mealDescription: String,
        mealPrice: Double,
        mealRating: Double,
        userId: Long
    ) {
        mealLocation.mealName = mealName
        mealLocation.mealDescription = mealDescription
        mealLocation.mealPrice = mealPrice
        mealLocation.mealRating = mealRating
        mealLocation.userId = currentUser.id
    }

    //Image CallBack function
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            // added in to allow for permissions to access files
                            mealLocation.image =
                                result.data!!.data!! // sets the image uri to the uri from the data.data object
                            view.contentResolver.takePersistableUriPermission(
                                mealLocation.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            view.updateImage(mealLocation.image)

//                            Picasso.get() // Picasso Library
//                                .load(mealLocation.image) // Loads the uri we got back from results.data.data
//                                .into(binding.mealLocationImage) // binds it to the UI
//                            binding.chooseImage.setText(R.string.change_mealLocation_image) // Change the button label once an image has been selected
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }

            }
    }

    // Map CallBack Function

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location = $location")
                            mealLocation.lat = location.lat
                            mealLocation.lng = location.lng
                            mealLocation.zoom = location.zoom
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }

                Timber.i("Map Loaded")
            }
    }


}
