package properties.user.quest

import Game.Units.Characters.UnitTypes
import Game.Event.Arguments.GameEndInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import properties.resources.Reward

class QuestStorage {
    private val maxAmount = 6
    private val requestList: SnapshotStateList<Quest> = mutableStateListOf(
        KillQuest(0, 0, 0, "Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), UnitTypes.BARBARIAN, 2),
        KillQuest(0, 1, 0, "Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), UnitTypes.BARBARIAN, 3),
        KillQuest(0, 2, 1,"Test_Name", "Test_Description", "Test_Icon", 1, Reward(1, 1, 1), UnitTypes.BARBARIAN, 5),
    )

    val list: List<Quest> get() = requestList

    fun getRequestByPosition(x: Int, y: Int): Quest? = requestList.find { it.x == x && it.y == y }

    fun createRequest(request: Quest) {
        if (requestList.size < maxAmount) {
            requestList.add(request)
        }
    }

    fun checkAllRequests() {
        requestList.forEach {
            if (it.isCompleted()) {
                it.getReward()
            }
        }

        requestList.removeIf { it.isCompleted() }
    }

    fun updateAllRequests(endInfo: GameEndInfo) {
        requestList.forEach { it.update(endInfo) }
    }
}