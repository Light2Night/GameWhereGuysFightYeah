package ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainMenu(
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onStart,
    ) {
        Text("Start")
    }
}