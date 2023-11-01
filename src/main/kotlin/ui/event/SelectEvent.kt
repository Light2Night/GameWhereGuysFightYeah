package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class SelectEvent(
    val game: Game,
    val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        gameData.selectedUnit.value = game.getUnitById(game.selectedUnitIndex) ?: return
    }
}