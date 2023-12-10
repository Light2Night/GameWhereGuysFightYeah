package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class HealerData(
    override val type: UnitTypes = UnitTypes.HEALER,
    override val maxHP: Int = 125,
    val heal: Int = 15,
    val healingEffectHeal: Int = 7,
    val healingEffectTurns: Int = 3,
) : RecruitData {
    override fun absoluteData(level: Int): RecruitData {
        val multiplier = multiplier(level)

        return copy(
            maxHP = (maxHP * multiplier).toInt(),
            heal = (heal * multiplier).toInt(),
            healingEffectHeal = (healingEffectHeal * multiplier).toInt(),
        )
    }
}
