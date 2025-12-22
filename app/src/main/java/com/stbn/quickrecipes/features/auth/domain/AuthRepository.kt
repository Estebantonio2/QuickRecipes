package com.stbn.quickrecipes.features.auth.domain

import com.stbn.quickrecipes.core.util.DataError
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.auth.domain.models.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User, DataError.Network>
    suspend fun register(name: String, email: String, password: String): Result<User, DataError.Network>
    fun getCurrentUser(): User?
    fun logout()
}