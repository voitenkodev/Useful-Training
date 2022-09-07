package ui.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.designsystem.common.DesignComponent
import ui.designsystem.controls.InputFieldBody1
import ui.designsystem.controls.InputFieldBody2

@Composable
fun InputRepeat(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    InputFieldBody2(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textAlign = TextAlign.Center,
        maxLines = 1,
        maxLength = 2,
        placeholder = "0",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        digits = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'),
    )
}

@Composable
fun InputWeight(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    InputFieldBody2(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textAlign = TextAlign.Center,
        maxLines = 1,
        maxLength = 6,
        placeholder = "0.0",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        digits = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ',', '.'),
    )
}

@Composable
fun InputName(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    InputFieldBody1(
        modifier = Modifier
            .background(DesignComponent.colors.primary, RoundedCornerShape(8.dp))
            .border(BorderStroke(2.dp, DesignComponent.colors.special2), RoundedCornerShape(8.dp))
            .padding(12.dp)
            .then(modifier),
        value = value,
        placeholder = "Name of exercise",
        maxLines = 1,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    )
}
