package ui.screens.cutsceneScreen.characters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import properties.user.recruit.RecruitFactory

object CSCharacterStorage {
    private val loadingStatus = CutsceneLoadingStatus()
    private val characters = mutableListOf<CSCharacter>()

    suspend fun loadCharacters(): CutsceneLoadingStatus {
        coroutineScope {
            launch(Dispatchers.IO) {
                loadingStatus.isLoaded = false

                val recruitFactory = RecruitFactory()
                characters.clear()
                recruitFactory.getCharIDs().forEach {
                    loadingStatus.loadingCharacterID = it
                    val recruit = recruitFactory.getPreset(it) ?: return@forEach
                    characters.add(CSCharacter(recruit.id, recruit.charID, listOf()))
                }

                loadingStatus.isLoaded = true
            }
        }

        return loadingStatus
    }
}