package properties.user.recruit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.Serializable
import properties.user.recruit.serializers.RecruitStorageSerializer
import user

@Serializable(with = RecruitStorageSerializer::class)
class RecruitStorage(
    guildRecruits: List<Recruit>? = null,
    recruits: List<Recruit>? = null,
    selectedRecruits: List<Recruit>? = null,
) {
    private val factory = RecruitFactory()
    val maxGuildRecruits = 4
    val maxSelections = 5

    private val guildRecruitList: SnapshotStateList<Recruit> = mutableStateListOf()
    private val recruitsList: SnapshotStateList<Recruit> = mutableStateListOf()
    private val selectedRecruitsList: SnapshotStateList<Recruit> = mutableStateListOf()

    val guildList: List<Recruit> get() = guildRecruitList
    val list: List<Recruit> get() = recruitsList
    val selectedList: List<Recruit> get() = selectedRecruitsList

    init {
        guildRecruits?.let { guildRecruitList.addAll(it) }
        recruits?.let { recruitsList.addAll(it) }
        selectedRecruits?.let { selectedRecruitsList.addAll(it) }

        while (guildRecruitList.size < maxGuildRecruits) {
            createNewRecruitToGuild()
        }
    }

    fun createNewRecruitToGuild(): Recruit {
        val newRecruit = factory.createRandomUnique(
            list = recruitsList + guildRecruitList,
            costCoins = 5..50,
            costCrystals = 0..3
        )

        guildRecruitList.add(newRecruit)
        return newRecruit
    }

    fun createNewRecruit(levels: IntRange): Recruit {
        val newRecruit = factory.createRandomUnique(
            list = recruitsList + guildRecruitList,
            levels = levels,
        ).also { it.cost = null }

        recruitsList.add(newRecruit)
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