package properties.user.chest

import user

class RewardSheetEntry(
    val type: SheetEntryType,
    val weight: Int = 0,
    val value: IntRange = 0..0,
) {
    var absoluteValue: Int? = null
        private set
        get() {
            if (field != null) {
                return field!!
            }

            field = value.random()
            return field!!
        }

    fun copy(): RewardSheetEntry {
        return RewardSheetEntry(
            type,
            weight,
            value,
        )
    }

    fun giveReward() {
        when (type) {
            SheetEntryType.Coins -> absoluteValue?.let { user.resources.coins += it }
            SheetEntryType.Crystals -> absoluteValue?.let { user.resources.crystals += it }
            SheetEntryType.OneStar -> absoluteValue?.let { user.recruits.createNewRecruit(it..it) }
            SheetEntryType.TwoStar -> absoluteValue?.let { user.recruits.createNewRecruit(it..it) }
            SheetEntryType.ThreeStar -> absoluteValue?.let { user.recruits.createNewRecruit(it..it) }
        }
    }
}