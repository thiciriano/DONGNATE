package com.dongnate.data.repository

import com.dongnate.data.local.JsonDatabase
import com.dongnate.data.model.Interest
import com.dongnate.util.AuthHelper

class InterestRepositoryImpl : InterestRepository {

    override fun create(interest: Interest): Result<Interest> {
        val db = JsonDatabase.get()
        if (exists(interest.userId, interest.requestId)) {
            return Result.failure(Exception("Você já manifestou interesse neste pedido"))
        }
        val newInterest = interest.copy(
            id = (db.interests.maxOfOrNull { it.id } ?: 0) + 1,
            createdAt = AuthHelper.currentTimestamp()
        )
        db.interests.add(newInterest)
        JsonDatabase.save()
        return Result.success(newInterest)
    }

    override fun findByRequestId(requestId: Int): List<Interest> {
        return JsonDatabase.get().interests.filter { it.requestId == requestId }
    }

    override fun findByUserId(userId: Int): List<Interest> {
        return JsonDatabase.get().interests.filter { it.userId == userId }
    }

    override fun exists(userId: Int, requestId: Int): Boolean {
        return JsonDatabase.get().interests.any { it.userId == userId && it.requestId == requestId }
    }
}
