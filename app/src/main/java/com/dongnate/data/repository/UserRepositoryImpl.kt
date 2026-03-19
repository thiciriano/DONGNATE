package com.dongnate.data.repository

import com.dongnate.data.local.JsonDatabase
import com.dongnate.data.model.User
import com.dongnate.util.AuthHelper

class UserRepositoryImpl : UserRepository {

    override fun create(user: User): Result<User> {
        val db = JsonDatabase.get()
        if (db.users.any { it.email == user.email }) {
            return Result.failure(Exception("E-mail já cadastrado"))
        }
        val newUser = user.copy(
            id = (db.users.maxOfOrNull { it.id } ?: 0) + 1,
            passwordHash = AuthHelper.hash(user.passwordHash),
            createdAt = AuthHelper.currentTimestamp()
        )
        db.users.add(newUser)
        JsonDatabase.save()
        return Result.success(newUser)
    }

    override fun findByEmail(email: String): User? {
        return JsonDatabase.get().users.find { it.email == email.lowercase() }
    }

    override fun findById(id: Int): User? {
        return JsonDatabase.get().users.find { it.id == id }
    }

    override fun update(user: User): Result<User> {
        val db = JsonDatabase.get()
        val index = db.users.indexOfFirst { it.id == user.id }
        if (index == -1) return Result.failure(Exception("Usuário não encontrado"))
        db.users[index] = user
        JsonDatabase.save()
        return Result.success(user)
    }
}
