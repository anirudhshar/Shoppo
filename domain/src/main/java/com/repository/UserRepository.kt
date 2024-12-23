package com.repository

import com.model.UserDomainModel
import com.network.ResultWrapper

interface UserRepository {

    suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel>
    suspend fun register(email: String, password: String, name: String): ResultWrapper<UserDomainModel>

}