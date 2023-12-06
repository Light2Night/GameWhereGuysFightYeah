package properties.user.chest

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import utilities.uniqueId

class ChestStorage {
    val maxAmount = 3
    private val chestList: SnapshotStateMap<Int, Chest> = mutableStateMapOf()

    val list: Map<Int, Chest> get() = chestList

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