package ui

import Game.Units.Characters.Barbarian
import Game.Units.Characters.Healer
import Game.Units.Characters.Magician
import Game.Game
import Game.Move
import Game.Actions
import Game.PlayerTypes
import gamedata.GameData

class ActionFabric(
    val game: Game,
    val gameData: GameData,
) {
    fun createActions(): List<Action> {
        val currentUnit = gameData.currentUnit ?: return emptyList()

        if (currentUnit.team.playerType == PlayerTypes.AI) {
            return emptyList()
        }

        return when (currentUnit) {
            is Barbarian -> listOf(
                Action("атака") {
                    val selectedUnit = gameData.selectedUnit ?: return@Action
                    currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
            )
            is Magician -> listOf(
                Action("атака") {
                    val selectedUnit = gameData.selectedUnit ?: return@Action
                    currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
                Action("отруєння") {
                    val selectedUnit = gameData.selectedUnit ?: return@Action
                    currentUnit.move(Move(Actions.Poisoning, selectedUnit.id))
                },
            )
            is Healer -> listOf(
                Action("миттєве лікування") {
                    val selectedUnit = gameData.selectedUnit ?: return@Action
                    currentUnit.move(Move(Actions.InstantHealing, selectedUnit.id))
                },
                Action("лікувальний ефект") {
                    val selectedUnit = gameData.selectedUnit ?: return@Action
                    currentUnit.move(Move(Actions.Healing, selectedUnit.id))
                },
            )
            else -> listOf()
        }
    }
}