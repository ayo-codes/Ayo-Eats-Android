package org.wit.ayoeats.models

import android.content.Context
import android.net.Uri

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.wit.ayoeats.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*



const val JSON_FILE = "mealLocations.json"

val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()

val listType: Type = object : TypeToken<ArrayList<MealLocationModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}
class MealLocationJSONStore(private val context: Context) : MealLocationStore { // takes a context as a parameter and implements the MealLocationStore

    var mealLocations = mutableListOf<MealLocationModel>()

    init {
        if(exists(context, JSON_FILE)){
            deserialize()
        }
    }
    override fun findAll(): MutableList<MealLocationModel> {
        logAll()
        return mealLocations
    }

    override fun create(mealLocation: MealLocationModel) {
        mealLocation.id = generateRandomId()
        mealLocations.add(mealLocation)
        serialize()
    }

    override fun update(mealLocation: MealLocationModel) {

    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(mealLocations , listType)
        write(context, JSON_FILE , jsonString)
    }

    private fun deserialize(){
        val jsonString = read(context, JSON_FILE)
        mealLocations = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll(){
        mealLocations.forEach { Timber.i("$it") }
    }

    class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Uri {
            return Uri.parse(json?.asString)
        }

        override fun serialize(
            src: Uri?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.toString())
        }
    }
}