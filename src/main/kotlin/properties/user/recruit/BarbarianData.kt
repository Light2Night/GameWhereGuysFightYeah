package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class BarbarianData(
    override val type: UnitTypes = UnitTypes.BARBARIAN,
    override val maxHP: Int = 200,
    val damage: Int = 20,
    val damageDelta: Int = 10,
) : RecruitData {
    override fun absoluteData(level: Int): RecruitData {
        val multiplier = multiplier(level)

        return copy(
            maxHP = (maxHP * multiplier).toInt(),
            damage = (damage * multiplier).toInt(),
            damageDelta = (damageDelta * multiplier).toInt(),
        )
    }
}
