package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import colorBackgroundDarker
import properties.textures.Textures
import transparencySecond
import ui.composable.shaders.repeatableTexture

@Composable
fun MedievalProgressbar(
    value: Int,
    min: Int,
    max: Int,
    orientation: Orientation = Orientation.Horizontal,
    modifier: Modifier = Modifier,
) {
    val textureBitmap by remember {
        mutableStateOf(
            when (orientation) {
                Orientation.Vertical -> Textures["background/divider.png"]
                Orientation.Horizontal -> Textures["background/divider_h.png"]
            }
        )
    }

    MedievalBox(modifier = modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .repeatableTexture(textureBitmap, orientation)
                .background(colorBackgroundDarker.copy(alpha = transparencySecond))
        )

        val size by remember(value, min, max) {
            derivedStateOf {
                (value.toFloat() - min.toFloat()) / (max.toFloat() - min.toFloat())
            }
        }

        Box(
            Modifier
                .clipToBounds()
                .repeatableTexture(textureBitmap, orientation)
                .fillMaxHeight(if (orientation == Orientation.Vertical) size else 1F)
                .fillMaxWidth(if (orientation == Orientation.Horizontal) size else 1F)
                .align(Alignment.TopStart)
        )
    }
}