import Game.Game
import Game.Units.Characters.UnitTypes
import Game.Units.Factories.ViewModels.BarbarianViewModel
import Game.Units.Factories.ViewModels.HealerViewModel
import Game.Units.Factories.ViewModels.MageViewModel
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
import properties.user.recruit.Recruit
import properties.user.recruit.RecruitFactory
import ui.GameScreen
import ui.MainMenu
import ui.ResultsScreen
import ui.Screen
import ui.event.*

fun main() = application {
    var firstTime by remember { mutableStateOf(true) }
    if (firstTime) {
        Properties.loadStyle()
        Properties.loadSettings()
        Properties.loadLanguage(settings.language)
        Properties.loadUser()

        repeat(3) {
            user.recruits.createNewRecruitToGuild()
        }

        firstTime = false
    }

    var screen by remember { mutableStateOf(Screen.Main) }
    val game by remember { mutableStateOf(Game()) }
    val gameData by remember { mutableStateOf(GameData(game)) }

    val windowState = rememberWindowState(
        width = 1400.dp,
        height = 700.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        when (screen) {
            Screen.Main -> MainMenu(
                onStart = { enemies ->
                    game.reset()
                    //allies.forEach { game.addAlly(it) }
                    //enemies.forEach { game.addEnemy(it) }

                    user.recruits.selectedList.forEach {
                        val newUnit = when (it.data.type) {
                            UnitTypes.BARBARIAN -> game.alliesFactory.createBarbarian(it.toViewModel() as BarbarianViewModel)
                            UnitTypes.MAGICIAN -> game.alliesFactory.createMagician(it.toViewModel() as MageViewModel)
                            UnitTypes.HEALER -> game.alliesFactory.createHealer(it.toViewModel() as HealerViewModel)
                        }

                        game.addUnit(newUnit)
                        gameData.unitIDs[newUnit.id] = it.id
                    }

                    val createdEnemies = mutableListOf<Recruit>()
                    enemies.forEach { type ->
                        val enemyRecruit = RecruitFactory().createRandomUnique(
                            user.recruits.selectedList + createdEnemies,
                            type,
                        )

                        createdEnemies.add(enemyRecruit)

                        val newUnit = when (enemyRecruit.data.type) {
                            UnitTypes.BARBARIAN -> game.enemyFactory.createBarbarian(enemyRecruit.toViewModel() as BarbarianViewModel)
                            UnitTypes.MAGICIAN -> game.enemyFactory.createMagician(enemyRecruit.toViewModel() as MageViewModel)
                            UnitTypes.HEALER -> game.enemyFactory.createHealer(enemyRecruit.toViewModel() as HealerViewModel)
                        }

                        game.addUnit(newUnit)
                        gameData.unitIDs[newUnit.id] = enemyRecruit.id
                    }
                    gameData.enemiesRecruits = createdEnemies
                    gameData.updateUnits(game.units)

                    game.events.setSelectedIndexChangedEvent(SelectEvent(game, gameData))
                    game.events.setCurrentIndexChangedEvent(CurrentUnitChangedEvent(game, gameData))
                    game.events.setMoveCompletedEvent(MoveCompletedEvent(game, gameData))
                    game.events.setGameEndEvent(GameEndedEvent(gameData))

                    game.start()

                    screen = Screen.Game
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
            Screen.Game -> GameScreen(
                game = game,
                gameData = gameData,
                onEnd = {
                    screen = Screen.Results
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
            Screen.Results -> ResultsScreen(
                result = gameData.gameResult.value!!,
                onBack = {
                    user.requests.updateAllRequests(gameData.gameResult.value!!)
                    user.requests.checkAllRequests()
                    gameData.reset()
                    screen = Screen.Main
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            )
        }
    }
}
