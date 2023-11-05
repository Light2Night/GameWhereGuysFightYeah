package ui

import lang

enum class MainMenuTab {
    Guild, Party, World, Settings;

    override fun toString(): String {
        return when (this) {
            Guild -> lang.guild_tab.replaceFirstChar { it.uppercaseChar() }
            Party -> lang.party_tab.replaceFirstChar { it.uppercaseChar() }
            World -> lang.world_tab.replaceFirstChar { it.uppercaseChar() }
            Settings -> lang.settings_tab.replaceFirstChar { it.uppercaseChar() }
        }
    }
}