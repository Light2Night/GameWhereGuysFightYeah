package gamedata.event

import Game.Event.Handler
import Game.Game
import gamedata.GameData

class SelectEvent(
    private val game: Game,
    private val gameData: GameData,
) : Handler() {

    override fun handle() {
        game.selectedUnitIndex?.let { gameData.selectedUnit = game.getUnitById(it) ?: return }
    }
}