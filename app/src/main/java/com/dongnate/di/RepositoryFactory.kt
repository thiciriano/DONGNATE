package com.dongnate.di

import com.dongnate.data.repository.InterestRepository
import com.dongnate.data.repository.InterestRepositoryImpl
import com.dongnate.data.repository.OngRepository
import com.dongnate.data.repository.OngRepositoryImpl
import com.dongnate.data.repository.RequestRepository
import com.dongnate.data.repository.RequestRepositoryImpl
import com.dongnate.data.repository.UserRepository
import com.dongnate.data.repository.UserRepositoryImpl

abstract class RepositoryFactory {
    abstract fun createUserRepository(): UserRepository
    abstract fun createOngRepository(): OngRepository
    abstract fun createRequestRepository(): RequestRepository
    abstract fun createInterestRepository(): InterestRepository
}

class LocalRepositoryFactory : RepositoryFactory() {
    override fun createUserRepository(): UserRepository = UserRepositoryImpl()
    override fun createOngRepository(): OngRepository = OngRepositoryImpl()
    override fun createRequestRepository(): RequestRepository = RequestRepositoryImpl()
    override fun createInterestRepository(): InterestRepository = InterestRepositoryImpl()
}

object AppRepositories {
    private val factory: RepositoryFactory = LocalRepositoryFactory()

    val users: UserRepository by lazy { factory.createUserRepository() }
    val ongs: OngRepository by lazy { factory.createOngRepository() }
    val requests: RequestRepository by lazy { factory.createRequestRepository() }
    val interests: InterestRepository by lazy { factory.createInterestRepository() }
}
