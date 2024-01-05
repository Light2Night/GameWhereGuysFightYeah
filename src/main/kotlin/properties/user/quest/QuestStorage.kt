package properties.user.quest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import gamedata.GameData
import kotlinx.serialization.Serializable
import properties.user.quest.serializers.QuestStorageSerializer
import utilities.uniqueId

@Serializable(with = QuestStorageSerializer::class)
class QuestStorage(
    quests: List<Quest>? = null,
) {
    private val maxAmount = 6
    private val questList: SnapshotStateList<Quest> = mutableStateListOf()

    val list: List<Quest> get() = questList

    init {
        quests?.let { questList.addAll(it) }

        while (questList.size < 6) {
            createQuest()
        }
    }

    fun getQuestByPosition(x: Int, y: Int): Quest? = questList.find { it.x == x && it.y == y }

    fun createQuest() {
        if (questList.size < maxAmount) {
            val slot = findEmptySlot() ?: return

            QuestFactory().createRandomQuest(slot.first, slot.second, questList.uniqueId())?.let { questList.add(it) }
        }
    }

    private fun findEmptySlot(): Pair<Int, Int>? {
        repeat(3) { x ->
            repeat(2) { y ->
                if (getQuestByPosition(x, y) == null) {
                    return Pair(x, y)
                }
            }
        }

        return null
    }

    fun checkAllQuests() {
        questList.forEach {
            if (it.isCompleted()) {
                it.getReward()
            }
        }

        questList.removeIf { it.isCompleted() }

        while (findEmptySlot() != null) {
            createQuest()
        }
    }

    fun updateAllQuests(gameData: GameData) {
        questList.forEach { it.update(gameData) }
    }
}