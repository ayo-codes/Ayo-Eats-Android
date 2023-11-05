package org.wit.ayoeats.models

// interface to manage models , function names and parameters are created but not how they will be used
interface MealLocationStore {


    fun findAll(): List<MealLocationModel> // findall function should return a list with MealLocations
    fun create(mealLocation: MealLocationModel) //should create a new mealLocation of type MealLocationModel Class using it's arguments

    fun update(mealLocation: MealLocationModel) // to update the mealLocation

    fun delete(mealLocation: MealLocationModel) // to delete a mealLocation

    fun findById(id:Long) : MealLocationModel?


}