package components.items

import DesignComponent
import LineChart
import PointLine
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import controls.TextFieldBody2
import controls.secondaryBackground

@Composable
fun LineChartItem(
    modifier: Modifier = Modifier,
    lines: List<PointLine>,
    onClick: (PointLine, Int) -> Unit
) = LineChartBox(
    modifier = modifier
        .secondaryBackground()
        .padding(DesignComponent.size.space),
    lines = lines,
    onClick = onClick
)

@Composable
private fun LineChartBox(
    modifier: Modifier = Modifier,
    lines: List<PointLine>,
    onClick: (PointLine, Int) -> Unit
) {

    val minY = remember(lines) { lines.minOfOrNull { it.yValue.min() }?.toInt() ?: 0 }
    val maxY = remember(lines) { lines.maxOfOrNull { it.yValue.max() }?.toInt() ?: 0 }
    val middleY = remember(lines) { ((minY + maxY) / 2) }
    val threeQuartersY = remember(lines) { ((maxY + middleY) / 2) }
    val quarterY = remember(lines) { ((minY + middleY) / 2) }
    val maxCount = remember(lines) { lines.maxOfOrNull { it.yValue.lastIndex } ?: 0 }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DesignComponent.size.space)
    ) {

        NameLabels(
            lines = lines
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(DesignComponent.size.space)
        ) {
            YLabels(
                modifier = Modifier.fillMaxHeight(),
                maxY = maxY,
                threeQuartersY = threeQuartersY,
                middleY = middleY,
                quarterY = quarterY,
                minY = minY
            )

            Column {
                LineChart(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    lines = lines,
                    onClick = onClick
                )

                XLabels(
                    modifier = Modifier.fillMaxWidth(),
                    maxCount = maxCount
                )
            }
        }
    }
}

@Composable
private fun NameLabels(
    modifier: Modifier = Modifier,
    lines: List<PointLine>
) = LazyRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(DesignComponent.size.space),
    content = { itemsIndexed(lines, key = { index, _ -> index }) { _, item -> Label(label = item.label, color = item.lineColor) } }
)

@Composable
private fun XLabels(
    modifier: Modifier = Modifier,
    maxCount: Int
) = Row(
    modifier = modifier.height(24.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Bottom
) {
    val texts = remember(maxCount) {
        List(maxCount) { index -> (index + 1).toString() }
    }

    texts.forEach { TextFieldBody2(text = it) }
}

@Composable
private fun YLabels(
    modifier: Modifier = Modifier,
    maxY: Int,
    threeQuartersY: Int,
    middleY: Int,
    quarterY: Int,
    minY: Int
) {
    val max = remember(maxY) { maxY.toString() }
    val min = remember(maxY) { minY.toString() }
    val middle = remember(maxY) { middleY.toString() }
    val quarter = remember(maxY) { quarterY.toString() }
    val threeQuarters = remember(maxY) { threeQuartersY.toString() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TextFieldBody2(
            text = max,
            textAlign = TextAlign.End
        )

        TextFieldBody2(
            text = threeQuarters,
            textAlign = TextAlign.End
        )

        TextFieldBody2(
            text = middle,
            textAlign = TextAlign.End
        )

        TextFieldBody2(
            text = quarter,
            textAlign = TextAlign.End
        )

        TextFieldBody2(
            text = min,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun Label(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(DesignComponent.size.space),
    verticalAlignment = Alignment.CenterVertically
) {
    Spacer(Modifier.size(14.dp).background(color))
    TextFieldBody2(text = label, color = DesignComponent.colors.caption)
}