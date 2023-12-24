package ui.composable.shaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import properties.textures.Textures

@Composable
fun ButtonBrush(): Brush {
    return ShaderBrush(
        ImageShader(
            Textures["background/button.png"],
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )
}