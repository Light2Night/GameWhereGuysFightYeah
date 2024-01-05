package properties.user.quest

import utilities.HasID
import gamedata.GameData
import properties.resources.Reward

interface Quest : HasID {
    override val id: Int
    val x: Int
    val y: Int
    val name: String
    val langName: String
    val description: String
    val icon: String
    val requiredLevel: Int
    val reward: Reward
    val target: Double
    val current: Double

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
