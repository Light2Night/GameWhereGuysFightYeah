package properties.user.quest

import Game.Units.Characters.UnitTypes
import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import gamedata.GameData
import isType
import properties.resources.Reward

class KillQuest(
    override val id: Int,
    override val x: Int,
    override val y: Int,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val requiredLevel: Int,
    override val reward: Reward,
    val unitType: UnitTypes,
    val amount: Int,
) : Quest {

    var count by mutableStateOf(0)
        private set

    override val progressString: String get() = "$count/$amount"

    override fun update(gameData: GameData) {
        val endInfo = gameData.gameResult ?: return
        val teams = endInfo.sessionStatistic.TeamStatistics.keys.filter { it.playerType == PlayerTypes.AI }

        var deadUnits = 0
        teams.forEach { team ->
            val count = endInfo.sessionStatistic.TeamStatistics[team]?.DeadUnits?.filter { it.isType(unitType) }?.size
            count?.let { deadUnits += it }
        }

        count += deadUnits
    }

    override fun forceComplete() {
        count = amount
    }

    override fun isCompleted(): Boolean = count >= amount
}