package properties.user.chest

class RewardSheetEntry(
    val type: SheetEntryType,
    val weight: Int = 0,
    val value: IntRange = 0..0,
) {
    private var absoluteValue: Int? = null

    fun getAbsoluteValue(): Int {
        if (absoluteValue != null) {
            return absoluteValue!!
        }

        absoluteValue = value.random()
        return absoluteValue!!
    }

    fun copy(): RewardSheetEntry {
        return RewardSheetEntry(
            type,
            weight,
            value,
        )
    }
}