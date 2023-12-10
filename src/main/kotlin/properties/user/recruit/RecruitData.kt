package properties.user.recruit

import Game.Units.Characters.UnitTypes

interface RecruitData {
    val type: UnitTypes
    val maxHP: Int

    fun absoluteData(level: Int): RecruitData

    fun multiplier(level: Int): Double  = 1.0 + (level - 1) / 10.0
}