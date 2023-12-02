package properties.user.chest

data class Chest(
    val id: Int,
    val type: ChestType,
) {
    val name get() = type.chestName

    val sheet = type.rewardSheet
}
