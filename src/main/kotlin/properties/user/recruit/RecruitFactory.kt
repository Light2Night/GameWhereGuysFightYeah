package properties.user.recruit

import Game.Units.Characters.UnitTypes
import properties.resources.Cost
import uniqueId

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
        Recruit(0, 0, "test0", "description_test0", 1, 1, "textures/characters/barbarian_placeholder.png", BarbarianData()),
        Recruit(1, 1, "test1", "description_test1", 1, 1, "textures/characters/magician_placeholder.png", MageData()),
        Recruit(2, 2, "test2", "description_test2", 1, 1, "textures/characters/healer_placeholder.png", HealerData()),
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
            when (preset.data.type) {
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
        val preset = presets.filter { it.data.type == type }.random()

        val name = if (preset.stars == 1) {
            when (preset.data.type) {
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