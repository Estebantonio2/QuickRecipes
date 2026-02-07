package com.stbn.quickrecipes.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stbn.quickrecipes.R
import com.stbn.quickrecipes.core.presentation.util.UiText
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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _state
            .map { listOf(it.name, it.email, it.password, it.confirmPassword) }
            .distinctUntilChanged()
            .onEach {
                val currentState = _state.value

                val isNameValid = currentState.name.isNotBlank()
                val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()
                val isPasswordValid = currentState.password.length >= 8
                val isConfirmPasswordValid = currentState.confirmPassword.length >= 8

                _state.update { it.copy(
                    canRegister = isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
                ) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnNameChange -> {
                _state.update { it.copy(name = action.name) }
            }
            is RegisterAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email) }
            }
            is RegisterAction.OnPasswordChange -> {
                _state.update { it.copy(password = action.password) }
            }
            is RegisterAction.OnConfirmPasswordChange -> {
                _state.update { it.copy(confirmPassword = action.confirmPassword) }
            }
            RegisterAction.OnPasswordVisibilityChange -> {
                _state.update { it.copy(isPasswordVisible = !_state.value.isPasswordVisible) }
            }
            RegisterAction.OnConfirmPasswordVisibilityChange -> {
                _state.update { it.copy(isConfirmPasswordVisible = !_state.value.isConfirmPasswordVisible) }
            }
            RegisterAction.OnRegisterClick -> register()
            else -> Unit
        }
    }

    private fun register() {
        if (state.value.isRegistering) return
        viewModelScope.launch {
            if (state.value.password != state.value.confirmPassword) {
                eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_passwords_dont_match)))
                return@launch
            }
            _state.update { it.copy(isRegistering = true) }
            val result = authRepository.register(
                name = state.value.name,
                email = state.value.email,
                password = state.value.password
            )
            _state.update { it.copy(isRegistering = false) }

            when (result) {
                is Result.Error -> {
                    eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    authRepository.logout()
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}