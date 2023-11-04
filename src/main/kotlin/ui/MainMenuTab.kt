package ui

import lang

enum class MainMenuTab {
    Profile, Game, Settings;

    override fun toString(): String {
        return when (this) {
            Profile -> lang.profile_tab.replaceFirstChar { it.uppercaseChar() }
            Game -> lang.game_tab.replaceFirstChar { it.uppercaseChar() }
            Settings -> lang.settings_tab.replaceFirstChar { it.uppercaseChar() }
        }
    }
}