package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import colorBackgroundSecond
import kotlinx.coroutines.launch

@Composable
fun BeamArea(
    startPoint: Offset,
    endPoint: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val animatedX = remember { Animatable(startPoint.x) }
    val animatedY = remember { Animatable(startPoint.y) }

    LaunchedEffect("animationKey") {
        launch {
            animatedX.animateTo(
                endPoint.x,
                tween(
                    duration,
                    easing = EaseIn,
                ),
            )
        }
        launch {
            animatedY.animateTo(
                endPoint.y,
                tween(
                    duration,
                    easing = EaseIn,
                ),
            )
        }
    }

    Canvas(modifier = modifier) {
        drawLine(
            color = colorBackgroundSecond,
            start = startPoint,
            end = Offset(
                animatedX.value,
                animatedY.value
            ),
            strokeWidth = 10F,
        )
    }
}