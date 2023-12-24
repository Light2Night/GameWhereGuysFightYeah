package ui.action

import Game.Actions
import Game.Events.Arguments.Actions.ActionInfo
import properties.textures.Textures

data class Action(
    val name: String,
    val type: Actions,
    val action: () -> ActionInfo?,
) {
    val image get() = when (this.type) {
        Actions.Attack -> Textures["assets/attack.png"]
        Actions.PoisoningEffect -> Textures["assets/poison.png"]
        Actions.InstantHealing -> Textures["assets/instant_healing.png"]
        Actions.HealingEffect -> Textures["assets/healing.png"]
    }
}
