package ui

import Game.Characters.Barbarian
import Game.Characters.GameUnit
import Game.Characters.Healer
import Game.Characters.Magician
import Game.Game
import GameData
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import bigText
import border
import colorBackground
import colorBackgroundDarker
import colorBorder
import colorSelectedBorder
import colorText
import emptyImageBitmap
import getImageBitmap
import imageHeight
import imageWidth
import normalText
import padding
import smallBorder
import smallCorners
import ui.composable.MedievalButton

@Composable
fun GameScreen(
    game: Game,
    gameData: GameData,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        UnitList(
            units = gameData.allies,
            selectedUnitID = gameData.selectedUnit.value.id,
            side = Side.Left,
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier.weight(1F),
        )

        Box(
            modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color.Black)
        )

        GameBoard(
            game = game,
            gameData = gameData,
            onAction = {
                game.next()
                gameData.gameResult.value?.let { onEnd() }
            },
            modifier = Modifier.weight(1F),
        )

        Box(
            modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color.Black)
        )

        UnitList(
            units = gameData.enemies,
            selectedUnitID = gameData.selectedUnit.value.id,
            side = Side.Right,
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
fun UnitList(
    selectedUnitID: Int,
    units: List<GameUnit>,
    side: Side,
    onSelect: (Int) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        units.forEach { unit ->
            UnitInfo(
                unit = unit,
                isSelected = unit.id == selectedUnitID,
                side = side,
                onSelect = {
                    onSelect(unit.id)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun UnitInfo(
    unit: GameUnit,
    isSelected: Boolean,
    side: Side = Side.Left,
    onSelect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = if (side == Side.Right) Arrangement.End else Arrangement.Start,
        modifier = modifier
            .clickable { onSelect() }
            .padding(padding)
            .background(colorBackgroundDarker, MedievalShape(smallCorners.value))
            .border(
                if (isSelected) border else smallBorder,
                if (isSelected) colorSelectedBorder else colorBorder,
                MedievalShape(smallCorners.value)
            )
            .clip(MedievalShape(smallCorners.value)),
    ) {
        if (side == Side.Left) {
            Image(
                bitmap = getImageBitmap(
                    when (unit) {
                        is Barbarian -> { "textures/characters/barbarian_placeholder.png" }
                        is Magician -> { "textures/characters/magician_placeholder.png" }
                        is Healer -> { "textures/characters/healer_placeholder.png" }
                        else -> ""
                    }
                ) ?: emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, MedievalShape(smallCorners.value))
                    .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
                    .clip(MedievalShape(smallCorners.value)),
            )
        }

        Column(
            horizontalAlignment = if (side == Side.Right) Alignment.End else Alignment.Start,
            modifier = Modifier
        ) {
            Text(
                "${unit.id} - ${unit.name}",
                color = colorText,
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            )

            Text(
                "HP: ${unit.hp}/${unit.maxHp}",
                fontSize = normalText,
                color = colorText,
                fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            )

            when (unit) {
                is Barbarian -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = colorText,
                        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                    )
                }
                is Magician -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = colorText,
                        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                    )

                    val effect = unit.magicalEffect
                    Text(
                        "EFF: 15 (2 turns)",
                        fontSize = normalText,
                        color = colorText,
                        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                    )
                }
                is Healer -> {
                    Text(
                        "HEAL: ${unit.heal}",
                        fontSize = normalText,
                        color = colorText,
                        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                    )

                    val effect = unit.healingEffect
                    Text(
                        "EFF: 10 (3 turns)",
                        fontSize = normalText,
                        color = colorText,
                        fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
                    )
                }
            }
        }

        if (side == Side.Right) {
            Image(
                bitmap = getImageBitmap(
                    when (unit) {
                        is Barbarian -> { "textures/characters/barbarian_placeholder.png" }
                        is Magician -> { "textures/characters/magician_placeholder.png" }
                        is Healer -> { "textures/characters/healer_placeholder.png" }
                        else -> ""
                    }
                ) ?: emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, MedievalShape(smallCorners.value))
                    .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
                    .clip(MedievalShape(smallCorners.value)),
            )
        }
    }
}

@Composable
fun GameBoard(
    game: Game,
    gameData: GameData,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        UnitInfo(
            unit = gameData.currentUnit.value,
            isSelected = false,
            modifier = Modifier.fillMaxWidth(),
        )
        Actions(
            actions = ActionFabric(game, gameData).createActions(),
            selectedUnitID = gameData.selectedUnit.value.id,
            onAction = onAction,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Actions(
    actions: List<Action>,
    selectedUnitID: Int,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow {
        actions.forEach { action ->
            MedievalButton(
                text = action.name,
                onClick = {
                    action.action(selectedUnitID)
                    onAction()
                },
                modifier = modifier,
            )
        }
    }
}