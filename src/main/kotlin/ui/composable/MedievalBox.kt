package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import colorBackgroundSecond
import colorBorder
import smallBorder
import smallCorners
import ui.composable.shaders.MedievalShape

@Composable
fun MedievalBox(
    background: Brush = SolidColor(colorBackgroundSecond),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(background, MedievalShape(smallCorners))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners))
            .clip(MedievalShape(smallCorners)),
        content = content,
    )
}