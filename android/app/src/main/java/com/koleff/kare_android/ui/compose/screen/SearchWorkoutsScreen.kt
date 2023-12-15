package com.koleff.kare_android.ui.compose.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.koleff.kare_android.data.model.dto.ExerciseDto
import com.koleff.kare_android.data.model.event.OnSearchEvent
import com.koleff.kare_android.ui.compose.LoadingWheel
import com.koleff.kare_android.ui.compose.SearchBar
import com.koleff.kare_android.ui.compose.SearchWorkoutList
import com.koleff.kare_android.ui.compose.scaffolds.SearchListScaffold
import com.koleff.kare_android.ui.view_model.ExerciseViewModel
import com.koleff.kare_android.ui.view_model.WorkoutDetailsViewModel
import com.koleff.kare_android.ui.view_model.WorkoutViewModel

@Composable
fun SearchWorkoutsScreen(
    navController: NavHostController,
    isNavigationInProgress: MutableState<Boolean>,
    exercise: ExerciseDto,
    workoutViewModel: WorkoutViewModel,
    workoutDetailsViewModelFactory: WorkoutDetailsViewModel.Factory,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var selectedWorkoutId by remember { mutableStateOf<Int>(-1) }
    val workoutDetailsViewModel: WorkoutDetailsViewModel = viewModel(
        key = "WorkoutDetailsViewModel-$selectedWorkoutId",
        factory = WorkoutDetailsViewModel.provideWorkoutDetailsViewModelFactory(
            factory = workoutDetailsViewModelFactory,
            workoutId = selectedWorkoutId
        )
    )
    val workoutDetailsState by workoutDetailsViewModel.state.collectAsState()

    //Adds exercise to workout
    LaunchedEffect(workoutDetailsState.workout) {
        if (workoutDetailsState.workout.workoutId != -1) {
            workoutDetailsState.workout.exercises.add(exercise)
            workoutViewModel.updateWorkout(workoutDetailsState.workout)
        }
    }

    SearchListScaffold("Select workout", navController, isNavigationInProgress) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .pointerInput(Unit) {

                //Hide keyboard on tap outside SearchBar
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            }
            .fillMaxSize()

        val workoutState by workoutViewModel.state.collectAsState()
        val allWorkouts = workoutState.workoutList


        //All workouts
        if (workoutState.isLoading) {
            LoadingWheel()
        } else {
            Column(modifier = modifier) {
                //Search bar
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onSearch = { text ->
                        workoutViewModel.onSearchEvent(OnSearchEvent.OnSearchTextChange(text))
                    },
                    onToggleSearch = {
                        workoutViewModel.onSearchEvent(OnSearchEvent.OnToggleSearch())
                    })

                SearchWorkoutList(
                    modifier = Modifier.fillMaxSize(),
                    workoutList = allWorkouts,
                    navController = navController
                ) { workoutId ->

                    //Updates WorkoutDetailsViewModel...
                    selectedWorkoutId = workoutId
                }
            }
        }
    }
}