package com.stbn.quickrecipes.features.profile.presentation

sealed interface ProfileAction {
    data object OnLogoutClick: ProfileAction
}