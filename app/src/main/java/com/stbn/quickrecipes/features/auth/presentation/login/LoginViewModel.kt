package com.stbn.quickrecipes.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        _state
            .map { it.email to it.password }
            .distinctUntilChanged()
            .onEach { (email, password) ->
                _state.update { it.copy(
                    canLogin = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8
                ) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email) }
            }
            is LoginAction.OnPasswordChange -> {
                _state.update { it.copy(password = action.password) }
            }
            LoginAction.OnPasswordVisibilityChange -> {
                _state.update { it.copy(isPasswordVisible = !_state.value.isPasswordVisible) }
            }
            LoginAction.OnLoginClick -> {  }
            else -> Unit
        }
    }
}