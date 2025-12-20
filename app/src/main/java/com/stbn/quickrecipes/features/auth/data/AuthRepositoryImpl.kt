package com.stbn.quickrecipes.features.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.stbn.quickrecipes.core.util.DataError
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.auth.domain.AuthRepository
import com.stbn.quickrecipes.features.auth.domain.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<User, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<User, DataError.Network> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                val profileUpdate = userProfileChangeRequest {
                    displayName = name
                }
                firebaseUser.updateProfile(profileUpdate).await()

                val user = User(
                    id = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: name
                )
                Result.Success(user)
            } else {
                Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val error = when (e) {
                is com.google.firebase.FirebaseNetworkException -> DataError.Network.NO_INTERNET
                is com.google.firebase.auth.FirebaseAuthUserCollisionException -> DataError.Network.CONFLICT
                else -> DataError.Network.SERVER_ERROR
            }
            Result.Error(error)
        }
    }
}