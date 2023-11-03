package org.wit.ayoeats.activities

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.ayoeats.models.Location

class EditLocationPresenter (val view: EditLocationView) {

    var location = Location()

    init {
        location = view.intent.extras?.getParcelable<Location>("location")!!
    }

    fun initMap(map : GoogleMap) {

        // Add a marker in Lagos and move the camera
        val loc = LatLng(location.lat , location.lng)
        val options = MarkerOptions() // instantiate a class of MarkerOptions
            .title("Meal Location")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.addMarker(options)

        map.setOnMarkerDragListener(view) // sets a marker drag listener to the view on map ,
        map.setOnMarkerClickListener(view)// sets a marker click listener to the view on the map,
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    fun doUpdateLocation(lat: Double, lng:Double, zoom: Float){
        location.lat = lat
        location.lng = lng
        location.zoom = zoom
    }

    fun doUpdateMarker(marker: Marker){
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
    }

    fun doOnBackPressed(){
        val resultIntent = Intent()
        resultIntent.putExtra("location" , location)
        view.setResult(Activity.RESULT_OK, resultIntent)
        view.finish()
    }
}