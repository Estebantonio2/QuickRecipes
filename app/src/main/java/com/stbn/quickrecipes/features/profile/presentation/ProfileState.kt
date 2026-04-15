package com.stbn.quickrecipes.features.profile.presentation

import com.stbn.quickrecipes.features.auth.domain.model.User

data class ProfileState(
    val user: User?= null
)
