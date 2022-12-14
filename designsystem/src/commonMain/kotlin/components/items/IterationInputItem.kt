package components.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.inputs.InputRepeat
import components.inputs.InputWeight
import controls.DividerPrimary

@Composable
fun IterationInputItem(
    modifier: Modifier = Modifier,
    weight: String,
    repeat: String,
    updateWeight: (value: String) -> Unit,
    updateRepeat: (value: String) -> Unit
) = Column(modifier = modifier.width(60.dp).padding(vertical = 4.dp)) {

    InputWeight(
        value = weight,
        onValueChange = updateWeight
    )

    DividerPrimary(
        modifier = Modifier.padding(horizontal = 8.dp)
    )

    InputRepeat(
        value = repeat,
        onValueChange = updateRepeat
    )
}
