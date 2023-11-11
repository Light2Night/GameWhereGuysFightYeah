package properties.user.recruit

import Game.Effects.Healling
import Game.Effects.Poisoning
import Game.Units.Factories.ViewModels.BarbarianViewModel
import Game.Units.Factories.ViewModels.BaseUnitViewModel
import Game.Units.Factories.ViewModels.HealerViewModel
import Game.Units.Factories.ViewModels.MageViewModel
import HasID

data class Recruit(
    override val id: Int,
    val charID: Int,
    val name: String,
    val description: String,
    val stars: Int,
    var level: Int,
    val profileImage: String,
    val data: RecruitData,
    var cost: RecruitCost? = null,
) : HasID {
    fun toViewModel(): BaseUnitViewModel {
        return when (data) {
            is BarbarianData -> BarbarianViewModel(
                name,
                data.maxHP,
                data.maxHP,
                data.damage,
                data.damageDelta,
            )
            is MageData -> MageViewModel(
                name,
                data.maxHP,
                data.maxHP,
                data.damage,
                data.damageDelta,
                Poisoning(),
            )
            is HealerData -> HealerViewModel(
                name,
                data.maxHP,
                data.maxHP,
                data.heal,
                Healling(),
            )
            else -> throw Exception("Unknown Recruit Data")
        }
    }
}
