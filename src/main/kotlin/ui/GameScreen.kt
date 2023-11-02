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
import border
import colorBackground
import colorBackgroundLighter
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
            .background(colorBackground, RoundedCornerShape(smallCorners))
            .border(
                if (isSelected) border else smallBorder,
                if (isSelected) colorSelectedBorder else colorBorder,
                RoundedCornerShape(smallCorners)
            )
            .clip(RoundedCornerShape(smallCorners)),
    ) {
        if (side == Side.Left) {
            Image(
                bitmap = getImageBitmap("textures/unit_image_placeholder.png") ?: emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackgroundLighter, RoundedCornerShape(smallCorners))
                    .border(smallBorder, colorBorder, RoundedCornerShape(smallCorners))
                    .clip(RoundedCornerShape(smallCorners)),
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
            )

            Text(
                "HP: ${unit.hp}/${unit.maxHp}",
                fontSize = normalText,
                color = colorText
            )

            when (unit) {
                is Barbarian -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = colorText
                    )
                }
                is Magician -> {
                    Text(
                        "DMG: ${unit.damage - unit.damageDelta}-${unit.damage}",
                        fontSize = normalText,
                        color = colorText
                    )

                    val effect = unit.magicalEffect
                    Text(
                        "EFF: 15 (2 turns)",
                        fontSize = normalText,
                        color = colorText
                    )
                }
                is Healer -> {
                    Text(
                        "HEAL: ${unit.heal}",
                        fontSize = normalText,
                        color = colorText
                    )

                    val effect = unit.healingEffect
                    Text(
                        "EFF: 10 (3 turns)",
                        fontSize = normalText,
                        color = colorText
                    )
                }
            }
        }

        if (side == Side.Right) {
            Image(
                bitmap = getImageBitmap("textures/unit_image_placeholder.png") ?: emptyImageBitmap,
                contentDescription = unit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackgroundLighter, RoundedCornerShape(smallCorners))
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