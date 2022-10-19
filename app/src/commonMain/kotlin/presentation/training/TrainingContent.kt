package presentation.training

import DesignComponent
import GlobalState
import Graph
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import components.BackHandler
import components.Error
import components.Header
import components.Loading
import components.Root2
import components.items.EditExerciseItem
import controls.ButtonPrimary
import presentation.map.toExerciseComponent
import rememberDispatcher
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import selectState

@Composable
fun TrainingContent() {
    val rootController = LocalRootController.current

    val dispatcher = rememberDispatcher()
    val state by selectState<GlobalState, TrainingState> { this.trainingState }

    val presenter = remember { TrainingPresenter(dispatcher) }

    Root2(
        modifier = Modifier.fillMaxSize(),
        loading = {
            Loading(state.loading)
        },
        error = {
            Error(message = state.error, close = { dispatcher(TrainingAction.Error(null)) })
        },
        back = {
            BackHandler(action = { rootController.popBackStack() })
        },
        header = {
            Header(
                title = "Exercises!",
                save = {
                    dispatcher(TrainingAction.ValidateExercises)
                    dispatcher(TrainingAction.CalculateDuration)
                    dispatcher(TrainingAction.CalculateValues)
                    presenter.saveTraining(state.training) {
                        rootController.push(Graph.Review.link)
                    }
                }
            )
        },
        footer = {
            NewExerciseItem(
                modifier = Modifier.fillMaxWidth().padding(DesignComponent.size.space),
                onClick = { dispatcher(TrainingAction.AddExerciseAction) }
            )
        },
        content = {
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
        }
    )
}

@Composable
private fun NewExerciseItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = ButtonPrimary(
    modifier = modifier,
    text = "Add Exercise",
    onClick = { onClick.invoke() }
)