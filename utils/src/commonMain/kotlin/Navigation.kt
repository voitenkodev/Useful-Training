import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger

@Composable
fun NavigationHost(
    currentScreen: String?,
    screenToRemove: String?,
    animationTyper: AnimationTyper = AnimationTyper.Push(3000),
    isForward: Boolean,
    onScreenRemove: ((String) -> Unit)? = null,
    content: @Composable (String) -> Unit
) {

    val stateHolder = rememberSaveableStateHolder()

    AnimatedTransition(
        targetState = currentScreen,
        animation = animationTyper,
        isForwardDirection = isForward,
        content = { direct ->
            if (direct != null) {
                Logger.i { "current = $currentScreen remove = $screenToRemove" }
                Logger.i { "direct = $direct" }
                stateHolder.SaveableStateProvider(direct) { content(direct) }
            }
        }
    )

    LaunchedEffect(currentScreen, screenToRemove) {
        screenToRemove?.let {
            stateHolder.removeState(it)
            onScreenRemove?.invoke(it)
        }
    }
}

@Composable
private fun <T> AnimatedTransition(
    targetState: T,
    animation: AnimationTyper,
    isForwardDirection: Boolean,
    onAnimationEnd: (() -> Unit)? = null,
    content: @Composable (T) -> Unit
) = AnimatedContentWithCallback(
    targetState = targetState,
    onAnimationEnd = onAnimationEnd ?: { },
    content = { target -> content(target) },
    transitionSpec = when (animation) {
        is AnimationTyper.Present -> presentation(isForwardDirection, animation.animationTime)
        is AnimationTyper.Fade -> crossFade(animation.animationTime)
        AnimationTyper.None -> presentation(isForwardDirection, 1)
        is AnimationTyper.Push -> push(isForwardDirection, animation.animationTime)
    }
)

@Composable
private fun <S> AnimatedContentWithCallback(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform,
    contentAlignment: Alignment = Alignment.TopStart,
    onAnimationEnd: () -> Unit,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    val previousValue = remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = targetState, label = "AnimatedContent")

    if (transition.isRunning != previousValue.value) {
        previousValue.value = transition.isRunning
        if (!transition.isRunning) onAnimationEnd()
    }

    transition.AnimatedContent(
        modifier,
        transitionSpec,
        contentAlignment,
        content = content
    )
}

// __________________ ANIMATIONS __________________

sealed class AnimationTyper {
    object None : AnimationTyper()
    data class Push(val animationTime: Int = 300) : AnimationTyper()
    data class Present(val animationTime: Int = 300) : AnimationTyper()
    data class Fade(val animationTime: Int = 300) : AnimationTyper()
}

fun <T> crossFade(transitionTime: Int): AnimatedContentScope<T>.() -> ContentTransform = {
    (fadeIn(animationSpec = tween(transitionTime)) with fadeOut(animationSpec = tween(transitionTime)))
        .using(SizeTransform(clip = false))
}

fun <T> push(isForward: Boolean, transitionTime: Int): AnimatedContentScope<T>.() -> ContentTransform = {
    if (isForward) {
        (slideInHorizontally(
            animationSpec = tween(transitionTime),
            initialOffsetX = { width -> width })
                + fadeIn(animationSpec = tween(transitionTime)) with slideOutHorizontally(
            animationSpec = tween(transitionTime),
            targetOffsetX = { width -> -width })
                + fadeOut(animationSpec = tween(transitionTime)))
            .using(SizeTransform(clip = false))
    } else {
        (slideInHorizontally(
            animationSpec = tween(transitionTime),
            initialOffsetX = { width -> -width })
                + fadeIn(animationSpec = tween(transitionTime)) with slideOutHorizontally(
            animationSpec = tween(transitionTime), targetOffsetX = { width -> width })
                + fadeOut(animationSpec = tween(transitionTime)))
            .using(SizeTransform(clip = false))
    }
}

fun <T> presentation(isOpen: Boolean, transitionTime: Int): AnimatedContentScope<T>.() -> ContentTransform = {
    if (isOpen) {
        (slideInVertically(
            animationSpec = tween(transitionTime),
            initialOffsetY = { height -> height })
                + fadeIn(animationSpec = tween(transitionTime)) with slideOutVertically(
            animationSpec = tween(transitionTime),
            targetOffsetY = { height -> -(height / 8) })
                + fadeOut(animationSpec = tween(transitionTime)))
            .using(SizeTransform(clip = false))
    } else {
        (slideInVertically(
            animationSpec = tween(transitionTime),
            initialOffsetY = { height -> -(height / 8) })
                + fadeIn(animationSpec = tween(transitionTime)) with slideOutVertically(
            animationSpec = tween(transitionTime), targetOffsetY = { height -> height })
                + fadeOut(animationSpec = tween(transitionTime)))
            .using(SizeTransform(clip = false))
    }
}