package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class MageData(
    override val type: UnitTypes = UnitTypes.MAGICIAN,
    override val maxHP: Int = 100,
    val damage: Int = 25,
    val damageDelta: Int = 20,
    val magicalEffectDamage: Int = 15,
    val magicalEffectTurns: Int = 2,
) : RecruitData {
    override fun absoluteData(level: Int): RecruitData {
        val multiplier = multiplier(level)

        return copy(
            maxHP = (maxHP * multiplier).toInt(),
            damage = (damage * multiplier).toInt(),
            damageDelta = (damageDelta * multiplier).toInt(),
            magicalEffectDamage = (magicalEffectDamage * multiplier).toInt(),
        )
    }
}
