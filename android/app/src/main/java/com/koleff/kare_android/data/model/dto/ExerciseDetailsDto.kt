package com.koleff.kare_android.data.model.dto

import com.squareup.moshi.Json

data class ExerciseDetailsDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "muscle_group")
    val muscleGroup: MuscleGroup,
    @field:Json(name = "machine")
    val machineType: MachineType,
    @field:Json(name = "video_url")
    val videoUrl: String
)
