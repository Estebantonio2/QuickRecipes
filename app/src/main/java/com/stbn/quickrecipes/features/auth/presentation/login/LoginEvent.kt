package com.stbn.quickrecipes.features.auth.presentation.login

import com.stbn.quickrecipes.core.presentation.util.UiText

sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
    data class Error(val error: UiText): LoginEvent
}