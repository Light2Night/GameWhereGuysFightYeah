package properties.language

import Game.Units.Characters.UnitTypes
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val start_button: String,
    val back_button: String,

    val guild_tab: String,
    val party_tab: String,
    val world_tab: String,
    val settings_tab: String,

    val level_short: String,
    val exp_short: String,

    val barbarian_name: String,
    val magician_name: String,
    val healer_name: String,

    val user_won: String,
    val ai_won: String,

    val no_recruits: String,
    val too_small_level: String,

    val kill_quest_name: String,
) {
    fun getUnitName(type: UnitTypes): String = when(type) {
        UnitTypes.BARBARIAN -> barbarian_name
        UnitTypes.MAGICIAN -> magician_name
        UnitTypes.HEALER -> healer_name
    }
}