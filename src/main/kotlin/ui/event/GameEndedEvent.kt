package ui.event

import Game.Event.GameEndEventable
import Game.Game
import Game.GameEndInfo
import GameData

class GameEndedEvent(
    val game: Game,
    val gameData: GameData,
) : GameEndEventable {
    override fun onEvent(info: GameEndInfo) {
        gameData.gameResult.value = info
    }
}