package properties.user.chest

import androidx.compose.runtime.mutableStateListOf

class RewardSheet(
    val amount: Int,
    val coins: RewardSheetEntry = RewardSheetEntry(SheetEntryType.Coins),
    val crystals: RewardSheetEntry = RewardSheetEntry(SheetEntryType.Crystals),
    val oneStar: RewardSheetEntry = RewardSheetEntry(SheetEntryType.OneStar),
    val twoStar: RewardSheetEntry = RewardSheetEntry(SheetEntryType.TwoStar),
    val threeStar: RewardSheetEntry = RewardSheetEntry(SheetEntryType.ThreeStar),
) {
    var entries = mutableStateListOf<RewardSheetEntry>()

    fun getNextEntry(): RewardSheetEntry? {
        if (entries.size >= amount) return null

        val allEntries = listOf(coins, crystals, oneStar, twoStar, threeStar).filter { it.weight > 0 }
        val maxWeight = allEntries.maxOf { it.weight }
        val randomWeight = (1..maxWeight).random()
        val possibleEntries = allEntries.filter { it.weight >= randomWeight }

        val entry = possibleEntries.random().copy()
        entries.add(entry)
        return entry
    }

    fun giveReward() {
        entries.forEach { it.giveReward() }
    }
}