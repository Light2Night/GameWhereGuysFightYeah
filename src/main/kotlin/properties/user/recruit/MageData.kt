package properties.user.recruit

import Game.Units.Characters.UnitTypes

data class MageData(
    override val type: UnitTypes = UnitTypes.MAGICIAN,
    override val maxHP: Int = 100,
    val damage: Int = 25,
    val damageDelta: Int = 20,
    val magicalEffectDamage: Int = 15,
    val magicalEffectTurns: Int = 2,
) : RecruitData
