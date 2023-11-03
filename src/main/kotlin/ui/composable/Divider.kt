package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import emptyImageBitmap
import getImageBitmap

@Composable
fun Divider(
    modifier: Modifier = Modifier,
) {
    val imageBrush = ShaderBrush(
        ImageShader(
            getImageBitmap("textures/background/divider.png") ?: emptyImageBitmap,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )

    Box(
        modifier = modifier
            .width(16.dp)
            .background(imageBrush)
    )
}