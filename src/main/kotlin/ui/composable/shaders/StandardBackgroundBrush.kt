package ui.composable.shaders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import emptyImageBitmap
import getImageBitmap

@Composable
fun StandardBackgroundBrush(): Brush {
    val texture by remember { mutableStateOf(getImageBitmap("textures/background/2.png")) }

    return ShaderBrush(
        ImageShader(
            texture ?: emptyImageBitmap,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )
}