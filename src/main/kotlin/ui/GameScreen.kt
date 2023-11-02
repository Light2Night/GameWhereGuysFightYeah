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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bigText
import colorBackground
import colorBorder
import corners
import emptyImageBitmap
import imageHeight
import imageWidth
import normalText
import padding
import smallBorder
import smallCorners

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
            onAction = { game.next() },
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
                }
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
        modifier = modifier
            .clickable { onSelect() },
    ) {
        if (side == Side.Left) {
            Image(
                bitmap = emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, RoundedCornerShape(smallCorners))
                    .border(smallBorder, colorBorder, RoundedCornerShape(smallCorners))
                    .clip(RoundedCornerShape(smallCorners)),
            )
        }

        Column(
            horizontalAlignment = if (side == Side.Left) Alignment.End else Alignment.Start,
            modifier = Modifier
        ) {
            Text(
                "${unit.id} - ${unit.name}",
                color = if (isSelected) Color.Blue else Color.Black,
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )

            Text(
                "HP: ${unit.hp}/${/*unit.maxHp*/100}",
                fontSize = normalText,
                color = if (isSelected) Color.Blue else Color.Black
            )

            when (unit) {
                is Barbarian -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = if (isSelected) Color.Blue else Color.Black
                    )
                }
                is Magician -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = if (isSelected) Color.Blue else Color.Black
                    )

                    val effect = unit.magicalEffect
                    Text(
                        "EFF: 15 (2 turns)",
                        fontSize = normalText,
                        color = if (isSelected) Color.Blue else Color.Black
                    )
                }
                is Healer -> {
                    Text(
                        "HEAL: ${unit.heal}",
                        fontSize = normalText,
                        color = if (isSelected) Color.Blue else Color.Black
                    )

                    val effect = unit.healingEffect
                    Text(
                        "EFF: 10 (3 turns)",
                        fontSize = normalText,
                        color = if (isSelected) Color.Blue else Color.Black
                    )
                }
            }
        }

        if (side == Side.Right) {
            Image(
                bitmap = emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, RoundedCornerShape(smallCorners))
                    .border(smallBorder, colorBorder, RoundedCornerShape(smallCorners))
                    .clip(RoundedCornerShape(smallCorners)),
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
            unit = gameData.selectedUnit.value,
            isSelected = false,
            onSelect = {}
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
            Button(
                onClick = {
                    action.action(selectedUnitID)
                    onAction()
                },
                modifier = modifier,
            ) {
                Text(action.name)
            }
        }
    }
}