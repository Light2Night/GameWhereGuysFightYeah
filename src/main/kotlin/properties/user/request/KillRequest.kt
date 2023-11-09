package properties.user.request

import Game.Characters.UnitTypes
import Game.Event.Arguments.GameEndInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import user

class KillRequest(
    override val id: Int,
    override val x: Int,
    override val y: Int,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val requiredLevel: Int,
    override val rewardCoins: Int,
    override val rewardCrystals: Int,
    override val rewardExp: Int,
    val unitType: UnitTypes,
    val amount: Int,
) : GuildRequest {

    var count by mutableStateOf(0)
        private set

    override val progressString: String get() = "$count/$amount"

    override fun update(endInfo: GameEndInfo) {
        count++
    }

    override fun isCompleted(): Boolean = count >= amount

    override fun getReward() {
        if (isCompleted()) {
            user.coins += rewardCoins
            user.crystals += rewardCrystals
            user.exp += rewardExp
        }
    }
}