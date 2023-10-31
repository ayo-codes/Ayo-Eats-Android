package org.wit.ayoeats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.ayoeats.databinding.CardMealLocationBinding
import org.wit.ayoeats.models.MealLocationModel


// interface for the listener to help with when the card is clicked,
interface MealLocationListener {
    fun onMealLocationClick(mealLocation: MealLocationModel)
}

// added a listener as a parameter of the adapter, which is utilised by the onBindViewHolder
class MealLocationAdapter constructor( private var mealLocations: List<MealLocationModel> , private val listener: MealLocationListener): RecyclerView.Adapter<MealLocationAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainHolder {
        val binding = CardMealLocationBinding.inflate(LayoutInflater.from(parent.context), parent , false)

        return MainHolder(binding)


    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mealLocation = mealLocations[holder.adapterPosition]
        holder.bind(mealLocation , listener)
    }

    override fun getItemCount(): Int {
        return mealLocations.size
    }

    class MainHolder(private val binding: CardMealLocationBinding) : RecyclerView.ViewHolder(binding.root){

        // bind takes a listener as a parameter
        fun bind(mealLocation: MealLocationModel, listener: MealLocationListener ) {
            binding.mealName.text = mealLocation.mealName
            binding.mealDescription.text = mealLocation.mealDescription
            Picasso.get().load(mealLocation.image).resize(200,200).into(binding.imageIcon) // this gets the image , resizes it and binds it to the imageicon UI
            binding.root.setOnClickListener { listener.onMealLocationClick(mealLocation) }
        }
    }
}