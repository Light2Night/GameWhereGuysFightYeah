package properties.user

import androidx.compose.ui.graphics.ImageBitmap
import getImageBitmap
import properties.user.recruit.RecruitStorage
import properties.user.quest.QuestStorage
import properties.user.worldMap.WorldMap

//@Serializable
data class User(
    var name: String = "Test_Name",
    var coins: Int = 0,
    var crystals: Int = 0,
    var exp: Int = 30,
    var expTarget: Int = 100,
    var lvl: Int = 1,
    val requests: QuestStorage = QuestStorage(),
    val recruits: RecruitStorage = RecruitStorage(),
    val worldMap: WorldMap = WorldMap(),
) {
    val profileImage: ImageBitmap? get() =
        getImageBitmap("data/user/user.webp")
            ?: getImageBitmap("data/user/user.png")
}
