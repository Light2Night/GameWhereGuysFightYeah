package ui.composable

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.TextUnit
import colorText
import normalText

@Composable
fun MedievalText(
    text: String,
    color: Color = colorText,
    fontSize: TextUnit = normalText,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        style = style,
        modifier = modifier,
    )
}