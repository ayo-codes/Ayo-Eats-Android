package org.wit.ayoeats.views.meallocationmaps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.ayoeats.databinding.ActivityMealLocationMapsBinding
import org.wit.ayoeats.databinding.ContentMealLocationMapsBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel

class MealLocationMapsView : AppCompatActivity() , GoogleMap.OnMarkerClickListener  { //implement onMarkerClickListener on the activity
    private lateinit var binding: ActivityMealLocationMapsBinding // binds to the outer layout
    private lateinit var contentBinding: ContentMealLocationMapsBinding // binds to the content meal location layout
//    lateinit var map: GoogleMap
    lateinit var app: MainApp
    lateinit var presenter: MealLocationMapsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityMealLocationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = MealLocationMapsPresenter(this) // instantiate the presenter object

        contentBinding = ContentMealLocationMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState) // creates the map in the mapView

        contentBinding.mapView.getMapAsync {
            presenter.doPopulateMap(it)
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



    // marker listener for details on the map
    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    fun showMealLocation (mealLocation : MealLocationModel) {
        contentBinding.currentMealName.text = mealLocation.mealName
        contentBinding.currentMealDescription.text = mealLocation.mealDescription
        Picasso.get().load(mealLocation.image).into(contentBinding.currentImage)
    }
}


