package ui.action

import Game.Actions
import getImageBitmap

data class Action(
    val name: String,
    val type: Actions,
    val action: () -> Unit,
) {
    val image get() = when (this.type) {
        Actions.Attack -> getImageBitmap("textures/assets/attack.png")
        Actions.Poisoning -> getImageBitmap("textures/assets/poison.png")
        Actions.InstantHealing -> getImageBitmap("textures/assets/instant_healing.png")
        Actions.Healing -> getImageBitmap("textures/assets/healing.png")
    }
}
