package com.stbn.quickrecipes.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stbn.quickrecipes.core.presentation.util.asUiText
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

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
            LoginAction.OnLoginClick -> login()
            else -> Unit
        }
    }

    private fun login() {
        if (state.value.isLoggingIn) return
        viewModelScope.launch {
            _state.update { it.copy(isLoggingIn = true) }
            val result = authRepository.login(
                email = state.value.email,
                password = state.value.password
            )
            _state.update { it.copy(isLoggingIn = false) }

            when (result) {
                is Result.Error -> {
                    eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}