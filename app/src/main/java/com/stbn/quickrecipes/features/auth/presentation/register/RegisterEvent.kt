package com.stbn.quickrecipes.features.auth.presentation.register

import com.stbn.quickrecipes.core.presentation.util.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}