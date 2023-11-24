package gamedata.event

import Game.Event.Handler
import Game.Game
import gamedata.GameData

class MoveCompletedEvent(
    private val game: Game,
    private val gameData: GameData,
) : Handler() {
    override fun handle() {
        gameData.updateUnits(game.units)

        val currentActionIndex = gameData.currentActionIndex.value ?: 0
        for (index in currentActionIndex..game.cycleActions.lastIndex) {
            gameData.addActionInfo(game.cycleActions[index])
        }
    }
}