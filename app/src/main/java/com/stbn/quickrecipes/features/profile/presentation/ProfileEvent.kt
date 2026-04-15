package com.stbn.quickrecipes.features.profile.presentation

sealed interface ProfileEvent {
    data object OnLogout: ProfileEvent
}