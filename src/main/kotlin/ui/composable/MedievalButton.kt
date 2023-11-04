package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import bigText
import biggerPadding
import colorBorder
import colorTextSecond
import smallBorder
import smallCorners
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
            color = colorTextSecond,
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
        Row {
            Image(
                icon,
                contentDescription = text,
                modifier = Modifier.fillMaxHeight()
            )
            Text(
                text,
                color = colorTextSecond,
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
            .background(ButtonBrush(), MedievalShape(smallCorners.value))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
            .clip(MedievalShape(smallCorners.value)),
    ) {
        content()
    }
}