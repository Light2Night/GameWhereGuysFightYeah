package properties.user.recruit

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
) : HasID
