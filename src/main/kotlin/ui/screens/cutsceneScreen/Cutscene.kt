package ui.screens.cutsceneScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ui.screens.cutsceneScreen.event.CutsceneEvent

class Cutscene(
    val name: String,
    val characterIDs: List<Int>,
    val background: String,
    private val eventSequence: List<CutsceneEvent>,
) {
    private var currentEventIndex = -1

    private val stateHistory = mutableListOf<CutsceneState>()
    var state: CutsceneState by mutableStateOf(CutsceneState())

    fun start() {
        currentEventIndex = -1
    }

    fun next(): CutsceneEvent? {
        currentEventIndex++
        val next = eventSequence.getOrNull(currentEventIndex)
        next?.let {
            state = it.newState(state)
            stateHistory.add(state)
        }
        return next
    }

    fun back(): CutsceneEvent? {
        currentEventIndex--
        stateHistory.removeLast()
        state = stateHistory.last()
        return eventSequence.getOrNull(currentEventIndex)
    }
}
