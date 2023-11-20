package properties.resources

import user

interface Resources {
    var coins: Int
    var crystals: Int
    var exp: Int

    val isAvailableToBuy: Boolean get() = user.resources.coins >= coins && user.resources.crystals >= crystals

    fun giveToUser() {
        user.resources.plus(this)
    }

    fun plus(other: Resources) {
        coins += other.coins
        crystals += other.crystals
        exp += other.exp
    }

    fun minus(other: Resources) {
        coins -= other.coins
        crystals -= other.crystals
        exp -= other.exp
    }
}