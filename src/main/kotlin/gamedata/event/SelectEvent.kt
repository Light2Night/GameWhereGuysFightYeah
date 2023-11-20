package gamedata.event

import Game.Event.Eventable
import Game.Game
import gamedata.GameData

class SelectEvent(
    private val game: Game,
    private val gameData: GameData,
) : Eventable {

    override fun onEvent() {
        game.selectedUnitIndex?.let { gameData.selectedUnit = game.getUnitById(it) ?: return }
    }
}