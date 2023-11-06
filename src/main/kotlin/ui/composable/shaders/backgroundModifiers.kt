package ui.composable.shaders

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ui.composable.Orientation
import kotlin.math.ceil

@Composable
fun Modifier.repeatableTexture(
    texture: ImageBitmap,
    orientation: Orientation = Orientation.Horizontal,
): Modifier = this
    .drawBehind {
        val imageRatio = texture.width.toFloat() / texture.height
        val imageHeight = if (orientation == Orientation.Horizontal) size.height else texture.height * imageRatio
        val imageWidth = if (orientation == Orientation.Vertical) size.width else texture.width / imageRatio
        val amount = if (orientation == Orientation.Horizontal)
            ceil(size.width / imageWidth).toInt()
        else
            ceil(size.height / imageHeight).toInt()

        repeat(amount) {
            drawImage(
                image = texture,
                dstOffset = IntOffset(
                    if (orientation == Orientation.Horizontal) (imageWidth * it).toInt() else 0,
                    if (orientation == Orientation.Vertical) (imageHeight * it).toInt() else 0
                ),
                dstSize = IntSize(imageWidth.toInt(), imageHeight.toInt()),
            )
        }
    }

@Composable
fun Modifier.texture(
    texture: ImageBitmap,
    shape: Shape = RectangleShape,
): Modifier {
    val brush = ShaderBrush(
        ImageShader(
            texture,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )

    return background(brush, shape)
}

@Composable
fun Modifier.background(
    background: ImageBitmap,
): Modifier = drawBehind {
    drawImage(
        image = background,
        dstOffset = IntOffset.Zero,
        dstSize = IntSize(size.width.toInt(), size.height.toInt()),
    )
}