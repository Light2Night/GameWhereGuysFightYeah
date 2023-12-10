package ui.action

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
                Action(Actions.Attack.text, Actions.Attack) {
                    val selectedUnit = gameData.selectedUnit ?: return@Action null
                    return@Action currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
            )
            is Magician -> listOf(
                Action(Actions.Attack.text, Actions.Attack) {
                    val selectedUnit = gameData.selectedUnit ?: return@Action null
                    return@Action currentUnit.move(Move(Actions.Attack, selectedUnit.id))
                },
                Action(Actions.Poisoning.text, Actions.Poisoning) {
                    val selectedUnit = gameData.selectedUnit ?: return@Action null
                    return@Action currentUnit.move(Move(Actions.Poisoning, selectedUnit.id))
                },
            )
            is Healer -> listOf(
                Action(Actions.InstantHealing.text, Actions.InstantHealing) {
                    val selectedUnit = gameData.selectedUnit ?: return@Action null
                    return@Action currentUnit.move(Move(Actions.InstantHealing, selectedUnit.id))
                },
                Action(Actions.Healing.text, Actions.Healing) {
                    val selectedUnit = gameData.selectedUnit ?: return@Action null
                    return@Action currentUnit.move(Move(Actions.Healing, selectedUnit.id))
                },
            )
            else -> listOf()
        }
    }
}