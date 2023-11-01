package ui

data class Action(
    val name: String,
    val action: (Int) -> Unit,
)
