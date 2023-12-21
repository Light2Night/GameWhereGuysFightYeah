package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

@Composable
fun SlashArea(
    texture: ImageBitmap,
    startPoint: Offset,
    endPoint: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val animatedRotation = remember { Animatable(0F) }
    val animatedAlpha = remember { Animatable(1.0F) }

    LaunchedEffect("animationKey") {
        launch {
            animatedRotation.animateTo(
                -50F,
                tween(
                    duration / 3 * 2,
                    easing = EaseIn,
                ),
            )

            animatedRotation.animateTo(
                50F,
                tween(
                    duration / 3,
                    easing = EaseIn,
                ),
            )
        }

        launch {
            animatedAlpha.animateTo(
                0.0F,
                tween(
                    duration,
                    easing = EaseIn,
                ),
            )
        }
    }

    Canvas(modifier = modifier) {
        val imageSize = IntSize(100, 100)

        val centerForStartPoint = Offset(startPoint.x - imageSize.width / 2, startPoint.y - imageSize.height / 2)
        rotate(animatedRotation.value, startPoint) {
            drawImage(
                texture,
                dstOffset = IntOffset(centerForStartPoint.x.toInt(), centerForStartPoint.y.toInt()),
                dstSize = imageSize,
                alpha = animatedAlpha.value,
            )
        }

        val centerForEndPoint = Offset(endPoint.x - imageSize.width / 2, endPoint.y - imageSize.height / 2)
        rotate(animatedRotation.value, endPoint) {
            drawImage(
                texture,
                dstOffset = IntOffset(centerForEndPoint.x.toInt(), centerForEndPoint.y.toInt()),
                dstSize = imageSize,
                alpha = 1.0F - animatedAlpha.value,
            )
        }
    }
}