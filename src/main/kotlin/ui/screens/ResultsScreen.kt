package ui.screens

import Game.Events.Arguments.GameEndInfo
import Game.PlayerTypes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import bigText
import colorTextError
import emptyImageBitmap
import getImageBitmap
import hugeText
import lang
import padding
import properties.resources.Reward
import properties.user.worldMap.Location
import smallIconSize
import ui.composable.MedievalButton
import ui.composable.MedievalText

@Composable
fun ResultsScreen(
    result: GameEndInfo,
    location: Location,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val reward by remember {
        mutableStateOf(if (result.teamWinner.playerType == PlayerTypes.Human) location.getReward() else Reward(0, 0, 0))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            when (result.teamWinner.playerType) {
                PlayerTypes.AI -> lang.ai_won
                PlayerTypes.Human -> lang.user_won
                null -> "<ERROR>"
            },
            color = colorTextError,
            fontSize = hugeText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )

        Reward(
            reward = reward,
        )

        MedievalButton(
            text = lang.back_button.replaceFirstChar { it.uppercaseChar() },
            onClick = {
                if (result.teamWinner.playerType == PlayerTypes.Human) {
                    reward.giveToUser()
                }

                onBack()
            },
        )
    }
}

@Composable
private fun Reward(
    reward: Reward,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        if (reward.coins > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                Image(
                    bitmap = getImageBitmap("textures/assets/coin.png") ?: emptyImageBitmap,
                    contentDescription = "coin icon",
                    modifier = Modifier.size(smallIconSize),
                )

                MedievalText(
                    reward.coins.toString(),
                    fontSize = bigText,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (reward.crystals > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                Image(
                    bitmap = getImageBitmap("textures/assets/crystal.png") ?: emptyImageBitmap,
                    contentDescription = "crystal icon",
                    modifier = Modifier.size(smallIconSize),
                )

                MedievalText(
                    reward.crystals.toString(),
                    fontSize = bigText,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (reward.exp > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                Image(
                    bitmap = getImageBitmap("textures/assets/crystal.png") ?: emptyImageBitmap,
                    contentDescription = "experience icon",
                    modifier = Modifier.size(smallIconSize),
                )

                MedievalText(
                    reward.exp.toString(),
                    fontSize = bigText,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}