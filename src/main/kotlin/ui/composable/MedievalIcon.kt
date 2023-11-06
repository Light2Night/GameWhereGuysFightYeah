package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import colorBackgroundSecond
import colorBorder
import smallBorder
import smallCorners
import ui.composable.shaders.MedievalShape

@Composable
fun MedievalIcon(
    icon: ImageBitmap,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(colorBackgroundSecond, MedievalShape(smallCorners))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners))
            .clip(MedievalShape(smallCorners)),
    ) {
        Image(
            icon,
            contentDescription = "icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
    }
}