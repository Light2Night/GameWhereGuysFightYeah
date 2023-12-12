package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hugeText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skiko.currentNanoTime
import ui.composable.MedievalText
import kotlin.random.Random

@Composable
fun FlyingText(
    startPoint: Offset,
    text: String,
    color: Color,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val xShift by remember { mutableStateOf(Random(currentNanoTime()).nextInt(-20, 20)) }
    val yShift by remember { mutableStateOf(Random(currentNanoTime()).nextInt(-40, 40)) }
    val shiftedStartPont by remember { mutableStateOf(Offset(startPoint.x + xShift, startPoint.y + yShift)) }

    val animatedX = remember { Animatable(shiftedStartPont.x) }
    val animatedY = remember { Animatable(shiftedStartPont.y) }
    val animatedAlpha = remember { Animatable(1F) }

    LaunchedEffect("animationKey") {
        launch {
            animatedX.animateTo(
                shiftedStartPont.x + Random(currentNanoTime()).nextInt(-20, 20),
                animationSpec = tween(duration),
            )
        }

        launch {
            animatedY.animateTo(
                shiftedStartPont.y - 20F,
                animationSpec = tween(duration / 3),
            )
            animatedY.animateTo(
                shiftedStartPont.y + 40F,
                animationSpec = tween(duration / 3 * 2),
            )
        }

        launch {
            delay(duration / 3 * 2L)
            animatedAlpha.animateTo(
                0F,
                animationSpec = tween(duration / 3),
            )
        }
    }

    Box(modifier) {
        MedievalText(
            text = text,
            fontSize = hugeText,
            color = color.copy(alpha = animatedAlpha.value),
            modifier = Modifier.offset(
                animatedX.value.dp,
                animatedY.value.dp,
            )
        )
    }
}