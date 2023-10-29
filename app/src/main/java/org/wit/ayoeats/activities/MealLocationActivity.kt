package org.wit.ayoeats.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.ayoeats.databinding.ActivityMealLocationBinding
import org.wit.ayoeats.models.MealLocationModel
import timber.log.Timber
import timber.log.Timber.i


class MealLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealLocationBinding // sets the variable binding to a type of ActivityEatLocationBinding

    var mealLocation = MealLocationModel() // instantiate the EatLocationModel Class here
    val mealLocations =
        ArrayList<MealLocationModel>() // Creates an array list of the individual eat locations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMealLocationBinding.inflate(layoutInflater) // set the variable binding to what is returned from the layout inflater, which is the whole layout
        setContentView(binding.root) // sets the contentView to the root property of what is returned from the layoutInflater, which is usually the whole view

        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("Eat Location Activity started")

        binding.btnAdd.setOnClickListener {
            // No need for var or val keyword since eatLocation was set to var above, note this for properties of classes
            mealLocation.mealName =
                binding.mealName.text.toString() // gets the text inside mealName and converts it to a string
            if (mealLocation.mealName.isNotEmpty()) {
                mealLocations.add(mealLocation.copy())
                i("add Button pressed : ${mealLocation.mealName}")
                for (i in mealLocations.indices){
                   i ("MealLocation[$i]: ${this.mealLocations[i]}")
                }
            } else {
                Snackbar.make(it, "Please Enter a Meal Name", Snackbar.LENGTH_LONG)
                    .show() // This shows the warning if the field is empty
            }

            i("this is the mealLocation.title ${mealLocation.mealName}")
            i("this is the mealLocation on its own  ${mealLocation.toString()}")
        }
    }
}