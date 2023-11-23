package properties.user.quest

import Game.Units.Characters.UnitTypes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import gamedata.GameData
import properties.resources.Reward

class QuestStorage {
    private val maxAmount = 6
    private val questList: SnapshotStateList<Quest> = mutableStateListOf(
        KillQuest(0, 0, 0, "Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), UnitTypes.BARBARIAN, 2),
        KillCharacterQuest(0, 1, 0, "Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), 0, 3),
        KillNameQuest(0, 2, 1,"Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), "Arthur", 5),
    )

    val list: List<Quest> get() = questList

    fun getQuestByPosition(x: Int, y: Int): Quest? = questList.find { it.x == x && it.y == y }

    fun createQuest(request: Quest) {
        if (questList.size < maxAmount) {
            questList.add(request)
        }
    }

    fun checkAllQuests() {
        questList.forEach {
            if (it.isCompleted()) {
                it.getReward()
            }
        }

        questList.removeIf { it.isCompleted() }
    }

    fun updateAllQuests(gameData: GameData) {
        questList.forEach { it.update(gameData) }
    }
}