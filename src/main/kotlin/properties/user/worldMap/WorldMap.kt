package properties.user.worldMap

import properties.resources.Reward

class WorldMap {
    val locations: List<Location> = listOf(
        Location(
            id = 0,
            x = 400,
            y = 320,
            name = "Test0",
            description = "description_Test0",
            level = 1,
            danger = 0,
            minReward = Reward(coins = 3, crystals = 1, exp = 2),
            maxReward = Reward(coins = 10, crystals = 3, exp = 8),
            image = "Test0",
            enemyTypes = listOf(1, 2),
        ),
        Location(
            id = 1,
            x = 1000,
            y = 400,
            name = "Test1",
            description = "description_Test1",
            level = 1,
            danger = 0,
            minReward = Reward(coins = 5, crystals = 1, exp = 4),
            maxReward = Reward(coins = 20, crystals = 1, exp = 10),
            image = "Test1",
            enemyTypes = listOf(0, 1),
        ),
        Location(
            id = 2,
            x = 300,
            y = 700,
            name = "Test2",
            description = "description_Test2",
            level = 2,
            danger = 0,
            minReward = Reward(coins = 8, crystals = 1, exp = 15),
            maxReward = Reward(coins = 25, crystals = 2, exp = 25),
            image = "Test2",
            enemyTypes = listOf(0, 1, 2),
        ),
    )
}
