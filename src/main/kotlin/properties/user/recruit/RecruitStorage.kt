package properties.user.recruit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import user

class RecruitStorage {
    private val guildRecruitList: SnapshotStateList<Recruit> = mutableStateListOf()
    private val recruitsList: SnapshotStateList<Recruit> = mutableStateListOf()

    val guildList: List<Recruit> get() = guildRecruitList
    val list: List<Recruit> get() = recruitsList

    fun createNewRecruitToGuild() {
        val factory = RecruitFactory()
        guildRecruitList.add(factory.createRandomUnique(recruitsList + guildRecruitList))
    }

    fun buyRecruit(recruit: Recruit) {
        if (recruit.cost?.isAvailableToBuy == false || recruit.cost == null) return

        guildRecruitList.remove(recruit)
        recruitsList.add(recruit)

        user.coins -= recruit.cost!!.coins
        user.crystals -= recruit.cost!!.crystal

        recruit.cost = null

        createNewRecruitToGuild()
    }
}