package ui

import MainMenu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    var screen by remember { mutableStateOf(Screen.Main) }

    val windowState = rememberWindowState(
        width = 1400.dp,
        height = 700.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        when(screen) {
            Screen.Main -> MainMenu(
                onStart = {
                    screen = Screen.Game
                }
            )
            Screen.Game -> GameScreen(
                onEnd = {
                    screen = Screen.Main
                }
            )
        }
    }
}
