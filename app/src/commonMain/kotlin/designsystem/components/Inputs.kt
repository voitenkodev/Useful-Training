package designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import designsystem.common.DesignComponent
import designsystem.controls.InputFieldPrimary
import designsystem.controls.InputFieldSecondary

@Composable
fun InputEmail(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    InputFieldPrimary(
        modifier = Modifier.background(DesignComponent.colors.primary, RoundedCornerShape(8.dp))
            .border(BorderStroke(2.dp, DesignComponent.colors.special), RoundedCornerShape(8.dp))
            .padding(12.dp)
            .then(modifier),
        value = value,
        onValueChange = onValueChange,
//            leadingIcon = Icons.Outlined.Person,
        placeholder = "Email",
        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )
    )
}

@Composable
fun InputPassword(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    InputFieldPrimary(
        modifier = Modifier.background(DesignComponent.colors.primary, RoundedCornerShape(8.dp))
            .border(BorderStroke(2.dp, DesignComponent.colors.special), RoundedCornerShape(8.dp))
            .padding(12.dp)
            .then(modifier),
        value = value,
        onValueChange = onValueChange,
//            leadingIcon = Icons.Outlined.Person,
        placeholder = "Password",
        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
fun InputName(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    InputFieldPrimary(
        modifier = Modifier
            .background(DesignComponent.colors.primary, RoundedCornerShape(8.dp))
            .border(BorderStroke(2.dp, DesignComponent.colors.special), RoundedCornerShape(8.dp))
            .padding(12.dp)
            .then(modifier),
        value = value,
        placeholder = "Name of exercise",
        maxLines = 1,
        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Next),
    )
}

@Composable
fun InputWeight(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    InputFieldSecondary(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textAlign = TextAlign.Center,
        maxLines = 1,
        maxLength = 6,
        placeholder = "0.0",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
        digits = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ',', '.'),
    )
}

@Composable
fun InputRepeat(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    InputFieldSecondary(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textAlign = TextAlign.Center,
        maxLines = 1,
        maxLength = 2,
        placeholder = "0",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        digits = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'),
    )
}