package properties.user.recruit

import Game.Units.Factories.ViewModels.BarbarianViewModel
import Game.Units.Factories.ViewModels.BaseUnitViewModel
import Game.Units.Factories.ViewModels.HealerViewModel
import Game.Units.Factories.ViewModels.MageViewModel
import utilities.HasID
import properties.resources.Cost

data class Recruit(
    override val id: Int,
    val charID: Int,
    val name: String,
    val description: String,
    val stars: Int,
    var level: Int,
    val profileImage: String,
    val data: RecruitData,
    var cost: Cost? = null,
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
            )
            is HealerData -> HealerViewModel(
                name,
                data.maxHP,
                data.maxHP,
                data.heal,
            )
            else -> throw Exception("Unknown Recruit Data")
        }
    }
}
