package ui

import Game.Characters.Barbarian
import Game.Characters.Healer
import Game.Characters.Magician
import Game.Game
import Game.Move
import Game.Actions
import GameData

class ActionFabric(
    val game: Game,
    val gameData: GameData,
) {
    fun createActions(): List<Action> {
        val currentUnit = gameData.currentUnit.value ?: return listOf()
        val selectedUnit = gameData.selectedUnit.value ?: return listOf()

        return when (currentUnit) {
            is Barbarian -> listOf(
                Action("атака") {
                    currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
            )
            is Magician -> listOf(
                Action("атака") {
                    currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
                Action("отруєння") {
                    currentUnit.move(Move(Actions.Poisoning, selectedUnit.id))
                },
            )
            is Healer -> listOf(
                Action("миттєве лікування") {
                    currentUnit.move(Move(Actions.InstantHealing, selectedUnit.id))
                },
                Action("лікувальний ефект") {
                    currentUnit.move(Move(Actions.Healing, selectedUnit.id))
                },
            )
            else -> listOf()
        }
    }
}