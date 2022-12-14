package components.items

import DesignComponent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import components.inputs.InputExerciseName
import components.labels.AccentLabel
import controls.IconPrimary

@Composable
fun InputNameItem(
    number: Int,
    isHelpShowed: Boolean,
    showHelp: (Boolean) -> Unit,
    name: String,
    update: (String) -> Unit,
    remove: () -> Unit
) = Row(
    modifier = Modifier.padding(start = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
) {

    isHelpShowed.not() // todo remove it

    AccentLabel(
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp, end = 4.dp),
        text = "$number",
    )

    InputExerciseName(
        modifier = Modifier.onFocusChanged { showHelp.invoke(it.hasFocus) }.weight(1f),
        value = name,
        onValueChange = update,
    )

    IconPrimary(
        modifier = Modifier.height(20.dp).width(50.dp),
        imageVector = Icons.Filled.Delete,
        color = DesignComponent.colors.caption,
        onClick = remove,
    )
}