package properties.user.worldMap

import HasID
import org.jetbrains.skiko.currentNanoTime
import properties.resources.Reward
import kotlin.random.Random

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
            coins = Random(currentNanoTime()).nextInt(minReward.coins, maxReward.coins),
            crystals = Random(currentNanoTime()).nextInt(minReward.crystals, maxReward.crystals),
            exp = Random(currentNanoTime()).nextInt(minReward.exp, maxReward.exp),
        )
    }
}
