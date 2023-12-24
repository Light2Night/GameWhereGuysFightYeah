package ui.composable.shaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import properties.textures.Textures

@Composable
fun StandardBackgroundBrush(): Brush {
    return ShaderBrush(
        ImageShader(
            Textures["background/2.png"],
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )
}