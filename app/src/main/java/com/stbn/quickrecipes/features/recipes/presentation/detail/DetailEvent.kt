package com.stbn.quickrecipes.features.recipes.presentation.detail

import com.stbn.quickrecipes.core.presentation.util.UiText

sealed interface DetailEvent {
    data class Error(val error: UiText): DetailEvent
}