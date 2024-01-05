package properties.user.quest

import Game.Units.Characters.UnitTypes
import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import gamedata.GameData
import isType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lang
import properties.resources.Reward
import properties.user.quest.serializers.KillQuestSerializer

@Serializable(with = KillQuestSerializer::class)
@SerialName("KillQuest")
class KillQuest(
    override val id: Int,
    override val x: Int,
    override val y: Int,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val requiredLevel: Int,
    override val reward: Reward,
    override val target: Double,
    current: Double = 0.0,
    val unitType: UnitTypes,
) : Quest {

    override val langName: String get() = run {
        name.ifBlank {
            lang.kill_quest_name
                .replaceFirstChar { it.uppercaseChar() }
                .replace("<amount>", target.toInt().toString())
                .replace("<name>", lang.getUnitName(unitType))
        }
    }

    override var current by mutableStateOf(current)
        private set

    override val progressString: String get() = "${current.toInt()}/${target.toInt()}"

    override fun update(gameData: GameData) {
        val endInfo = gameData.gameResult ?: return
        val teams = endInfo.sessionStatistic.TeamStatistics.keys.filter { it.playerType == PlayerTypes.AI }

        var deadUnits = 0
        teams.forEach { team ->
            val count = endInfo.sessionStatistic.TeamStatistics[team]?.DeadUnits?.filter { it.isType(unitType) }?.size
            count?.let { deadUnits += it }
        }

        current += deadUnits
    }

    override fun forceComplete() {
        current = target
    }

    override fun isCompleted(): Boolean = current >= target
}