package com.dongnate.data.repository

import com.dongnate.data.local.JsonDatabase
import com.dongnate.data.model.Request
import com.dongnate.util.AuthHelper

class RequestRepositoryImpl : RequestRepository {

    override fun create(request: Request): Result<Request> {
        val db = JsonDatabase.get()
        val activeCount = db.requests.count { it.ongId == request.ongId && it.status == "active" }
        if (activeCount >= 10) {
            return Result.failure(Exception("Limite de 10 pedidos ativos atingido"))
        }
        val newRequest = request.copy(
            id = (db.requests.maxOfOrNull { it.id } ?: 0) + 1,
            createdAt = AuthHelper.currentTimestamp()
        )
        db.requests.add(newRequest)
        JsonDatabase.save()
        return Result.success(newRequest)
    }

    override fun findAll(): List<Request> {
        return JsonDatabase.get().requests.filter { it.status == "active" }
    }

    override fun findByOngId(ongId: Int): List<Request> {
        return JsonDatabase.get().requests.filter { it.ongId == ongId }
    }

    override fun findById(id: Int): Request? {
        return JsonDatabase.get().requests.find { it.id == id }
    }

    override fun update(request: Request): Result<Request> {
        val db = JsonDatabase.get()
        val index = db.requests.indexOfFirst { it.id == request.id }
        if (index == -1) return Result.failure(Exception("Pedido não encontrado"))
        db.requests[index] = request
        JsonDatabase.save()
        return Result.success(request)
    }

    override fun delete(id: Int) {
        val db = JsonDatabase.get()
        db.requests.removeAll { it.id == id }
        JsonDatabase.save()
    }
}
