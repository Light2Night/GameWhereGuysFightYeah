package properties.user.worldMap

class WorldMap {
    val locations: List<Location> = listOf(
        Location(0, 400, 320, "Test0", "description_Test0", 0, 0, "Test0", listOf(1, 2)),
        Location(1, 1000, 400, "Test1", "description_Test1", 0, 0, "Test1", listOf(0, 1)),
        Location(2, 300, 700, "Test2", "description_Test2", 0, 0, "Test2", listOf(0, 1, 2)),
    )
}
