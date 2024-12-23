package com.usecase

import com.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {

    suspend fun execute(email: String, password: String) =
        userRepository.login(email, password)

}