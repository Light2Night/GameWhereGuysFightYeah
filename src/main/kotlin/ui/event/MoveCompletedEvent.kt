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
    }
}