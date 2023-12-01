package ui.action

import Game.Actions
import lang

val Actions.text get() = when (this) {
    Actions.Attack -> lang.attack_button.uppercase()
    Actions.Poisoning -> lang.poison_button.uppercase()
    Actions.InstantHealing -> lang.instant_healing_button.uppercase()
    Actions.Healing -> lang.healing_button.uppercase()
}