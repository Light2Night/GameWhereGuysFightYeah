package gamedata.event

import Game.Event.GameEndEventable
import Game.Event.Arguments.GameEndInfo
import gamedata.GameData

class GameEndedEvent(
    private val gameData: GameData,
) : GameEndEventable {
    override fun onEvent(info: GameEndInfo) {
        gameData.gameResult = info
    }
}