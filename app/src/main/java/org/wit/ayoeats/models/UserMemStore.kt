package org.wit.ayoeats.models

import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber.i

var lastUserId = 0L

internal fun getUserId(): Long {
    return lastUserId++
}

class UserMemStore : AppCompatActivity(), UserStore {
    val users = ArrayList<User>()

    override fun create(user: User) {
        user.id = getUserId()
        users.add(user)
        logAll()

    }

    override fun checkCredentials(user: User): String {

        if (findByEmail(user.email, users) !== null) {
            i("user exists")
            val currentUser = (findByEmail(user.email, users))
            i("$currentUser from after the findByEmail within checkCredentials" )
            if (user.password == currentUser?.password) {
                i("passwords match")
//                app.currentUser = app.updateCurrentUser(currentUser)
//                i("${app.currentUser} from the loginUser function after being assigned")
////                   app.currentUser?.id = currentUser.id
////                    app.currentUser?.firstname = currentUser.firstname
////                    app.currentUser?.email = currentUser.email
////                    app.currentUser?.password = currentUser.password
////                    app.currentUser?.surname = currentUser.surname
                return "User Found"
            } else {
                i("wrong password")
                return "Wrong Password"
            }
        } else {
            i("user does not exist")
            return "User Not Found"
        }
    }

    override fun loggedInUser(user: User): User? {
        return findByEmail(user.email, users)
    }

    override fun findAll(): List<User> {
        return users
    }

    fun logAll() {
        users.forEach { i("$it") }
    }

    fun findByEmail(email: String , users :ArrayList<User> ) :User? {
        return users.find{ it.email == email }

    }
}





