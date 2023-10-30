package org.wit.ayoeats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.ayoeats.databinding.CardMealLocationBinding
import org.wit.ayoeats.models.MealLocationModel

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