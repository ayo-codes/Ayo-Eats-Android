package org.wit.ayoeats.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.ayoeats.R
import org.wit.ayoeats.main.MainApp

class MealLocationListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_location_list)
        app = application as MainApp // late initialising of MainApp class
    }
}