import Game.Game
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
import ui.GameScreen
import ui.MainMenu
import ui.event.SelectEvent
import ui.Screen
import ui.event.CurrentUnitChangedEvent
import ui.event.MoveCompletedEvent

fun main() = application {
    var screen by remember { mutableStateOf(Screen.Main) }
    var game by remember { mutableStateOf<Game?>(null) }
    var gameData by remember { mutableStateOf<GameData?>(null) }

    val windowState = rememberWindowState(
        width = 1400.dp,
        height = 700.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        when(screen) {
            Screen.Main -> MainMenu(
                onStart = {
                    game = Game()
                    game?.start()
                    gameData = GameData(game!!)
                    game?.setSelectedIndexChanged(
                        SelectEvent(game!!, gameData!!)
                    )
                    game?.setCurrentIndexChanged(
                        CurrentUnitChangedEvent(game!!, gameData!!)
                    )
                    game?.setMoveCompleted(
                        MoveCompletedEvent(game!!, gameData!!)
                    )
                    screen = Screen.Game
                }
            )
            Screen.Game -> GameScreen(
                game = game!!,
                gameData = gameData!!,
                onEnd = {
                    game = null
                    gameData = null
                    screen = Screen.Main
                }
            )
        }
    }
}
