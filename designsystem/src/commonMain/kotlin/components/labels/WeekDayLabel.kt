package components.labels

import DesignComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import controls.TextFieldBody1

@Composable
fun WeekDayLabel(
    modifier: Modifier = Modifier,
    weekDayEnglish: String?,
) {
    val c0 = DesignComponent.colors.accent_primary
    val c1 = DesignComponent.colors.unique.color1
    val c2 = DesignComponent.colors.unique.color2
    val c3 = DesignComponent.colors.unique.color3
    val c4 = DesignComponent.colors.unique.color4
    val c5 = DesignComponent.colors.unique.color5
    val c6 = DesignComponent.colors.unique.color6
    val c7 = DesignComponent.colors.unique.color7

    val txt = remember(weekDayEnglish) { weekDayEnglish?.uppercase() }

    val backgroundColor = remember(weekDayEnglish) {
        when (weekDayEnglish?.uppercase()) {
            "MONDAY" -> c1
            "TUESDAY" -> c2
            "WEDNESDAY" -> c3
            "THURSDAY" -> c4
            "FRIDAY" -> c5
            "SATURDAY" -> c6
            "SUNDAY" -> c7
            else -> c0
        }
    }

    TextFieldBody1(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = DesignComponent.shape.circleShape
            ).padding(horizontal = 8.dp, vertical = 2.dp),
        text = txt,
        fontWeight = FontWeight.Bold
    )
}