package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
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
    orientation: Orientation = Orientation.Vertical,
    modifier: Modifier = Modifier,
) {
    val texture = when (orientation) {
        Orientation.Vertical -> getImageBitmap("textures/background/divider.png") ?: emptyImageBitmap
        Orientation.Horizontal -> getImageBitmap("textures/background/divider_h.png") ?: emptyImageBitmap
    }
    val imageBrush = ShaderBrush(
        ImageShader(
            texture,
            TileMode.Repeated,
            TileMode.Repeated,
        )
    )

    when (orientation) {
        Orientation.Vertical -> Box(
            modifier = modifier
                .width(16.dp)
                .background(imageBrush)
        )
        Orientation.Horizontal -> Box(
            modifier = modifier
                .height(16.dp)
                .background(imageBrush)
        )
    }

}