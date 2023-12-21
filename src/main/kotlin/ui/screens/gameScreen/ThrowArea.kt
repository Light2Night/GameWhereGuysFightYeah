package ui.screens.gameScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun ThrowArea(
    texture: ImageBitmap,
    startPoint: Offset,
    endPoint: Offset,
    duration: Int,
    modifier: Modifier = Modifier,
) {
    val animatedRotation = remember { Animatable(-50F) }
    val animatedX = remember { Animatable(startPoint.x) }
    val animatedY = remember { Animatable(startPoint.y) }

    LaunchedEffect("animationKey") {
        launch {
            animatedRotation.animateTo(
                150F,
                tween(
                    duration,
                    easing = EaseIn,
                ),
            )
        }

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
            val flyHeight = abs(startPoint.x - endPoint.x) / 3
            val endY1 = min(startPoint.y, endPoint.y) - max(20.0F, flyHeight)

            animatedY.animateTo(
                endY1,
                tween(
                    duration / 3 * 2,
                    easing = EaseIn,
                ),
            )

            animatedY.animateTo(
                endPoint.y,
                tween(
                    duration / 3,
                    easing = EaseIn,
                )
            )
        }
    }

    Canvas(modifier = modifier) {
        val rectImage = Rect(
            animatedX.value - 50,
            animatedY.value - 50,
            animatedX.value + 50,
            animatedY.value + 50,
        )

        rotate(animatedRotation.value, rectImage.center) {
            drawImage(
                texture,
                dstOffset = IntOffset(rectImage.topLeft.x.toInt(), rectImage.topLeft.y.toInt()),
                dstSize = IntSize(rectImage.size.width.toInt(), rectImage.size.height.toInt()),
            )
        }
    }
}