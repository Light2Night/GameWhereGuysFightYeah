package ui.screens.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import properties.Properties
import settings
import ui.composable.MedievalButton

@Composable
fun SettingsSubScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MedievalButton(
            text = "Українська",
            onClick = {
                settings.language = "ua"
                Properties.saveSettings()
                Properties.loadLanguage(settings.language)
            },
        )

        MedievalButton(
            text = "English",
            onClick = {
                settings.language = "en"
                Properties.saveSettings()
                Properties.loadLanguage(settings.language)
            },
        )
    }
}