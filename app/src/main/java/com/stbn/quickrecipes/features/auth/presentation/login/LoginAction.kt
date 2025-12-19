package com.stbn.quickrecipes.features.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
    data class OnEmailChange(val email: String): LoginAction
    data class OnPasswordChange(val password: String): LoginAction
}