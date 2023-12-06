package properties.user.chest

import utilities.HasID

data class Chest(
    override val id: Int,
    val type: ChestType,
) : HasID {
    val name get() = type.chestName
    val image get() = type.image

    val sheet = type.rewardSheet
}
