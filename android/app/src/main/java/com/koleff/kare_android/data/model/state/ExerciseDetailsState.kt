package com.koleff.kare_android.data.model.state

import com.koleff.kare_android.data.model.dto.ExerciseData
import com.koleff.kare_android.data.model.dto.ExerciseDetailsDto
import com.koleff.kare_android.data.model.response.base_response.KareError

data class ExerciseDetailsState (
    val exercise: ExerciseDetailsDto? = null,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error : KareError = KareError.GENERIC
)