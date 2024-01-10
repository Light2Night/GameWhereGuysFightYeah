package ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.ceil

@Composable
fun Grid(
    items: List<Any>,
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable (item: Any) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        repeat(ceil(items.size / columns.toDouble()).toInt()) { index1 ->
            Row {
                var emptyLeft = columns

                repeat(columns) { index2 ->
                    if (index1 * columns + index2 < items.size) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[index1 * columns + index2])
                        }

                        emptyLeft--
                    }
                }

                if (emptyLeft > 0) {
                    Box(modifier = Modifier.weight(emptyLeft.toFloat()))
                }
            }
        }
    }
}