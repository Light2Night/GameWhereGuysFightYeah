package properties.user.recruit

import Game.Characters.UnitTypes

interface RecruitData {
    val type: UnitTypes
    val maxHP: Int
}