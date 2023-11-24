package gamedata.event

import Game.Events.Arguments.GameEndInfo
import Game.Events.HandlerGeneric
import gamedata.GameData

class GameEndedEvent(
    private val gameData: GameData,
) : HandlerGeneric<GameEndInfo>() {
    override fun handle(info: GameEndInfo) {
        gameData.gameResult = info
    }
}