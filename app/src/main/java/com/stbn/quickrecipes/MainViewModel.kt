package com.stbn.quickrecipes

import androidx.lifecycle.ViewModel
import com.stbn.quickrecipes.features.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    val isLoggedIn: Boolean
        get() {
            val currentUser = authRepository.getCurrentUser()
            println(currentUser)
            return currentUser != null
        }
}