package properties.user.request

import Game.Event.Arguments.GameEndInfo
import HasID

interface GuildRequest : HasID {
    override val id: Int
    val x: Int
    val y: Int
    val name: String
    val description: String
    val icon: String
    val requiredLevel: Int
    val rewardCoins: Int
    val rewardCrystals: Int
    val rewardExp: Int

    val progressString: String

    fun update(endInfo: GameEndInfo)
    fun isCompleted(): Boolean
    fun getReward()
}
