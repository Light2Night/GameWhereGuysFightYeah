package properties

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val start_button: String,
    val back_button: String,

    val guild_tab: String,
    val party_tab: String,
    val world_tab: String,
    val settings_tab: String,

    val barbarian_name: String,
    val magician_name: String,
    val healer_name: String,

    val user_won: String,
    val ai_won: String,
)