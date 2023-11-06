import Game.Characters.GameUnit
import Game.Game
import Game.Event.Arguments.GameEndInfo
import Game.PlayerTypes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class GameData(
    game: Game,
) {
    private val units: SnapshotStateList<GameUnit> = mutableStateListOf()
    val allies get() = units.filter { it.team.playerType ==  PlayerTypes.Human }
    val enemies get() = units.filter { it.team.playerType ==  PlayerTypes.AI }

    val currentUnit = mutableStateOf<GameUnit?>(null)
    val selectedUnit = mutableStateOf<GameUnit?>(null)

    val gameResult = mutableStateOf<GameEndInfo?>(null)

    init {
        updateUnits(game.units)
    }

    fun updateUnits(allies: List<GameUnit>) {
        this.units.clear()
        this.units.addAll(allies)
    }

    fun reset() {
        units.clear()
        currentUnit.value = null
        selectedUnit.value = null
        gameResult.value = null
    }
}