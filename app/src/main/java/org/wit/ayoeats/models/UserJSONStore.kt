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
import org.wit.ayoeats.helpers.exists
import org.wit.ayoeats.helpers.read
import org.wit.ayoeats.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.Random

const val JSON_FILE1 = "users.json"

val gsonBuilder1: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser1())
    .create()

val listType1: Type = object : TypeToken<ArrayList<User>>() {}.type

fun generateRandomId1(): Long {
    return Random().nextLong()
}

class UserJSONStore(private val context: Context) : UserStore {

    var users = mutableListOf<User>()

    init {
        if (exists(context, JSON_FILE1)) {
            deserialize()
        }
    }

    override fun create(user: User) {
        user.id = generateRandomId1()
        users.add(user)
        serialize()
    }

    override fun checkCredentials(user: User): String {
        if (findByEmail(user.email, users) !== null) {
            Timber.i("user exists")
            val currentUser = (findByEmail(user.email, users))
            Timber.i("$currentUser from after the findByEmail within checkCredentials")
            if (user.password == currentUser?.password) {
                Timber.i("passwords match")
//                app.currentUser = app.updateCurrentUser(currentUser)
//                i("${app.currentUser} from the loginUser function after being assigned")
////                   app.currentUser?.id = currentUser.id
////                    app.currentUser?.firstname = currentUser.firstname
////                    app.currentUser?.email = currentUser.email
////                    app.currentUser?.password = currentUser.password
////                    app.currentUser?.surname = currentUser.surname
                return "User Found"
            } else {
                Timber.i("wrong password")
                return "Wrong Password"
            }
        } else {
            Timber.i("user does not exist")
            return "User Not Found"
        }
    }

    override fun loggedInUser(user: User): User? {
        return findByEmail(user.email, users)
    }

    override fun findAll(): List<User> {
        logAll()
        return users
    }

    private fun serialize() {
        val jsonString = gsonBuilder1.toJson(users, listType1)
        write(context, JSON_FILE1, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE1)
        users = gsonBuilder1.fromJson(jsonString, listType1)
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }

    private fun findByEmail(email: String, users: MutableList<User>): User? {
        return users.find { it.email == email }

    }
}


class UriParser1 : JsonDeserializer<Uri>, JsonSerializer<Uri> {
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