package gamedata.event

import Game.Event.Handler
import Game.Game
import gamedata.GameData

class CurrentUnitChangedEvent(
    private val game: Game,
    private val gameData: GameData,
) : Handler() {

    override fun handle() {
        game.currentUnitIndex?.let { gameData.currentUnit = game.getUnitById(it) ?: return }
    }
}