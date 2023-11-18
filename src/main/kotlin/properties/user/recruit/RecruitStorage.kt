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
        val newRecruit = factory.createRandomUnique(
            list = recruitsList + guildRecruitList,
            costCoins = 5..50,
            costCrystals = 0..3
        )

        guildRecruitList.add(newRecruit)
        return newRecruit
    }

    fun buyRecruit(recruit: Recruit, replace: Boolean = true) {
        if (recruit.cost?.isAvailableToBuy == false || recruit.cost == null) return

        guildRecruitList.remove(recruit)
        recruitsList.add(recruit)

        recruit.cost?.let { user.resources.minus(it) }

        recruit.cost = null

        if (replace) createNewRecruitToGuild()
    }

    fun selectRecruit(recruit: Recruit) {
        if (selectedRecruitsList.find { it.id == recruit.id } != null || selectedRecruitsList.size >= maxSelections) return

        selectedRecruitsList.add(recruit)
    }

    fun deselectRecruit(recruit: Recruit) {
        selectedRecruitsList.remove(recruit)
    }
}