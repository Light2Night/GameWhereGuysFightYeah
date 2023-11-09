package properties.user.request

import Game.Characters.UnitTypes
import Game.Event.Arguments.GameEndInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class RequestsStorage {
    private val maxAmount = 6
    private val requestList: SnapshotStateList<GuildRequest> = mutableStateListOf(
        KillRequest(0, 0, 0, "Test_Name", "Test_Description", "Test_Icon", 1, 1, 1, 1, UnitTypes.BARBARIAN, 2),
        KillRequest(0, 1, 0, "Test_Name", "Test_Description", "Test_Icon", 1, 1, 1, 1, UnitTypes.BARBARIAN, 3),
        KillRequest(0, 2, 1,"Test_Name", "Test_Description", "Test_Icon", 1, 1, 1, 1, UnitTypes.BARBARIAN, 5),
    )

    val list: List<GuildRequest> get() = requestList

    fun getRequestByPosition(x: Int, y: Int): GuildRequest? = requestList.find { it.x == x && it.y == y }

    fun createRequest(request: GuildRequest) {
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