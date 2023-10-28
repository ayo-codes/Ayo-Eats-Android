package org.wit.ayoeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.ayoeats.databinding.ActivityEatlocationBinding
import timber.log.Timber
import timber.log.Timber.i


class EatLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEatlocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityEatlocationBinding.inflate(layoutInflater) // set the variable binding to what is returned from the layout inflater, which is the whole layout
        setContentView(binding.root) // sets the contentView to the root property of what is returned from the layoutInflater, which is usually the whole view
        binding.btnAdd.setOnClickListener {
            val mealName =
                binding.mealName.text.toString() // gets the text inside mealName and converts it to a string
            if (mealName.isNotEmpty()) {
                i("add Button pressed")
            } else {
                Snackbar.make(it, "Please Enter a Meal Name", Snackbar.LENGTH_LONG)
                    .show() // This shows the warning if the field is empty
            }
            Timber.plant(Timber.DebugTree()) // initialise the logging library
            i("Eat Location Activity started")

        }
    }
}