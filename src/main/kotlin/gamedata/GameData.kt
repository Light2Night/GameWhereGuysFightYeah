package gamedata

import Game.Units.Characters.GameUnit
import Game.Events.Arguments.Actions.ActionInfo
import Game.Game
import Game.Events.Arguments.GameEndInfo
import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import properties.user.recruit.Recruit
import properties.user.worldMap.Location
import user

class GameData(
    val game: Game,
) {
    private val units = mutableStateListOf<GameUnit>()
    val allies get() = units.filter { it.team.playerType ==  PlayerTypes.Human }
    val enemies get() = units.filter { it.team.playerType ==  PlayerTypes.AI }

    var currentUnit by mutableStateOf<GameUnit?>(null)
    var selectedUnit by mutableStateOf<GameUnit?>(null)

    val unitIDs = mutableMapOf<Int, Int>()
    var enemiesRecruits = emptyList<Recruit>()

    private val actionInfoList = mutableStateListOf<ActionInfo>()
    val currentActionIndex = mutableStateOf<Int?>(null)
    val currentAction get() = currentActionIndex.value?.let { actionInfoList[it] }

    var gameResult by mutableStateOf<GameEndInfo?>(null)

    var location: Location? = null

    init {
        updateUnits(game.units)
    }

    fun getRecruitByID(id: Int): Recruit? {
        val allRecruits = enemiesRecruits + user.recruits.selectedList
        val charId = unitIDs[id]
        return allRecruits.find { it.id == charId }
    }

    fun updateUnits(units: List<GameUnit>) {
        this.units.clear()
        this.units.addAll(units)
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
        unitIDs.clear()
        enemiesRecruits = emptyList()

        currentUnit = null
        selectedUnit = null
        gameResult = null
        location = null
    }
}