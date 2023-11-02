package org.wit.ayoeats.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.ayoeats.R
import org.wit.ayoeats.adapters.MealLocationAdapter
import org.wit.ayoeats.adapters.MealLocationListener
import org.wit.ayoeats.databinding.ActivityMealLocationListBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel


class MealLocationListActivity : AppCompatActivity() , MealLocationListener {


    lateinit var app: MainApp
    private lateinit var binding : ActivityMealLocationListBinding // binding variable to connect code to the UI

    private var position : Int =0 // for the position of the adapter element
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealLocationListBinding.inflate(layoutInflater) // sets binding variable to the XML that is inflated
        setContentView(binding.root)

        // menu binding
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)


        app = application as MainApp // late initialising of MainApp class

        // Recycler View Work
        val layoutManager = LinearLayoutManager(this) // create a LinearLayout and assign it to variable passing this class
        binding.recyclerView.layoutManager = layoutManager // set the recyclerView layoutManager to the one we created above
        binding.recyclerView.adapter = MealLocationAdapter(app.mealLocations.findAll() , this) // set the recycler view adapter to our custom adapter


    }
      // --menu Options --
//    Create menu override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, MealLocationActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this , MealLocationMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // instantiates the registerForActivityResult, and the .launch method is used in the onOptionsItemSelected for item.map
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.mealLocations.findAll().size)
        }
    }

    // instantiates the registerForActivityResult, and the .launch method is used in the onOptionsItemSelected for item.map
    private val mapIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { }




    // MealLocationListener since we implement the interface we need to use it's functions
    override fun onMealLocationClick(mealLocation: MealLocationModel , pos:Int) {
        val launcherIntent = Intent(this, MealLocationActivity::class.java)
        launcherIntent.putExtra("mealLocation_edit" , mealLocation) // using parcelable to create a key("mealLocation_edit") and passes a value of (mealLocation object)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    // instantiates the registerForActivityResult, and the .launch method is used in the onMealLocationClickClass
    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.mealLocations.findAll().size)
        } else if (it.resultCode == 99) {
            (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
    }





}



