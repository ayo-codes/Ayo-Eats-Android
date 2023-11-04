package org.wit.ayoeats.models

interface UserStore {
    fun create (user : User)

    fun loginUser (user: User)
}