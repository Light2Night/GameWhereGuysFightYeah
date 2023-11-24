package properties.user.quest

import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import gamedata.GameData
import lang
import properties.resources.Reward
import kotlin.math.min

class KillNameQuest(
    override val id: Int,
    override val x: Int,
    override val y: Int,
    name: String,
    override val description: String,
    override val icon: String,
    override val requiredLevel: Int,
    override val reward: Reward,
    val charName: String,
    val amount: Int,
) : Quest {

    private val _name = name
    override val name: String get() = run {
        _name.ifBlank {
            lang.kill_quest_name
                .replaceFirstChar { it.uppercaseChar() }
                .replace("<amount>", amount.toString())
                .replace("<name>", charName)
        }
    }

    var count by mutableStateOf(0)
        private set

    override val progressString: String get() = "$count/$amount"

    override fun update(gameData: GameData) {
        val endInfo = gameData.gameResult ?: return
        val teams = endInfo.sessionStatistic.TeamStatistics.keys.filter { it.playerType == PlayerTypes.AI }

        var deadCount = 0
        teams.forEach { team ->
            val deadUnits = endInfo.sessionStatistic.TeamStatistics[team]?.DeadUnits ?: return@forEach
            val recruits = deadUnits.mapNotNull { gameData.getRecruitByID(it.id) }
            deadCount += recruits.filter { it.name == charName }.size
        }

        count = min(amount, count + deadCount)
    }

    override fun forceComplete() {
        count = amount
    }

    override fun isCompleted(): Boolean = count >= amount
}