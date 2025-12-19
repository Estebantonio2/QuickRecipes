package com.stbn.quickrecipes.features.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isPasswordVisible: Boolean = false
)
