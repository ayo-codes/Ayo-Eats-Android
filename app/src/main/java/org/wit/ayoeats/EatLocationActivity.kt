package org.wit.ayoeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.i


class EatLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eatlocation)

        Timber.plant(Timber.DebugTree()) // initialise the logging library
        i("Eat Location Activity started")
    }
}