package ui.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ui.Side

@Composable
fun SlideAppearAnimation(
    visible: Boolean,
    orientation: Orientation = Orientation.Horizontal,
    side: Side = Side.Right,
    duration: Int = 300,
    delayIn: Int = 0,
    delayOut: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutLinearInEasing,
                delayMillis = delayIn,
            )
        ) + if (orientation == Orientation.Horizontal) {
            slideInHorizontally(
                initialOffsetX = { if (side == Side.Right) it else -it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutSlowInEasing,
                    delayMillis = delayIn,
                )
            )
        } else {
            slideInVertically(
                initialOffsetY = { if (side == Side.Down) -it else it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutSlowInEasing,
                    delayMillis = delayIn,
                )
            )
        },
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutLinearInEasing,
                delayMillis = delayOut,
            )
        ) + if (orientation == Orientation.Horizontal) {
            slideOutHorizontally(
                targetOffsetX = { if (side == Side.Right) it else -it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutSlowInEasing,
                    delayMillis = delayOut,
                )
            )
        } else {
            slideOutVertically(
                targetOffsetY = { if (side == Side.Down) -it else -it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutSlowInEasing,
                    delayMillis = delayOut,
                )
            )
        },
    ) {
        content(modifier)
    }
}