package ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import ui.Side

@Composable
fun <T> SlideTransition(
    items: List<T>,
    currentIndex: Int,
    orientation: Orientation = Orientation.Horizontal,
    side: Side = Side.Right,
    duration: Int = 300,
    delayIn: Int = 0,
    delayOut: Int = 0,
    content: @Composable (Int, T) -> Unit,
) {
    Box {
        items.forEachIndexed { index, item ->
            SlideAppearAnimation(
                visible = index == currentIndex,
                orientation = orientation,
                side = side,
                duration = duration,
                delayIn = delayIn,
                delayOut = delayOut,
            ) {
                content(index, item)
            }
        }
    }
}