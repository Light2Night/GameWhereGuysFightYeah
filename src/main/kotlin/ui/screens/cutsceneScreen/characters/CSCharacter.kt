package ui.screens.cutsceneScreen.characters

import ui.screens.cutsceneScreen.Expression
import utilities.HasID

class CSCharacter(
    override val id: Int,
    val charID: Int,
    val expressions: List<Expression>,
) : HasID {
}