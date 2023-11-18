package properties.user.quest

import Game.Event.Arguments.GameEndInfo
import HasID
import properties.resources.Reward

interface Quest : HasID {
    override val id: Int
    val x: Int
    val y: Int
    val name: String
    val description: String
    val icon: String
    val requiredLevel: Int
    val reward: Reward

    val progressString: String

    fun update(endInfo: GameEndInfo)
    fun isCompleted(): Boolean
    fun getReward() {
        if (isCompleted()) {
            reward.giveToUser()
        }
    }
}
