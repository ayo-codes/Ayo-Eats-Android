package org.wit.ayoeats.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.ayoeats.R
import org.wit.ayoeats.databinding.ActivityMapBinding
import org.wit.ayoeats.models.Location

class MapActivity : AppCompatActivity(), OnMapReadyCallback , GoogleMap.OnMarkerDragListener {
// GoogleMap.OnMarkerDragListener used to track the movement of the marker
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the location object here to the object properties received from the MealLocationActivity
        location = intent.extras?.getParcelable<Location>("location")!!

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap



        // Add a marker in Lagos and move the camera
        val loc = LatLng(location.lat , location.lng)
        val options = MarkerOptions() // instantiate a class of MarkerOptions
            .title("Meal Location")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        // sets a drag listener to this map , you can pass this, since the class is implementing it
        map.setOnMarkerDragListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    // 3 functions are from the onMapDragListener
    override fun onMarkerDrag(p0: Marker) {

    }

    // This function is used to store the details of where the marker last was
    override fun onMarkerDragEnd(p0: Marker) {
        location.lat = p0.position.latitude
        location.lng = p0.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragStart(p0: Marker) {

    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location" , location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}