package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import properties.textures.Textures

@Composable
fun Divider(
    orientation: Orientation = Orientation.Vertical,
    modifier: Modifier = Modifier,
) {
    val texture by remember {
        mutableStateOf(
            when (orientation) {
                Orientation.Vertical -> Textures["background/divider.png"]
                Orientation.Horizontal -> Textures["background/divider_h.png"]
            }
        )
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