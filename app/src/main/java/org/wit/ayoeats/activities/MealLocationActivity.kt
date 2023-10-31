package org.wit.ayoeats.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.ayoeats.R
import org.wit.ayoeats.databinding.ActivityMealLocationBinding
import org.wit.ayoeats.helpers.showImagePicker
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel
import timber.log.Timber.i


class MealLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealLocationBinding // sets the variable binding to a type of ActivityEatLocationBinding
    var mealLocation = MealLocationModel() // instantiate the EatLocationModel Class here
    lateinit var app: MainApp // instantiate later MainApp class
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("Meal Location Activity started")
        var edit = false // this acts as a flag to let us know if we are or aren't in edit mode

        binding =
            ActivityMealLocationBinding.inflate(layoutInflater) // set the variable binding to what is returned from the layout inflater, which is the whole layout
        setContentView(binding.root) // sets the contentView to the root property of what is returned from the layoutInflater, which is usually the whole view

        //menu toolbar binding
        binding.toolbarAdd.title = title // sets title to title of the app
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp // This is where we initialise the lateinit from above

        if (intent.hasExtra("mealLocation_edit")) {
            edit =
                true // set the edit variable to true , to ensure we have a way of flagging we are in edit mode
            mealLocation = intent.extras?.getParcelable("mealLocation_edit")!!
            binding.mealName.setText(mealLocation.mealName)
            binding.mealDescription.setText(mealLocation.mealDescription)
            binding.mealPrice.setText(mealLocation.mealPrice.toString())
            binding.seekBarRatings.progress = mealLocation.mealRating.toInt()
            binding.RatingsProgress.text = mealLocation.mealRating.toString()
            binding.btnAdd.setText(R.string.save_meal_location) // change the button text
            Picasso.get()
                .load(mealLocation.image)
                .into(binding.mealLocationImage)

        }


        // learnt from Kotlin Course on Udemy
        binding.seekBarRatings.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.RatingsProgress.text = binding.seekBarRatings.progress.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.RatingsProgress.text = binding.seekBarRatings.progress.toString()

            }

        })


        // Event Handler for the Add Meal Location Button
        binding.btnAdd.setOnClickListener {
            // No need for var or val keyword since eatLocation was set to var above, note this for properties of classes
            mealLocation.mealName =
                binding.mealName.text.toString() // gets the text inside mealName and converts it to a string
            mealLocation.mealDescription = binding.mealDescription.text.toString()
            mealLocation.mealPrice = binding.mealPrice.text.toString().toDouble()
            mealLocation.mealRating = binding.RatingsProgress.text.toString().toDouble()

            //input validation and checking if in edit mode
            if (mealLocation.mealName.isEmpty() && mealLocation.mealDescription.isEmpty()) {
                Snackbar.make(it, R.string.enter_mealLocation_mealName, Snackbar.LENGTH_LONG)
                    .show() // This shows the warning if the field is empty
            } else {
                if (edit) { // Checks here if we are in edit mode
                    app.mealLocations.update(mealLocation.copy())
                } else {
                    app.mealLocations.create(mealLocation.copy())
                    i("add Button pressed : ${mealLocation.mealName}")
                    for (i in app.mealLocations.findAll().indices) {
                        i("MealLocation[$i]: ${this.app.mealLocations.findAll()[i]}")
                    }
                }

            }
            setResult(RESULT_OK)
            finish()


            i("this is the mealLocation.title ${mealLocation.mealName}")
            i("this is the mealLocation on its own  ${mealLocation.toString()}")
        }


        // Event Handler for the Add Image Button
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
//            i("Select Image Clicked")
        }

        registerImagePickerCallback()

        //Image CallBack


    }

    // Creates the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meal_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

     private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if(result.data != null){
                            i("Got Result ${result.data!!.data}")
                            mealLocation.image = result.data!!.data!! // sets the image uri to the uri from the data.data object
                            Picasso.get() // Picasso Library
                                .load(mealLocation.image) // Loads the uri we got back from results.data.data
                                .into(binding.mealLocationImage) // binds it to the UI
                        }
                    }
                    RESULT_CANCELED -> { }
                    else -> { }
                }

            }
    }

}