import Game.Game
import Game.Units.Characters.UnitTypes
import Game.Units.Factories.ViewModels.BarbarianViewModel
import Game.Units.Factories.ViewModels.HealerViewModel
import Game.Units.Factories.ViewModels.MageViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import gamedata.GameData
import gamedata.event.CurrentUnitChangedEvent
import gamedata.event.GameEndedEvent
import gamedata.event.MoveCompletedEvent
import gamedata.event.SelectEvent
import properties.Properties
import properties.textures.Textures
import properties.user.chest.Chest
import properties.user.recruit.Recruit
import properties.user.recruit.RecruitFactory
import ui.screens.GameScreen
import ui.screens.MainMenu
import ui.screens.ResultsScreen
import ui.Screen
import ui.screens.ChestScreen

fun main() = application {
    var firstTime by remember { mutableStateOf(true) }
    if (firstTime) {
        Textures.load()

        Properties.loadStyle()
        Properties.loadSettings()
        Properties.loadLanguage(settings.language)
        Properties.loadUser()

        while (user.recruits.guildList.size < 3) {
            user.recruits.createNewRecruitToGuild()
        }

        while (user.quests.list.size < 6) {
            user.quests.createQuest()
        }

        firstTime = false
    }

    var screen by remember { mutableStateOf(Screen.Main) }
    val game by remember { mutableStateOf(Game()) }
    val gameData by remember {
        val newGameData = GameData(game)

        game.events.SelectedIndexChangedEvent.addHandler(SelectEvent(game, newGameData))
        game.events.CurrentIndexChangedEvent.addHandler(CurrentUnitChangedEvent(game, newGameData))
        game.events.MoveCompletedEvent.addHandler(MoveCompletedEvent(game, newGameData))
        game.events.GameEndEvent.addHandler(GameEndedEvent(newGameData))

        mutableStateOf(newGameData)
    }

    var chest by remember { mutableStateOf<Chest?>(null) }

    val windowState = rememberWindowState(
        width = 1400.dp,
        height = 700.dp,
        position = WindowPosition.Aligned(Alignment.Center),
        placement = WindowPlacement.Maximized,
    )

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        when (screen) {
            Screen.Main -> MainMenu(
                onStart = { enemies, location ->
                    game.reset()
                    gameData.location = location

                    user.recruits.selectedList.forEach {
                        val newUnit = when (it.rawData.type) {
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

                        val newUnit = when (enemyRecruit.rawData.type) {
                            UnitTypes.BARBARIAN -> game.enemyFactory.createBarbarian(enemyRecruit.toViewModel() as BarbarianViewModel)
                            UnitTypes.MAGICIAN -> game.enemyFactory.createMagician(enemyRecruit.toViewModel() as MageViewModel)
                            UnitTypes.HEALER -> game.enemyFactory.createHealer(enemyRecruit.toViewModel() as HealerViewModel)
                        }

                        game.addUnit(newUnit)
                        gameData.unitIDs[newUnit.id] = enemyRecruit.id
                    }
                    gameData.enemiesRecruits = createdEnemies
                    gameData.updateUnits(game.units)

                    game.start()

                    screen = Screen.Game
                },
                onChestOpen = {
                    chest = it
                    screen = Screen.Chest
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground),
            )

            Screen.Game -> GameScreen(
                game = game,
                gameData = gameData,
                onEnd = { screen = Screen.Results },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground),
            )

            Screen.Results -> ResultsScreen(
                result = gameData.gameResult!!,
                location = gameData.location!!,
                onBack = {
                    user.quests.updateAllQuests(gameData)
                    user.quests.checkAllQuests()
                    gameData.reset()
                    screen = Screen.Main
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground),
            )

            Screen.Chest -> ChestScreen(
                chest = chest!!,
                onClose = {
                    chest = null
                    screen = Screen.Main
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground),
            )
        }
    }
}
