package org.wit.ayoeats.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.ayoeats.databinding.ActivityEatlocationBinding
import org.wit.ayoeats.models.EatLocationModel
import timber.log.Timber
import timber.log.Timber.i


class EatLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEatlocationBinding // sets the variable binding to a type of ActivityEatLocationBinding

    var eatLocation = EatLocationModel() // instantiate the EatLocationModel Class here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityEatlocationBinding.inflate(layoutInflater) // set the variable binding to what is returned from the layout inflater, which is the whole layout
        setContentView(binding.root) // sets the contentView to the root property of what is returned from the layoutInflater, which is usually the whole view

        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("Eat Location Activity started")

        binding.btnAdd.setOnClickListener {
            // No need for var or val keyword since eatLocation was set to var above, note this for properties of classes
            eatLocation.mealName =
                binding.mealName.text.toString() // gets the text inside mealName and converts it to a string
            if (eatLocation.mealName.isNotEmpty()) {
                i("add Button pressed : ${eatLocation.mealName}")
            } else {
                Snackbar.make(it, "Please Enter a Meal Name", Snackbar.LENGTH_LONG)
                    .show() // This shows the warning if the field is empty
            }

            i("this is the eatlocation.title ${eatLocation.mealName}")
            i("this is the eatlocation on its own  ${eatLocation.toString()}")
        }
    }
}