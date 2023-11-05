package org.wit.ayoeats.views.editlocation

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.ayoeats.models.Location
import timber.log.Timber.i
import java.io.IOException
import java.util.Locale

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

//        val mGeocoder = Geocoder(view.applicationContext , Locale.getDefault() )
//        var addressString = ""
//
//        try{
//            val addressList : MutableList<Address>? = mGeocoder.getFromLocation(location.lat , location.lng, 1)
//
//            // use your lat, long value here
//            if (addressList != null && addressList.isNotEmpty()) {
//                val address = addressList[0]
//                val sb = StringBuilder()
//                for (i in 0 until address.maxAddressLineIndex) {
//                    sb.append(address.getAddressLine(i)).append("\n")
//                }
//
//                // Various Parameters of an Address are appended
//                // to generate a complete Address
//                if (address.premises != null)
//                    sb.append(address.premises).append(", ")
//
//                sb.append(address.subAdminArea).append("\n")
//                sb.append(address.locality).append(", ")
//                sb.append(address.adminArea).append(", ")
//                sb.append(address.countryName).append(", ")
//                sb.append(address.postalCode)
//
//                // StringBuilder sb is converted into a string
//                // and this value is assigned to the
//                // initially declared addressString string.
//                addressString = sb.toString()
//            }
//            }catch (e: IOException) {
//            i("An Error Occurred")
//            }
//
//        // Finally, the address string is posted in the textView with LatLng.
////        addressTV.text = "Lat: $lat \nLng: $lng \nAddress: $addressString"
//
//        i("${addressString} = address string ")

    }

    // tutorial used for Reverse GeoCoding = "https://www.geeksforgeeks.org/reverse-geocoding-in-android/ "
    fun doUpdateLocation(lat: Double, lng:Double, zoom: Float){
        location.lat = lat
        location.lng = lng
        location.zoom = zoom


        val mGeocoder = Geocoder(view.applicationContext , Locale.getDefault() )


        var addressString = ""

        try{
            val addressList : MutableList<Address>? = mGeocoder.getFromLocation(lat , lng, 1)

            // use your lat, long value here
            if (addressList != null && addressList.isNotEmpty()) {
                val address = addressList[0]
                val sb = StringBuilder()
                for (i in 0 until address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append("\n")
                }

                // Various Parameters of an Address are appended
                // to generate a complete Address
                if (address.premises != null)
                    sb.append(address.premises).append(", ")
                sb.append(address.featureName).append(", ")
                sb.append(address.thoroughfare).append(",")
                sb.append(address.subAdminArea).append("\n")
                sb.append(address.locality).append(", ")
                sb.append(address.adminArea).append(", ")
                sb.append(address.countryName).append(", ")
                sb.append(address.postalCode)

                // StringBuilder sb is converted into a string
                // and this value is assigned to the
                // initially declared addressString string.
                addressString = sb.toString()
                location.address = addressString
            }
        }catch (e: IOException) {
            i("An Error Occurred")
        }

        // Finally, the address string is posted in the textView with LatLng.
//        addressTV.text = "Lat: $lat \nLng: $lng \nAddress: $addressString"

        i("${addressString} = address string ")
        i("Location ${location}")
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