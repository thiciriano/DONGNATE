package com.dongnate.data.local

import android.content.Context
import com.dongnate.data.model.*
import com.dongnate.util.AuthHelper
import com.google.gson.Gson
import java.io.File

object JsonDatabase {

    private const val FILE_NAME = "dongnate_db.json"
    private val gson = Gson()
    private var db: AppDatabase = AppDatabase()
    private var filesDir: File? = null

    fun init(ctx: Context) {
        filesDir = ctx.applicationContext.filesDir
        load()
        ensureTestAccounts()
    }

    private fun file(): File = File(filesDir ?: throw IllegalStateException("Database not initialized"), FILE_NAME)

    private fun load() {
        val f = try { file() } catch (e: Exception) { null }
        if (f != null && f.exists()) {
            db = try {
                gson.fromJson(f.readText(), AppDatabase::class.java) ?: AppDatabase()
            } catch (_: Exception) {
                AppDatabase()
            }
        }
    }

    fun save() {
        try {
            file().writeText(gson.toJson(db))
        } catch (_: Exception) {}
    }

    fun get(): AppDatabase = db

    private fun ensureTestAccounts() {
        val now = AuthHelper.currentTimestamp()
        val hashedPass = AuthHelper.hash("123456")

        val testDonorEmail = "doador@teste.com"
        if (db.users.none { it.email == testDonorEmail }) {
            db.users.add(User(
                id = (db.users.maxOfOrNull { it.id } ?: 0) + 1,
                fullName = "Doador Exemplo",
                email = testDonorEmail,
                passwordHash = hashedPass,
                role = "doador",
                createdAt = now
            ))
        }

        val testOngEmail = "ong@teste.com"
        if (db.users.none { it.email == testOngEmail }) {
            val ongUser = User(
                id = (db.users.maxOfOrNull { it.id } ?: 0) + 1,
                fullName = "Responsável ONG",
                email = testOngEmail,
                passwordHash = hashedPass,
                role = "ong",
                createdAt = now
            )
            db.users.add(ongUser)
            
            // Also ensure the ONG profile exists for this user
            if (db.ongs.none { it.userId == ongUser.id }) {
                db.ongs.add(Ong(
                    id = (db.ongs.maxOfOrNull { it.id } ?: 0) + 1,
                    userId = ongUser.id,
                    organizationName = "ONG Esperança Viva",
                    description = "Ajudando famílias em situação de vulnerabilidade com alimentos e roupas.",
                    cnpj = "12.345.678/0001-99",
                    fullAddress = "Rua da Solidariedade, 123",
                    phone = "(11) 98888-7777",
                    createdAt = now
                ))
            }
        }
        

        if (db.requests.isEmpty() && db.ongs.isNotEmpty()) {
            db.requests.add(Request(
                id = 1,
                ongId = db.ongs.first().id,
                title = "Cesta Básica",
                description = "Necessitamos de cestas básicas para 50 famílias em situação de risco.",
                category = "Alimentos",
                quantity = 50,
                unit = "Cestas",
                urgency = "Alta",
                deliveryLocation = "Sede da ONG",
                createdAt = now
            ))
        }

        save()
    }
}
