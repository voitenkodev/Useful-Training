package presentation.training

import DesignComponent
import GlobalState
import Graph
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.BackHandler
import components.Error
import components.Header
import components.Loading
import components.Popup
import components.Root
import components.items.EditExerciseItem
import controls.TextFieldH2
import controls.dashedBorder
import findNavigator
import presentation.map.toExerciseComponent
import rememberDispatcher
import selectState

@Composable
fun TrainingContent() {
    val navigator = findNavigator()
    val dispatcher = rememberDispatcher()
    val state by selectState<GlobalState, TrainingState> { this.trainingState }
    val presenter = remember { TrainingPresenter(dispatcher) }

    Root(
        modifier = Modifier.fillMaxSize(),
        loading = {
            Loading(state.loading)
        },
        error = {
            Error(message = state.error, close = { dispatcher(TrainingAction.Error(null)) })
        },
        back = {
            BackHandler(action = { dispatcher(TrainingAction.AskExitFromTraining(state.exitWarningVisibility.not())) })
        },
        popup = {
            Popup(
                visibility = state.exitWarningVisibility,
                title = "Warning",
                message = "Are you sure to exit from training?",
                button = "Back",
                click = {
                    dispatcher(TrainingAction.AskExitFromTraining(false))
                    navigator.back()
                },
                back = { dispatcher(TrainingAction.AskExitFromTraining(false)) }
            )
        },
        header = {
            Header(
                title = "Exercises!",
                save = {
                    dispatcher(TrainingAction.ValidateExercises)
                    dispatcher(TrainingAction.CalculateDuration)
                    dispatcher(TrainingAction.CalculateValues)
                    presenter.saveTraining(state.training) {
                        navigator.navigate(Graph.Review.link, popToInclusive = true)
                    }
                },
                back = { dispatcher(TrainingAction.AskExitFromTraining(true)) }
            )
        },
        scrollableContent = {
            itemsIndexed(state.training.exercises, key = { _, exercise -> exercise.id }) { index, exercise ->
                EditExerciseItem(
                    modifier = Modifier.animateItemPlacement(),
                    number = index + 1,
                    exercise = exercise.toExerciseComponent(),
                    updateName = { dispatcher(TrainingAction.SetNameExerciseAction(exerciseId = exercise.id, value = it)) },
                    removeExercise = { dispatcher(TrainingAction.RemoveExerciseAction(exerciseId = exercise.id)) },
                    updateWeight = { num, value ->
                        dispatcher(TrainingAction.SetWeightExerciseIterationAction(exerciseId = exercise.id, number = num, value = value))
                        dispatcher(TrainingAction.ProvideEmptyIteration(exerciseId = exercise.id))
                    },
                    updateRepeat = { num, value ->
                        dispatcher(TrainingAction.SetRepeatExerciseIterationAction(exerciseId = exercise.id, number = num, value = value))
                        dispatcher(TrainingAction.ProvideEmptyIteration(exerciseId = exercise.id))
                    }
                )
            }
            item(key = "new_exercise") {
                NewExercise(
                    modifier = Modifier.animateItemPlacement(),
                    onClick = { dispatcher(TrainingAction.AddExerciseAction) }
                )
            }
        }
    )
}

@Composable
private fun NewExercise(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .height(128.dp)
        .dashedBorder(
            width = 2.dp,
            color = DesignComponent.colors.accent_secondary.copy(alpha = 0.5f),
            shape = DesignComponent.shape.default, on = 8.dp, off = 8.dp
        ).clickable {
            onClick.invoke()
        },
    content = {
        TextFieldH2(
            modifier = Modifier.align(Alignment.Center),
            text = "Add Exercise",
            color = DesignComponent.colors.accent_secondary
        )
    }
)