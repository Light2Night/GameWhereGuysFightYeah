package properties.user.chest

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import kotlinx.serialization.Serializable
import properties.user.chest.serializers.ChestStorageSerializer
import utilities.uniqueId

@Serializable(with = ChestStorageSerializer::class)
class ChestStorage(
    chests: Map<Int, Chest>? = null,
) {
    val maxAmount = 3
    private val chestList: SnapshotStateMap<Int, Chest> = mutableStateMapOf()

    val list: Map<Int, Chest> get() = chestList

    init {
        chests?.let { chestList.putAll(it) }
    }

    fun addChest(type: ChestType): Chest? {
        val slot = findEmptySlot() ?: return null
        val newChest = Chest(
            id = chestList.values.uniqueId(),
            type = type,
        )

        chestList[slot] = newChest
        return newChest
    }

    fun removeChest(chest: Chest) {
        chestList.keys.forEach {
            if (chestList[it]?.id == chest.id) {
                chestList.remove(it)
                return
            }
        }
    }

    private fun findEmptySlot(): Int? {
        repeat(maxAmount) {i ->
            if (chestList[i] == null) {
                return i
            }
        }

        return null
    }
}