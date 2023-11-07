import Game.Characters.GameUnit
import Game.Event.Arguments.Actions.ActionInfo
import Game.Game
import Game.Event.Arguments.GameEndInfo
import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GameData(
    game: Game,
) {
    private val units = mutableStateListOf<GameUnit>()
    val allies get() = units.filter { it.team.playerType ==  PlayerTypes.Human }
    val enemies get() = units.filter { it.team.playerType ==  PlayerTypes.AI }

    var currentUnit by mutableStateOf<GameUnit?>(null)
    var selectedUnit by mutableStateOf<GameUnit?>(null)

    private val actionInfoList = mutableStateListOf<ActionInfo>()
    val currentActionIndex = mutableStateOf<Int?>(null)
    val currentAction get() = currentActionIndex.value?.let { actionInfoList[it] }

    val gameResult = mutableStateOf<GameEndInfo?>(null)

    init {
        updateUnits(game.units)
    }

    fun updateUnits(allies: List<GameUnit>) {
        this.units.clear()
        this.units.addAll(allies)
    }

    fun addActionInfo(info: ActionInfo) {
        actionInfoList.add(info)
    }

    fun nextActionInfo(): ActionInfo? {
        currentActionIndex.value?.let { index ->
            if (index >= actionInfoList.lastIndex) {
                currentActionIndex.value = null
                return null
            }
            currentActionIndex.value = index + 1

        } ?: run {
            if (actionInfoList.isEmpty()) {
                currentActionIndex.value = null
                return null
            }
            currentActionIndex.value = 0
        }

        return currentAction
    }

    fun resetActionInfo() {
        actionInfoList.clear()
    }

    fun reset() {
        units.clear()
        actionInfoList.clear()
        currentUnit = null
        selectedUnit = null
        gameResult.value = null
    }
}