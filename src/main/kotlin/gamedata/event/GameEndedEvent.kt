package gamedata.event

import Game.Event.Arguments.GameEndInfo
import Game.Event.HandlerGeneric
import gamedata.GameData

class GameEndedEvent(
    private val gameData: GameData,
) : HandlerGeneric<GameEndInfo>() {
    override fun handle(info: GameEndInfo) {
        gameData.gameResult = info
    }
}