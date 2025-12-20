package com.stbn.quickrecipes.features.auth.presentation.register

sealed interface RegisterAction {
    data object OnRegisterClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnPasswordVisibilityChange: RegisterAction
    data object OnConfirmPasswordVisibilityChange: RegisterAction
    data class OnNameChange(val name: String): RegisterAction
    data class OnEmailChange(val email: String): RegisterAction
    data class OnPasswordChange(val password: String): RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String): RegisterAction
}