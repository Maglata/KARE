package com.koleff.kare_android.ui.state

import com.koleff.kare_android.data.model.response.base_response.KareError

data class BaseState (
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error : KareError = KareError.GENERIC
)