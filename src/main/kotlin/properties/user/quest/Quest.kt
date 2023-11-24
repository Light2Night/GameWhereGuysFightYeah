package properties.user.quest

import HasID
import gamedata.GameData
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

    fun update(gameData: GameData)
    fun forceComplete()
    fun isCompleted(): Boolean
    fun getReward() {
        if (isCompleted()) {
            reward.giveToUser()
        }
    }
}
