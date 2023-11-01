package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class CurrentUnitChangedEvent(
    val game: Game,
    val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        gameData.currentUnit.value = game.getUnitById(game.currentUnitIndex) ?: return
    }
}