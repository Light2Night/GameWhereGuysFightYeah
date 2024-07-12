package properties.user.recruit

import Game.Units.Characters.UnitTypes
import properties.resources.Cost
import ui.screens.cutsceneScreen.Expression
import utilities.uniqueId

class RecruitFactory {
    private val mobBarbarianNames: List<String> = listOf(
        "Johnny",
        "Leonardo",
        "Michelangelo",
        "Raphael",
        "Donatello",
        "Pietro",
        "Giotto",
        "Giovanni",
    )

    private val mobMagicianNames: List<String> = listOf(
        "Lancelot",
        "Mordred",
        "Arthur",
        "Gandalf",
    )

    private val mobHealerNames: List<String> = listOf(
        "Ludo",
        "Nika",
        "Aurora",
        "Luna",
        "Athena",
        "Hera",
    )

    val randomName get() = (mobBarbarianNames + mobMagicianNames + mobHealerNames).random()

    private val presets: List<Recruit> = listOf(
        Recruit(
            id = 0,
            charID = 0,
            name = "test0",
            description = "description_test0",
            stars = 1,
            level = 1,
            profileImage = "characters/barbarian_placeholder.png",
            expressions = listOf(
                Expression("neutral", "characters/barbarian_placeholder.png")
            ),
            rawData = BarbarianData(),
        ),
        Recruit(
            id = 1,
            charID = 1,
            name = "test1",
            description = "description_test1",
            stars = 1,
            level = 1,
            profileImage = "characters/magician_placeholder.png",
            expressions = listOf(
                Expression("neutral", "characters/magician_placeholder.png")
            ),
            rawData = MageData(),
        ),
        Recruit(
            id = 2,
            charID = 2,
            name = "test2",
            description = "description_test2",
            stars = 1,
            level = 1,
            profileImage = "characters/healer_placeholder.png",
            expressions = listOf(
                Expression("neutral", "characters/healer_placeholder.png"),
                Expression("sad", "characters/healer_expressions/healer_placeholder_sad.png")
            ),
            rawData = HealerData(),
        ),
    )

    val randomPreset get() = presets.random()

    fun getPreset(charID: Int) = presets.find { it.charID == charID }

    fun createRandomUnique(
        list: List<Recruit>,
        levels: IntRange = 1..1,
        costCoins: IntRange = 1..1,
        costCrystals: IntRange = 1..1
    ): Recruit {
        val preset = presets.random()

        val name = if (preset.stars == 1) {
            when (preset.rawData.type) {
                UnitTypes.BARBARIAN -> mobBarbarianNames.random()
                UnitTypes.MAGICIAN -> mobMagicianNames.random()
                UnitTypes.HEALER -> mobHealerNames.random()
            }
        } else {
            preset.name
        }

        return preset.copy(
            name = name,
            id = list.uniqueId(),
            level = levels.random(),
            cost = Cost(
                coins = costCoins.random(),
                crystals = costCrystals.random(),
                0,
            )
        )
    }

    fun createRandomUnique(
        list: List<Recruit>,
        type: UnitTypes,
        levels: IntRange = 1..1,
        costCoins: IntRange = 1..1,
        costCrystals: IntRange = 1..1
    ): Recruit {
        val preset = presets.filter { it.rawData.type == type }.random()

        val name = if (preset.stars == 1) {
            when (preset.rawData.type) {
                UnitTypes.BARBARIAN -> mobBarbarianNames.random()
                UnitTypes.MAGICIAN -> mobMagicianNames.random()
                UnitTypes.HEALER -> mobHealerNames.random()
            }
        } else {
            preset.name
        }

        return preset.copy(
            name = name,
            id = list.uniqueId(),
            level = levels.random(),
            cost = Cost(
                coins = costCoins.random(),
                crystals = costCrystals.random(),
                0,
            )
        )
    }
}