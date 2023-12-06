package properties.user.chest

import emptyImageBitmap
import getImageBitmap
import lang

enum class ChestType {
    Wooden, Stone, Iron, Gold, Crystal, Legendary, Dragon;

    val chestName get() = when (this) {
        Wooden -> lang.wooden_chest_name.replaceFirstChar { it.uppercaseChar() }
        Stone -> lang.stone_chest_name.replaceFirstChar { it.uppercaseChar() }
        Iron -> lang.iron_chest_name.replaceFirstChar { it.uppercaseChar() }
        Gold -> lang.gold_chest_name.replaceFirstChar { it.uppercaseChar() }
        Crystal -> lang.crystal_chest_name.replaceFirstChar { it.uppercaseChar() }
        Legendary -> lang.legendary_chest_name.replaceFirstChar { it.uppercaseChar() }
        Dragon -> lang.dragon_chest_name.replaceFirstChar { it.uppercaseChar() }
    }

    val image get() = when (this) {
        Wooden -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Stone -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Iron -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Gold -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Crystal -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Legendary -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
        Dragon -> getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap
    }

    val rewardSheet get() = when (this) {
        Wooden -> RewardSheet(
            amount = 2,
            coins = RewardSheetEntry(SheetEntryType.Coins, 5, 10..50),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 1, 1..3),
        )
        Stone -> RewardSheet(
            amount = 4,
            coins = RewardSheetEntry(SheetEntryType.Coins, 8, 20..70),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 3, 1..5),
            oneStar = RewardSheetEntry(SheetEntryType.OneStar, 1, 1..1),
        )
        Iron -> RewardSheet(
            amount = 4,
            coins = RewardSheetEntry(SheetEntryType.Coins, 12, 50..100),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 5, 1..5),
            oneStar = RewardSheetEntry(SheetEntryType.OneStar, 2, 1..2),
            twoStar = RewardSheetEntry(SheetEntryType.TwoStar, 1, 1..1),
        )
        Gold -> RewardSheet(
            amount = 6,
            coins = RewardSheetEntry(SheetEntryType.Coins, 12, 50..100),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 6, 2..5),
            oneStar = RewardSheetEntry(SheetEntryType.OneStar, 4, 1..2),
            twoStar = RewardSheetEntry(SheetEntryType.TwoStar, 2, 1..1),
        )
        Crystal -> RewardSheet(
            amount = 6,
            coins = RewardSheetEntry(SheetEntryType.Coins, 15, 100..250),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 8, 5..10),
            twoStar = RewardSheetEntry(SheetEntryType.TwoStar, 4, 1..2),
            threeStar = RewardSheetEntry(SheetEntryType.ThreeStar, 1, 1..1),
        )
        Legendary -> RewardSheet(
            amount = 8,
            coins = RewardSheetEntry(SheetEntryType.Coins, 15, 200..400),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 10, 5..10),
            twoStar = RewardSheetEntry(SheetEntryType.TwoStar, 6, 1..4),
            threeStar = RewardSheetEntry(SheetEntryType.ThreeStar, 2, 1..2),
        )
        Dragon -> RewardSheet(
            amount = 10,
            coins = RewardSheetEntry(SheetEntryType.Coins, 15, 300..500),
            crystals = RewardSheetEntry(SheetEntryType.Crystals, 12, 10..20),
            twoStar = RewardSheetEntry(SheetEntryType.TwoStar, 10, 1..5),
            threeStar = RewardSheetEntry(SheetEntryType.ThreeStar, 5, 1..2),
        )
    }
}
