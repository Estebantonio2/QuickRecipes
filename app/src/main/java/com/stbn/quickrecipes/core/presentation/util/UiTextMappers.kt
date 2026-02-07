package com.stbn.quickrecipes.core.presentation.util

import com.stbn.quickrecipes.R
import com.stbn.quickrecipes.core.util.DataError

fun DataError.asUiText(): UiText {
    return when(this) {
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.CONFLICT -> UiText.StringResource(R.string.error_email_exists)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.error_unauthorized)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}