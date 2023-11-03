package org.wit.ayoeats.views.meallocationmaps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.ayoeats.main.MainApp

class MealLocationMapsPresenter(val view: MealLocationMapsView) {
    var app: MainApp

    init {
        app = view.application as MainApp
    }

    // configuration of map's display and last location
    fun doPopulateMap(map: GoogleMap){
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(view) // sets a markerclicklistener to the map - not sure why it was moved out of the loop
        app.mealLocations.findAll().forEach{
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.mealName).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }

    }

    fun doMarkerSelected(marker: Marker){
        val tag = marker.tag as Long
        val mealLocation = app.mealLocations.findById(tag)
        if (mealLocation != null){
            view.showMealLocation(mealLocation)
        }
    }
}