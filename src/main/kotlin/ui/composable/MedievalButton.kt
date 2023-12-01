package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import bigText
import biggerPadding
import colorBackgroundDarker
import colorBorder
import colorTextLighter
import iconSize
import smallBorder
import smallCorners
import transparencySecond
import ui.composable.shaders.ButtonBrush
import ui.composable.shaders.MedievalShape

@Composable
fun MedievalButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    MedievalButton(onClick, enabled, modifier) {
        Text(
            text,
            color = colorTextLighter,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            modifier = Modifier.padding(biggerPadding),
        )
    }
}

@Composable
fun MedievalButton(
    onClick: () -> Unit,
    icon: ImageBitmap,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    MedievalButton(onClick, enabled, modifier) {
        Image(
            icon,
            contentDescription = "",
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun MedievalButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageBitmap,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    MedievalButton(onClick, enabled, modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            MedievalBox(
                modifier = Modifier.height(iconSize)
            ) {
                Image(
                    icon,
                    contentDescription = text,
                    modifier = Modifier.fillMaxHeight()
                )
            }

            Text(
                text,
                color = colorTextLighter,
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                modifier = Modifier.padding(biggerPadding),
            )
        }
    }
}

@Composable
private fun MedievalButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(onClick = onClick, enabled = enabled)
            .background(ButtonBrush(), MedievalShape(smallCorners))
            .background(colorBackgroundDarker.copy(alpha = if (enabled) 0F else transparencySecond))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners))
            .clip(MedievalShape(smallCorners)),
    ) {
        content()
    }
}