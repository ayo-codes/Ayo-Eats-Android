package org.wit.ayoeats.views.meallocationlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.ayoeats.R
import org.wit.ayoeats.adapters.MealLocationAdapter
import org.wit.ayoeats.adapters.MealLocationListener
import org.wit.ayoeats.databinding.ActivityMealLocationListBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel


class MealLocationListView : AppCompatActivity() , MealLocationListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityMealLocationListBinding // binding variable to connect code to the UI

    private var position: Int = 0 // for the position of the adapter element
    lateinit var presenter: MealLocationListPresenter /// late initialise a type of MealLocationListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealLocationListBinding.inflate(layoutInflater) // sets binding variable to the XML that is inflated
        setContentView(binding.root)

        // menu tool binding
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        //presenter
        presenter = MealLocationListPresenter(this) // initialise the presenter passing this view as the view in the parameter
        //app
        app = application as MainApp // late initialising of MainApp class

        // Recycler View Work
        val layoutManager = LinearLayoutManager(this) // create a LinearLayout and assign it to variable passing this class
        binding.recyclerView.layoutManager = layoutManager // set the recyclerView layoutManager to the one we created above
        loadMealLocations() // gets the locations also calls the onRefresh Function

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
                presenter.doAddMealLocation() // calls this in the presenter
            }
            R.id.item_map -> {
                presenter.doShowMealLocationsMap() // calls this in the presenter
            }
            R.id.item_logout -> {
                presenter.doLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    // MealLocationListener since we implement the interface we need to use it's functions
    override fun onMealLocationClick(mealLocation: MealLocationModel , position:Int) {
       this.position = position
        presenter.doEditMealLocation(mealLocation, this.position)
    }


    // load the list of Locations
    private fun loadMealLocations() {

        binding.recyclerView.adapter = MealLocationAdapter(
            presenter.getMealLocations(),
            this
        ) // set the recycler view adapter to our custom adapter
        onRefresh()
    }

    // refresh or update the recycler view adapter
    fun onRefresh(){
        binding.recyclerView.adapter?.notifyItemRangeChanged(0, presenter.getMealLocations().size)
    }

    fun onDelete(position: Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

}



