package org.wit.ayoeats.models

interface UserStore {


    fun create (user : User)

    fun checkCredentials (user: User) : String

    fun loggedInUser (user: User) : User?

    fun findAll(): List<User>


}