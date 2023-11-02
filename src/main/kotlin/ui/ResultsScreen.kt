package ui

import Game.GameEndInfo
import Game.PlayerTypes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import colorText
import colorTextError
import hugeText
import ui.composable.MedievalButton

@Composable
fun ResultsScreen(
    result: GameEndInfo,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            when (result.teamWinner.playerType) {
                PlayerTypes.AI -> "Противник переміг!"
                PlayerTypes.Human -> "Ви розгромили противника!"
                null -> "<ERROR>"
            },
            color = colorTextError,
            fontSize = hugeText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )

        MedievalButton(
            text = "Назад",
            onClick = onBack,
        )
    }
}