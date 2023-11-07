package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class MoveCompletedEvent(
    private val game: Game,
    private val gameData: GameData,
) : Eventable {
    override fun onEvent() {
        gameData.updateUnits(game.units)

        val currentActionIndex = gameData.currentActionIndex.value ?: 0
        for (index in currentActionIndex..game.cycleActions.lastIndex) {
            gameData.addActionInfo(game.cycleActions[index])
        }
    }
}