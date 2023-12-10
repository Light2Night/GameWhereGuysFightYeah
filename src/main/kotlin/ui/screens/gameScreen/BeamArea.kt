package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import colorBackgroundSecond
import colorBackgroundSecondLighter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skiko.currentNanoTime
import kotlin.random.Random

@Composable
fun BeamArea(
    startPoint: Offset,
    endPoint: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val particles = remember { mutableStateListOf<Particle>() }

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

        launch {
            while (particles.size < 100) {
                delay(duration / 100L)
                if (Random(currentNanoTime()).nextInt(1, 100) < 50) {
                    particles.add(
                        Particle(
                            x = animatedX.value + Random(currentNanoTime()).nextInt(-10, 10).toFloat(),
                            y = animatedY.value + Random(currentNanoTime()).nextInt(-10, 10).toFloat(),
                            alpha = 1.0F,
                        )
                    )
                }
            }
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

        particles.forEach { particle ->
            drawCircle(
                center = Offset(
                    particle.x,
                    particle.y,
                ),
                color = colorBackgroundSecondLighter.copy(alpha = particle.alpha),
                radius = 10F,
            )
        }
    }
}