package com.repository

import com.model.UserDomainModel
import com.network.NetWorkService
import com.network.ResultWrapper

class UserRepoImpl(private val networkService: NetWorkService) : UserRepository {

    override suspend fun register(email: String, password: String, name: String) =
        networkService.register(email, password, name)

    override suspend fun login(email: String, password: String) =
        networkService.login(email, password)

}