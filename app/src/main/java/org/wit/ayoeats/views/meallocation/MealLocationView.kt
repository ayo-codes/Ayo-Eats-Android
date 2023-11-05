package org.wit.ayoeats.views.meallocation

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.ayoeats.R
import org.wit.ayoeats.databinding.ActivityMealLocationBinding
import org.wit.ayoeats.models.MealLocationModel
import timber.log.Timber.i


class MealLocationView : AppCompatActivity() {

    private lateinit var presenter: MealLocationPresenter
    private lateinit var binding: ActivityMealLocationBinding // sets the variable binding to a type of ActivityEatLocationBinding
    var mealLocation = MealLocationModel() // instantiate the EatLocationModel Class here


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("Meal Location View started")

        binding =
            ActivityMealLocationBinding.inflate(layoutInflater) // set the variable binding to what is returned from the layout inflater, which is the whole layout
        setContentView(binding.root) // sets the contentView to the root property of what is returned from the layoutInflater, which is usually the whole view

        //menu toolbar binding
        binding.toolbarAdd.title = title // sets title to title of the app
        setSupportActionBar(binding.toolbarAdd)

        // presenter initialised
        presenter =
            MealLocationPresenter(this) // initialise the presenter passing this view into it as a parameter




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
//            input validation and checking if in edit mode
            if (binding.mealName.text.toString().isEmpty() && binding.mealDescription.text.toString().isEmpty()) {
                Snackbar.make(it, R.string.enter_mealLocation_mealName, Snackbar.LENGTH_LONG)
                    .show() // This shows the warning if the field is empty
            } else {
                presenter.doAddOrSave(binding.mealName.text.toString(), binding.mealDescription.text.toString(), binding.mealPrice.text.toString().toDouble() , binding.RatingsProgress.text.toString().toDouble() , presenter.currentUser.id )
            }



            i("this is the mealLocation.title ${mealLocation.mealName}")
            i("this is the mealLocation on its own  ${mealLocation.toString()}")
        }


        // Event Handler for the Add Image Button
        binding.chooseImage.setOnClickListener {
            presenter.cacheMealLocation(
                binding.mealName.text.toString(),
                binding.mealDescription.text.toString(),
                binding.mealPrice.text.toString().toDouble(),
                binding.RatingsProgress.text.toString().toDouble(),
                presenter.currentUser.id
            ) // we now pass a context when using the showImagePicker
            i("Select Image Clicked")
            presenter.doSelectImage()
        }


        // Event Handler for the Pick Location Button
        binding.btnMealLocationMap.setOnClickListener {
            presenter.cacheMealLocation(
                binding.mealName.text.toString(),
                binding.mealDescription.text.toString(),
                binding.mealPrice.text.toString().toDouble(),
                binding.RatingsProgress.text.toString().toDouble(),
                presenter.currentUser.id
            )
            i("Select Pick Location Clicked")
            presenter.doSetLocation()
        }



    }

    // Creates the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_meal_location, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit // sets the isVisible to true or false based on the property of the boolean edit in the presenter class
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }

            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun showMealLocation (mealLocation: MealLocationModel){
        binding.mealName.setText(mealLocation.mealName)
        binding.mealDescription.setText(mealLocation.mealDescription)
        binding.mealPrice.setText(mealLocation.mealPrice.toString())
        binding.seekBarRatings.progress = mealLocation.mealRating.toInt()
        binding.RatingsProgress.text = mealLocation.mealRating.toString()
        binding.btnAdd.setText(R.string.save_meal_location) // change the button text
        Picasso.get()
            .load(mealLocation.image)
            .into(binding.mealLocationImage)
        //check if the image uri is not empty then change the button label
        if (mealLocation.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_mealLocation_image)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.mealLocationImage)
        binding.chooseImage.setText(R.string.change_mealLocation_image)
    }






}