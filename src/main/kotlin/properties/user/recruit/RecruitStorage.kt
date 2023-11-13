package properties.user.recruit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import user

class RecruitStorage {
    private val factory = RecruitFactory()
    val maxSelections = 5

    private val guildRecruitList: SnapshotStateList<Recruit> = mutableStateListOf()
    private val recruitsList: SnapshotStateList<Recruit> = mutableStateListOf()
    private val selectedRecruitsList: SnapshotStateList<Recruit> = mutableStateListOf()

    val guildList: List<Recruit> get() = guildRecruitList
    val list: List<Recruit> get() = recruitsList
    val selectedList: List<Recruit> get() = selectedRecruitsList

    fun createNewRecruitToGuild(): Recruit {
        val newRecruit = factory.createRandomUnique(recruitsList + guildRecruitList, costCoins = 1..3)
        guildRecruitList.add(newRecruit)
        return newRecruit
    }

    fun buyRecruit(recruit: Recruit, replace: Boolean = true) {
        if (recruit.cost?.isAvailableToBuy == false || recruit.cost == null) return

        guildRecruitList.remove(recruit)
        recruitsList.add(recruit)

        user.coins -= recruit.cost!!.coins
        user.crystals -= recruit.cost!!.crystals

        recruit.cost = null

        if (replace) createNewRecruitToGuild()
    }

    fun selectRecruit(recruit: Recruit) {
        if (selectedRecruitsList.contains(recruit) || selectedRecruitsList.size >= maxSelections) return

        selectedRecruitsList.add(recruit)
    }

    fun deselectRecruit(recruit: Recruit) {
        selectedRecruitsList.remove(recruit)
    }
}