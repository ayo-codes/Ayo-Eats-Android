package org.wit.ayoeats.models

import timber.log.Timber.i


val users = ArrayList<User>()

class UserMemStore : UserStore {
    override fun create(user: User) {
        users.add(user)
        logAll()

    }

    override fun loginUser(user: User): String {
            if (findByEmail(user.email, users) !== null) {
                i("user exists")
                val currentUser = (findByEmail(user.email, users))
                if (user.password == currentUser?.password) {
                    i("passwords match")
                    return "User Found"
                } else {
                    i("wrong password")
                    return "Wrong Password"
                }
            }else {
                i("user does not exist")
                return "User Not Found"
            }
        }


    }

    fun logAll() {
        users.forEach { i("$it") }
    }

    fun findByEmail(email: String , users :ArrayList<User> ) :User? {
      return users.find{ it.email == email }

    }
