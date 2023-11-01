package ui

import Game.Characters.GameUnit
import Game.Game
import GameData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier,
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
            modifier = Modifier,
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
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier,
        )
    }
}

@Composable
fun UnitList(
    selectedUnitID: Int,
    units: List<GameUnit>,
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
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onSelect() },
    ) {
        Text(unit.toString(), color = if (isSelected) Color.Blue else Color.Black)
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
        Text(text = gameData.currentUnit.value.toString())
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