package ui.action

import Game.Actions
import Game.Events.Arguments.Actions.ActionInfo
import getImageBitmap

data class Action(
    val name: String,
    val type: Actions,
    val action: () -> ActionInfo?,
) {
    val image = when (this.type) {
        Actions.Attack -> getImageBitmap("textures/assets/attack.png")
        Actions.PoisoningEffect -> getImageBitmap("textures/assets/poison.png")
        Actions.InstantHealing -> getImageBitmap("textures/assets/instant_healing.png")
        Actions.HealingEffect -> getImageBitmap("textures/assets/healing.png")
    }
}
