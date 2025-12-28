package com.stbn.quickrecipes.features.profile.presentation

import androidx.lifecycle.ViewModel
import com.stbn.quickrecipes.features.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
        }
    }

    private fun logout() {
        authRepository.logout()
    }
}