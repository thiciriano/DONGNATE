package com.dongnate.data.repository

import com.dongnate.data.local.JsonDatabase
import com.dongnate.data.model.Ong
import com.dongnate.util.AuthHelper

class OngRepositoryImpl : OngRepository {

    override fun create(ong: Ong): Result<Ong> {
        val db = JsonDatabase.get()
        if (db.ongs.any { it.cnpj == ong.cnpj }) {
            return Result.failure(Exception("CNPJ já cadastrado"))
        }
        val newOng = ong.copy(
            id = (db.ongs.maxOfOrNull { it.id } ?: 0) + 1,
            createdAt = AuthHelper.currentTimestamp()
        )
        db.ongs.add(newOng)
        JsonDatabase.save()
        return Result.success(newOng)
    }

    override fun findByUserId(userId: Int): Ong? {
        return JsonDatabase.get().ongs.find { it.userId == userId }
    }

    override fun findAll(): List<Ong> {
        return JsonDatabase.get().ongs.toList()
    }

    override fun findById(id: Int): Ong? {
        return JsonDatabase.get().ongs.find { it.id == id }
    }
}
