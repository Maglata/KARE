package com.koleff.kare_android.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseSet(
    val number: Int,
    val reps: Int,
    val weight: Float
) : Parcelable
