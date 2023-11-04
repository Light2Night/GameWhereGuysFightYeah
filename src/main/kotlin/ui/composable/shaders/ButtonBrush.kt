package ui.composable.shaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import emptyImageBitmap
import getImageBitmap

@Composable
fun ButtonBrush(): Brush {
    return ShaderBrush(
        ImageShader(
            getImageBitmap("textures/background/button.png") ?: emptyImageBitmap,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )
}