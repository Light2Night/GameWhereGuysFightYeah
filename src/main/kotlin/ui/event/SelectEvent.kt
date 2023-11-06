package ui.event

import Game.Event.Eventable
import Game.Game
import GameData

class SelectEvent(
    private val game: Game,
    private val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        game.selectedUnitIndex?.let { gameData.selectedUnit.value = game.getUnitById(it) ?: return }
    }
}