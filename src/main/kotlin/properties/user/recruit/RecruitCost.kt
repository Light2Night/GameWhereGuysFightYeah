package properties.user.recruit

import user

data class RecruitCost(
    val coins: Int,
    val crystals: Int,
) {
    val isAvailableToBuy: Boolean get() = user.coins >= coins && user.crystals >= crystals
}
