package com.dongnate.util

import com.dongnate.data.model.User

object Session {
    var currentUser: User? = null

    fun isLoggedIn() = currentUser != null
    fun isOng() = currentUser?.role == "ong"
    fun isDonor() = currentUser?.role == "doador"
    fun logout() { currentUser = null }
}
