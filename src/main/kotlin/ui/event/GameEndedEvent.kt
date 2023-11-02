package ui.event

import Game.Event.GameEndEventable
import Game.GameEndInfo
import GameData

class GameEndedEvent(
    private val gameData: GameData,
) : GameEndEventable {
    override fun onEvent(info: GameEndInfo) {
        gameData.gameResult.value = info
    }
}