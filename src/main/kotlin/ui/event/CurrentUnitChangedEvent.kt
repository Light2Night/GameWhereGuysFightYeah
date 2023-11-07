package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class CurrentUnitChangedEvent(
    private val game: Game,
    private val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        game.currentUnitIndex?.let { gameData.currentUnit = game.getUnitById(it) ?: return }
    }
}