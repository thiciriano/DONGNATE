package com.dongnate.data.repository

import com.dongnate.data.model.Interest
import com.dongnate.data.model.Ong
import com.dongnate.data.model.Request
import com.dongnate.data.model.User

interface UserRepository {
    fun create(user: User): Result<User>
    fun findByEmail(email: String): User?
    fun findById(id: Int): User?
    fun update(user: User): Result<User>
}

interface OngRepository {
    fun create(ong: Ong): Result<Ong>
    fun findByUserId(userId: Int): Ong?
    fun findAll(): List<Ong>
    fun findById(id: Int): Ong?
}

interface RequestRepository {
    fun create(request: Request): Result<Request>
    fun findAll(): List<Request>
    fun findByOngId(ongId: Int): List<Request>
    fun findById(id: Int): Request?
    fun update(request: Request): Result<Request>
    fun delete(id: Int)
}

interface InterestRepository {
    fun create(interest: Interest): Result<Interest>
    fun findByRequestId(requestId: Int): List<Interest>
    fun findByUserId(userId: Int): List<Interest>
    fun exists(userId: Int, requestId: Int): Boolean
}
