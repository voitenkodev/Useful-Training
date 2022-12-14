package components.inputs

import DesignComponent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import components.labels.InputLabel
import controls.IconPrimary
import controls.InputFieldPrimary
import controls.secondaryBackground

@Composable
fun InputEmail(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    InputFieldPrimary(
        modifier = modifier
            .secondaryBackground()
            .padding(16.dp),
        value = value,
        onValueChange = onValueChange,
        trailing = value?.isNotEmpty().takeIf { it == true }?.let {
            {
                IconPrimary(
                    imageVector = Icons.Default.Clear,
                    color = DesignComponent.colors.caption,
                    onClick = { onValueChange.invoke(String()) }
                )
            }
        },
        leading = { InputLabel(text = "Email") },
        maxLines = 1,
        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )
    )
}