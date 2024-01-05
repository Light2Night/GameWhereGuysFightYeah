package properties.user.quest

import Game.PlayerTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import gamedata.GameData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lang
import properties.resources.Reward
import properties.user.quest.serializers.KillCharacterQuestSerializer
import properties.user.recruit.RecruitFactory
import kotlin.math.min

@Serializable(with = KillCharacterQuestSerializer::class)
@SerialName("KillCharacterQuest")
class KillCharacterQuest(
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
    val charID: Int,
) : Quest {

    override val langName: String get() = run {
        name.ifBlank {
            lang.kill_quest_name
                .replaceFirstChar { it.uppercaseChar() }
                .replace("<amount>", target.toInt().toString())
                .replace("<name>", RecruitFactory().getPreset(charID)?.name ?: "<ERROR>")
        }
    }

    override var current by mutableStateOf(current)
        private set

    override val progressString: String get() = "${current.toInt()}/${target.toInt()}"

    override fun update(gameData: GameData) {
        val endInfo = gameData.gameResult ?: return
        val teams = endInfo.sessionStatistic.TeamStatistics.keys.filter { it.playerType == PlayerTypes.AI }

        var deadCount = 0
        teams.forEach { team ->
            val deadUnits = endInfo.sessionStatistic.TeamStatistics[team]?.DeadUnits ?: return@forEach
            val recruits = deadUnits.mapNotNull { gameData.getRecruitByID(it.id) }
            deadCount += recruits.filter { it.id == charID }.size
        }

        current = min(target, current + deadCount)
    }

    override fun forceComplete() {
        current = target
    }

    override fun isCompleted(): Boolean = current >= target
}