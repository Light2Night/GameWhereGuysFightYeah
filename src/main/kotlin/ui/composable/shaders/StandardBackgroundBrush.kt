package ui.composable.shaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import emptyImageBitmap
import getImageBitmap

@Composable
fun StandardBackgroundBrush(): Brush {
    return ShaderBrush(
        ImageShader(
            getImageBitmap("textures/background/2.png") ?: emptyImageBitmap,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )
}