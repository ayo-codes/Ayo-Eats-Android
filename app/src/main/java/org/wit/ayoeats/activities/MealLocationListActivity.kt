package org.wit.ayoeats.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import org.wit.ayoeats.databinding.ActivityMealLocationListBinding
import org.wit.ayoeats.databinding.CardMealLocationBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.MealLocationModel

class MealLocationListActivity : AppCompatActivity() {


    lateinit var app: MainApp
    private lateinit var binding : ActivityMealLocationListBinding // binding variable to connect code to the UI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealLocationListBinding.inflate(layoutInflater) // sets binding variable to the XML that is inflated
        setContentView(binding.root)
        app = application as MainApp // late initialising of MainApp class

        val layoutManager = LinearLayoutManager(this) // create a LinearLayout and assign it to variable passing this class
        binding.recyclerView.layoutManager = layoutManager // set the recyclerView layoutManager to the one we created above
        binding.recyclerView.adapter = MealLocationAdapter(app.mealLocations) // set the recycler view adapter to our custom adapter
    }
}

class MealLocationAdapter constructor( private var mealLocations: List<MealLocationModel> ): RecyclerView.Adapter<MealLocationAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainHolder {
        val binding = CardMealLocationBinding.inflate(LayoutInflater.from(parent.context), parent , false)

        return MainHolder(binding)


    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mealLocation = mealLocations[holder.adapterPosition]
        holder.bind(mealLocation)
    }

    override fun getItemCount(): Int {
        return mealLocations.size
    }

    class MainHolder(private val binding: CardMealLocationBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(mealLocation: MealLocationModel) {
            binding.mealName.text = mealLocation.mealName
            binding.mealDescription.text = mealLocation.mealDescription
        }
    }
}