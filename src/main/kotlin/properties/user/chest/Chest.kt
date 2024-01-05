package properties.user.chest

import kotlinx.serialization.Serializable
import properties.user.chest.serializers.ChestSerializer
import utilities.HasID

@Serializable(with = ChestSerializer::class)
data class Chest(
    override val id: Int,
    val type: ChestType,
    val sheet: RewardSheet = type.rewardSheet,
) : HasID {
    val name get() = type.chestName
    val image get() = type.image
}
