package ui.event

import Game.Event.Eventable
import Game.Game
import Game.Teams.PlayerTypes
import GameData

class MoveCompletedEvent(
    val game: Game,
    val gameData: GameData,
) : Eventable {
    override fun onEvent() {
        gameData.updateAllies(game.units.filter { it.team.playerType ==  PlayerTypes.Human })
        gameData.updateEnemies(game.units.filter { it.team.playerType ==  PlayerTypes.AI })
    }
}