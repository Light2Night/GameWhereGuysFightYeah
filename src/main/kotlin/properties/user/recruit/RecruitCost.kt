package properties.user.recruit

import user

data class RecruitCost(
    val coins: Int,
    val crystal: Int,
) {
    val isAvailableToBuy: Boolean get() = user.coins >= coins && user.crystals >= crystal
}
