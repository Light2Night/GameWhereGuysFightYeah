package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hugeText
import kotlinx.coroutines.launch
import ui.composable.MedievalText

@Composable
fun FlyingText(
    startPoint: Offset,
    text: String,
    color: Color,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val animatedX = remember { Animatable(startPoint.x) }
    val animatedY = remember { Animatable(startPoint.y) }

    LaunchedEffect("animationKey") {
        launch {
            animatedX.animateTo(
                startPoint.x + 100F,
                animationSpec = tween(duration),
            )
        }

        launch {
            animatedY.animateTo(
                startPoint.y + 100F,
                animationSpec = tween(duration),
            )
        }
    }

    Box(modifier) {
        MedievalText(
            text = text,
            fontSize = hugeText,
            color = color,
            modifier = Modifier.offset(
                animatedX.value.dp,
                animatedY.value.dp,
            )
        )
    }
}