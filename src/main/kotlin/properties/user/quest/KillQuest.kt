package properties.user.quest

import Game.Units.Characters.UnitTypes
import Game.Event.Arguments.GameEndInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    override fun update(endInfo: GameEndInfo) {
        count++
    }

    override fun isCompleted(): Boolean = count >= amount
}