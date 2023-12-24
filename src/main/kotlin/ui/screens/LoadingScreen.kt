package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import colorBackgroundDarker
import properties.textures.Textures
import transparencySecond
import ui.composable.MedievalBox
import ui.composable.shaders.StandardBackgroundBrush
import ui.composable.shaders.texture

@Composable
fun LoadingScreen(
    progress: Int,
    target: Int,
    modifier: Modifier = Modifier,
) {
    val progressState by rememberUpdatedState(progress)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(StandardBackgroundBrush()),
    ) {
        LoadingProgressbar(
            value = progressState,
            max = target,
            modifier = Modifier
                .fillMaxWidth(0.7F)
                .height(32.dp)
        )
    }
}

@Composable
private fun LoadingProgressbar(
    value: Int,
    min: Int = 0,
    max: Int,
    modifier: Modifier = Modifier,
) {
    MedievalBox(modifier = modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .texture(Textures["background/button.png"])
                .background(colorBackgroundDarker.copy(alpha = transparencySecond))
        )

        val size by remember(value, min, max) {
            derivedStateOf { (value.toFloat() - min.toFloat()) / (max.toFloat() - min.toFloat()) }
        }

        Box(
            Modifier
                .clipToBounds()
                .background(Color.Red)
                .texture(Textures["background/button.png"])
                .fillMaxWidth(size)
                .fillMaxHeight()
                .align(Alignment.TopStart)
        )
    }
}