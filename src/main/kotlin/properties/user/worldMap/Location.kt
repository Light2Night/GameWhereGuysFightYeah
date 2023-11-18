package properties.user.worldMap

import HasID
import properties.resources.Reward

data class Location(
    override val id: Int,
    val x: Int,
    val y: Int,
    val name: String,
    val description: String,
    val level: Int,
    val danger: Int,
    val minReward: Reward,
    val maxReward: Reward,
    val image: String,
    val enemyTypes: List<Int>,
) : HasID {
    fun getReward(): Reward {
        return Reward(
            coins = (minReward.coins..maxReward.coins).random(),
            crystals = (minReward.crystals..maxReward.crystals).random(),
            exp = (minReward.exp..maxReward.exp).random(),
        )
    }
}
