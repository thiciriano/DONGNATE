package com.dongnate.data.model

data class User(
    val id: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val passwordHash: String = "",
    val role: String = "doador",
    val accountStatus: String = "active",
    val createdAt: String = ""
)

data class Ong(
    val id: Int = 0,
    val userId: Int = 0,
    val organizationName: String = "",
    val description: String = "",
    val cnpj: String = "",
    val fullAddress: String = "",
    val phone: String = "",
    val website: String = "",
    val createdAt: String = ""
)

data class Request(
    val id: Int = 0,
    val ongId: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "food",
    val quantity: Int? = null,
    val unit: String = "",
    val urgency: String = "medium",
    val status: String = "active",
    val deliveryLocation: String = "",
    val createdAt: String = ""
)

data class Interest(
    val id: Int = 0,
    val requestId: Int = 0,
    val userId: Int = 0,
    val message: String = "",
    val status: String = "pending",
    val createdAt: String = ""
)

data class AppDatabase(
    val users: MutableList<User> = mutableListOf(),
    val ongs: MutableList<Ong> = mutableListOf(),
    val requests: MutableList<Request> = mutableListOf(),
    val interests: MutableList<Interest> = mutableListOf()
)
