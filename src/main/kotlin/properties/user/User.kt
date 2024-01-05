package properties.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import getImageBitmap
import kotlinx.serialization.Serializable
import properties.resources.UserResources
import properties.resources.calculateLevel
import properties.resources.calculateRequiredExp
import properties.user.chest.ChestStorage
import properties.user.recruit.RecruitStorage
import properties.user.quest.QuestStorage
import properties.user.worldMap.WorldMap

@Serializable(with = UserSerializer::class)
class User(
    name: String = "Test_Name",
    resources: UserResources = UserResources(
        coins = 50,
        crystals = 3,
        exp = 0,
        updateLevels = false,
    ),
    level: Int = calculateLevel(resources.exp),
    quests: QuestStorage = QuestStorage(),
    recruits: RecruitStorage = RecruitStorage(),
    worldMap: WorldMap = WorldMap(),
    chests: ChestStorage = ChestStorage(),
) {
    var name: String by mutableStateOf(name)
    var resources: UserResources by mutableStateOf(resources)
    var level: Int by mutableStateOf(level)
    var quests: QuestStorage by mutableStateOf(quests)
    var recruits: RecruitStorage by mutableStateOf(recruits)
    var worldMap: WorldMap by mutableStateOf(worldMap)
    var chests: ChestStorage by mutableStateOf(chests)

    val profileImage: ImageBitmap? get() =
        getImageBitmap("data/user/user.webp")
            ?: getImageBitmap("data/user/user.png")

    var requiredExp: Int by mutableStateOf(calculateRequiredExp(level))
}
