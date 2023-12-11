package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skiko.currentNanoTime
import ui.composable.shaders.drawStar
import kotlin.math.min
import kotlin.random.Random

@Composable
fun BeamArea(
    color: Color,
    particleColor: Color,
    startPoint: Offset,
    endPoint: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val particles = remember { mutableStateListOf<Particle>() }
    val particleAlphaAnimations = remember { mutableMapOf<Particle, Animatable<Float, AnimationVector1D>>() }

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
            while (particles.size < 150) {
                delay(duration / 100L)
                if (Random(currentNanoTime()).nextInt(1, 100) < 90) {
                    val newParticle = Particle(
                        x = animatedX.value + Random(currentNanoTime()).nextInt(-10, 10).toFloat(),
                        y = animatedY.value + Random(currentNanoTime()).nextInt(-10, 10).toFloat(),
                        alpha = 1.0F,
                    )

                    particles.add(newParticle)
                    particleAlphaAnimations[newParticle] = Animatable(newParticle.alpha)

                    launch {
                        particleAlphaAnimations[newParticle]!!.animateTo(
                            0.0F,
                            tween(duration * 2),
                        )
                    }
                }
            }
        }
    }

    Canvas(modifier = modifier) {
        val color1 = color
        val color2 = Color(
            min(1.0F, color.red + 0.2F),
            min(1.0F, color.green + 0.2F),
            min(1.0F, color.blue + 0.2F),
        )

        drawLine(
            brush = SolidColor(color1),
            start = startPoint,
            end = Offset(
                animatedX.value,
                animatedY.value
            ),
            strokeWidth = 10F,
        )

        particles.forEach { particle ->
            drawStar(
                center = Offset(
                    particle.x,
                    particle.y,
                ),
                size = 20F,
                color = particleColor.copy(alpha = particleAlphaAnimations[particle]?.value ?: 0.0F),
            )
        }
    }
}