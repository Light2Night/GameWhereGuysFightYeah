package properties.user.recruit

import Game.Units.Characters.UnitTypes

interface RecruitData {
    val type: UnitTypes
    val maxHP: Int
}