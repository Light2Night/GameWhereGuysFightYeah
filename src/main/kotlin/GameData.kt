import Game.Characters.GameUnit
import Game.Game
import Game.Teams.PlayerTypes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class GameData(
    game: Game,
) {
    val units: SnapshotStateList<GameUnit> = mutableStateListOf()
    val allies get() = units.filter { it.team.playerType ==  PlayerTypes.Human }
    val enemies get() = units.filter { it.team.playerType ==  PlayerTypes.AI }

    var currentUnit = mutableStateOf<GameUnit>(game.getUnitById(game.currentUnitIndex))
    var selectedUnit = mutableStateOf<GameUnit>(game.getUnitById(game.selectedUnitIndex))

    init {
        updateUnits(game.units)
    }

    fun updateUnits(allies: List<GameUnit>) {
        this.units.clear()
        this.units.addAll(allies)
    }
}