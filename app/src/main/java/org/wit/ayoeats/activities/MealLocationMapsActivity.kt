package org.wit.ayoeats.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.ayoeats.databinding.ActivityMealLocationMapsBinding

class MealLocationMapsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMealLocationMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealLocationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }

}