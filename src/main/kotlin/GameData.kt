import Game.Characters.GameUnit
import Game.Game
import Game.Teams.PlayerTypes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class GameData(
    game: Game,
) {
    val allies: SnapshotStateList<GameUnit> = mutableStateListOf()
    val enemies: SnapshotStateList<GameUnit> = mutableStateListOf()

    var currentUnit = mutableStateOf<GameUnit>(game.getUnitById(game.currentUnitIndex))
    var selectedUnit = mutableStateOf<GameUnit>(game.getUnitById(game.selectedUnitIndex))

    init {
        updateAllies(game.units.filter { it.team.playerType ==  PlayerTypes.Human })
        updateEnemies(game.units.filter { it.team.playerType ==  PlayerTypes.AI })
    }

    fun updateAllies(allies: List<GameUnit>) {
        this.allies.clear()
        this.allies.addAll(allies)
    }

    fun updateEnemies(enemies: List<GameUnit>) {
        this.enemies.clear()
        this.enemies.addAll(enemies)
    }

    fun updateCurrentUnit(unit: GameUnit) {
        this.currentUnit.value = unit
    }

    fun updateSelectedUnit(unit: GameUnit) {
        this.selectedUnit.value = unit
    }

    fun isCurrentUnitAlly() = allies.contains(currentUnit.value)
}