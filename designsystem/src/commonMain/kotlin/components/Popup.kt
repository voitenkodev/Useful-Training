package components

import DesignComponent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import controls.ButtonPrimary
import controls.TextFieldBody1
import controls.TextFieldH2
import controls.secondaryBackground

@Composable
fun Popup(
    modifier: Modifier = Modifier,
    visibility: Boolean = false,
    title: String?,
    message: String?,
    button: String?,
    click: () -> Unit,
    back: () -> Unit
) {

    if (visibility) {

        val interactionSource = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = back
                )
        )
    }

    AnimatedVisibility(
        visible = visibility,
        modifier = modifier.fillMaxWidth(),
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        Column(
            modifier = Modifier
                .padding(DesignComponent.size.space)
                .secondaryBackground()
                .padding(DesignComponent.size.space),
            verticalArrangement = Arrangement.spacedBy(DesignComponent.size.space),
            content = {

                TextFieldH2(text = title, fontWeight = FontWeight.Bold)

                TextFieldBody1(text = message)

                button?.let {
                    ButtonPrimary(
                        modifier = Modifier.fillMaxWidth(),
                        text = button,
                        onClick = click
                    )
                }
            }
        )
    }
}