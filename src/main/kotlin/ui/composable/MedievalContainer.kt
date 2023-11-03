package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import colorBackgroundSecond
import colorBorder
import smallBorder
import smallCorners

@Composable
fun MedievalContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(colorBackgroundSecond, MedievalShape(smallCorners.value))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
            .clip(MedievalShape(smallCorners.value)),
    ) {
        content()
    }
}