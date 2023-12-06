package properties.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import getImageBitmap
import properties.resources.UserResources
import properties.resources.calculateRequiredExp
import properties.user.chest.ChestStorage
import properties.user.recruit.RecruitStorage
import properties.user.quest.QuestStorage
import properties.user.worldMap.WorldMap

//@Serializable
data class User(
    var name: String = "Test_Name",
    var resources: UserResources = UserResources(
        coins = 50,
        crystals = 3,
        exp = 0,
    ),
    var level: Int = 1,
    val quests: QuestStorage = QuestStorage(),
    val recruits: RecruitStorage = RecruitStorage(),
    val worldMap: WorldMap = WorldMap(),
    val chests: ChestStorage = ChestStorage(),
) {
    val profileImage: ImageBitmap? get() =
        getImageBitmap("data/user/user.webp")
            ?: getImageBitmap("data/user/user.png")

    var requiredExp: Int by mutableStateOf(calculateRequiredExp(level))
}
