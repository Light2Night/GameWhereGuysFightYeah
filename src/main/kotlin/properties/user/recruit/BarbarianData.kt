package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class BarbarianData(
    override val type: UnitTypes = UnitTypes.BARBARIAN,
    override val maxHP: Int = 200,
    val damage: Int = 15,
    val damageDelta: Int = 10,
) : RecruitData
