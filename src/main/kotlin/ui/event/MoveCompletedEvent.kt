package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class MoveCompletedEvent(
    val game: Game,
    val gameData: GameData,
) : Eventable {
    override fun onEvent() {
        gameData.updateUnits(game.units)
    }
}