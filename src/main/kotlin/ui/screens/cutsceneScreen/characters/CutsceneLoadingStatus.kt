package ui.screens.cutsceneScreen.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CutsceneLoadingStatus {
    var isLoaded by mutableStateOf(false)
    var loadingCharacterID by mutableStateOf(-1)
}