package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class CurrentUnitChangedEvent(
    private val game: Game,
    private val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        gameData.currentUnit.value = game.getUnitById(game.currentUnitIndex) ?: return
    }
}