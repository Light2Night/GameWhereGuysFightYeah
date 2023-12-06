package properties.user.worldMap

import org.jetbrains.skiko.currentNanoTime
import utilities.HasID
import properties.resources.Reward
import properties.user.chest.ChestType
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
    val chestChances: Map<ChestType, Int>,
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

    fun getRandomChestType(): ChestType? {
        val sum = chestChances.values.sum()
        val random = Random(currentNanoTime()).nextInt(sum)

        var current = 0
        for ((type, chance) in chestChances) {
            current += chance

            if (random < current) {
                return type
            }
        }

        return null
    }
}
