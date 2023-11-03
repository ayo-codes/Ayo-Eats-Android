package org.wit.ayoeats.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.ayoeats.databinding.ActivityMealLocationMapsBinding
import org.wit.ayoeats.databinding.ContentMealLocationMapsBinding
import org.wit.ayoeats.main.MainApp

class MealLocationMapsActivity : AppCompatActivity() , GoogleMap.OnMarkerClickListener  { //implement onMarkerClickListener on the activity
    private lateinit var binding: ActivityMealLocationMapsBinding // binds to the outer layout
    private lateinit var contentBinding: ContentMealLocationMapsBinding // binds to the content meal location layout
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityMealLocationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentMealLocationMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState) // creates the map in the mapView

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    // functions related to displaying google maps
    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    // configuration of map's display and last location
    private fun configureMap(){
        map.uiSettings.isZoomControlsEnabled = true
        app.mealLocations.findAll().forEach{
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.mealName).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this) // sets a markerclicklistener to the map
        }

    }

    // marker listener for details on the map
    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val mealLocation = app.mealLocations.findById(tag)
        contentBinding.currentMealName.text = mealLocation!!.mealName
        contentBinding.currentMealDescription.text = mealLocation.mealDescription
        Picasso.get().load(mealLocation.image).into(contentBinding.currentImage)

        return false
    }
}


