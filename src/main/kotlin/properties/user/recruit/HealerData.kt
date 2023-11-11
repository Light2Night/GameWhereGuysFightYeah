package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class HealerData(
    override val type: UnitTypes = UnitTypes.HEALER,
    override val maxHP: Int = 125,
    val heal: Int = 20,
    val healingEffectHeal: Int = 10,
    val healingEffectTurns: Int = 3,
) : RecruitData
