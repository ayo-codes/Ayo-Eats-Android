package org.wit.ayoeats.views.editlocation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import org.wit.ayoeats.R
import org.wit.ayoeats.models.Location

class EditLocationView : AppCompatActivity(), OnMapReadyCallback , GoogleMap.OnMarkerDragListener , GoogleMap.OnMarkerClickListener {
// GoogleMap.OnMarkerDragListener used to track the movement of the marker
// GoogleMap.OnMarkerClickListener is used to track the clicks on the marker
    private lateinit var map: GoogleMap
    lateinit var presenter: EditLocationPresenter
//    private lateinit var binding: ActivityMapBinding
    var location = Location()

//    lateinit var places: Places

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_map)
        presenter = EditLocationPresenter(this) // instantiates the presenter passing this view as the view

        // Set the location object here to the object properties received from the MealLocationActivity
        location = intent.extras?.getParcelable<Location>("location")!!

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        Places.initialize(applicationContext, "API_KEY_GOES_HERE" )
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
        presenter.initMap(map)
    }

    // 3 functions are from the onMapDragListener
    override fun onMarkerDrag(p0: Marker) {

    }

    // This function used to store the details of where the marker last was
    override fun onMarkerDragEnd(p0: Marker) {
        presenter.doUpdateLocation(p0.position.latitude, p0.position.longitude, map.cameraPosition.zoom)
    }

    override fun onMarkerDragStart(p0: Marker) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.doOnBackPressed()
    }


// function override to listen for click changes on the marker element
    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }


}