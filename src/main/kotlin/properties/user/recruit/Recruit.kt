package properties.user.recruit

import Game.Units.Factories.ViewModels.BarbarianViewModel
import Game.Units.Factories.ViewModels.BaseUnitViewModel
import Game.Units.Factories.ViewModels.HealerViewModel
import Game.Units.Factories.ViewModels.MageViewModel
import kotlinx.serialization.Serializable
import utilities.HasID
import properties.resources.Cost
import properties.textures.Textures
import properties.user.recruit.serializers.RecruitSerializer
import ui.screens.cutsceneScreen.Expression

@Serializable(with = RecruitSerializer::class)
data class Recruit(
    override val id: Int,
    val charID: Int,
    val name: String,
    val description: String,
    val stars: Int,
    var level: Int,
    val profileImage: String,
    val expressions: List<Expression>,
    val rawData: RecruitData,
    var cost: Cost? = null,
) : HasID {
    val image get() = Textures[profileImage]

    val absoluteData get() = rawData.absoluteData(level)

    fun toViewModel(): BaseUnitViewModel {
        return when (val absoluteData = rawData.absoluteData(level)) {
            is BarbarianData -> BarbarianViewModel(
                name,
                absoluteData.maxHP,
                absoluteData.maxHP,
                absoluteData.damage,
                absoluteData.damageDelta,
            )
            is MageData -> MageViewModel(
                name,
                absoluteData.maxHP,
                absoluteData.maxHP,
                absoluteData.damage,
                absoluteData.damageDelta,
            )
            is HealerData -> HealerViewModel(
                name,
                absoluteData.maxHP,
                absoluteData.maxHP,
                absoluteData.heal,
            )
            else -> throw Exception("Unknown Recruit Data")
        }
    }
}
