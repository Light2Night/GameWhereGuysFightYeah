package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        /*UnitList(
            units = gameData.allies,
            selectedUnitID = gameData.selectedUnit.value.id,
            side = Side.Left,
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier.weight(1F),
        )*/

        Box(
            modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color.Black)
        )

        Button(
            onClick = onStart,
            modifier = Modifier.weight(1F),
        ) {
            Text("Start")
        }

        Box(
            modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color.Black)
        )

        /*UnitList(
            units = gameData.enemies,
            selectedUnitID = gameData.selectedUnit.value.id,
            side = Side.Right,
            onSelect = { game.selectedUnitIndex = it },
            modifier = Modifier.weight(1F),
        )*/
    }
}