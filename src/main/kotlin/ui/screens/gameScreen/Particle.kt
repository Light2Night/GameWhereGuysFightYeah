package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import colorBackgroundSecond
import kotlinx.coroutines.launch

data class Particle(
    val x: Float,
    val y: Float,
    val alpha: Float,
)

@Composable
fun Particle(
    position: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val alpha = remember { Animatable(1f) }

    LaunchedEffect("animationKey") {
        launch {
            alpha.animateTo(
                0f,
                animationSpec = tween(
                    duration,
                    easing = EaseIn,
                ),
            )
        }
    }

    Box(
        modifier = modifier
            .background(color = colorBackgroundSecond.copy(alpha = alpha.value))
            .offset(position.x.dp, position.y.dp),
    )
}