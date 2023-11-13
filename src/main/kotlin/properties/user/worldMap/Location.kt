package properties.user.worldMap

import HasID

data class Location(
    override val id: Int,
    val x: Int,
    val y: Int,
    val name: String,
    val description: String,
    val level: Int,
    val danger: Int,
    val image: String,
    val enemies: List<String>,
) : HasID
