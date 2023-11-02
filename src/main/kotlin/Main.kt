import Game.Game
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import properties.Properties
import ui.GameScreen
import ui.MainMenu
import ui.ResultsScreen
import ui.event.SelectEvent
import ui.Screen
import ui.event.CurrentUnitChangedEvent
import ui.event.GameEndedEvent
import ui.event.MoveCompletedEvent

fun main() = application {
    Properties.loadStyle()
    Properties.loadSettings()
    Properties.loadLanguage()
    Properties.loadUser()

    var screen by remember { mutableStateOf(Screen.Main) }
    var game by remember { mutableStateOf<Game?>(null) }
    var gameData by remember { mutableStateOf<GameData?>(null) }

    val windowState = rememberWindowState(
        width = 1400.dp,
        height = 700.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        when (screen) {
            Screen.Main -> MainMenu(
                onStart = { allies, enemies ->
                    game = Game()
                    allies.forEach { game?.addAlly(it) }
                    enemies.forEach { game?.addEnemy(it) }
                    game?.start()
                    gameData = GameData(game!!)
                    game?.events?.setSelectedIndexChangedEvent(SelectEvent(game!!, gameData!!))
                    game?.events?.setCurrentIndexChangedEvent(CurrentUnitChangedEvent(game!!, gameData!!))
                    game?.events?.setMoveCompletedEvent(MoveCompletedEvent(game!!, gameData!!))
                    game?.events?.setGameEndEvent(GameEndedEvent(game!!, gameData!!))
                    screen = Screen.Game
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
            Screen.Game -> GameScreen(
                game = game!!,
                gameData = gameData!!,
                onEnd = {
                    screen = Screen.Results
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
            Screen.Results -> ResultsScreen(
                result = gameData!!.gameResult.value!!,
                onBack = {
                    game = null
                    gameData = null
                    screen = Screen.Main
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
        }
    }
}
